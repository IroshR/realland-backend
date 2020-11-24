package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.api.response.AdminUserLoginResponse;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.AdminUser;
import com.teamtrace.realland.model.Merchant;
import com.teamtrace.realland.model.Role;
import com.teamtrace.realland.model.RoleGroup;
import com.teamtrace.realland.repository.AdminUserRepository;
import com.teamtrace.realland.repository.MerchantRepository;
import com.teamtrace.realland.repository.RoleGroupRepository;
import com.teamtrace.realland.service.AdminUserService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.SessionUtil;
import com.teamtrace.realland.util.UUIDUtil;
import com.teamtrace.realland.util.constant.Statuses;
import com.teamtrace.realland.util.constant.SystemParameters;
import com.teamtrace.realland.util.password.PasswordHandler;
import com.teamtrace.realland.util.password.PasswordHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private static Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private PasswordHandlerFactory passwordHandlerFactory;
    @Autowired
    private SessionUtil sessionUtil;

    public CreationResponse createAdminUser(AdminUserCreateRequest request) throws GeneralSecurityException {
        CreationResponse response = new CreationResponse();

        List<AdminUser> users = adminUserRepository.findAdminUserByLoginName(request.getLoginName());

        if (!users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Create Admin user, login name already exists. {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }

        Optional<Merchant> optional = merchantRepository.findById(request.getMerchantId());
        if (!optional.isPresent()) {
            logger.info("Create Admin user, invalid merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        Merchant merchant = optional.get();
        if (merchant.getStatus() == Statuses.DELETED) {
            logger.info("Create Admin user, can't create admin user for deleted merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        String pwd = UUIDUtil.generateRandomPassword(SystemParameters.AUTO_PASSWORD_LENGTH);

        String newPassword = passwordHandlerFactory.getPasswordHandler(PasswordHandler.MD5).
                hashPassword(request.getLoginName(), pwd);

        AdminUser adminUser = new AdminUser();
        adminUser.setEmail(request.getEmail());
        adminUser.setLoginName(request.getLoginName());
        adminUser.setPassword(newPassword);
        adminUser.setPwd(pwd);
        adminUser.setFullName(request.getFullName());
        adminUser.setMerchantId(request.getMerchantId());
        adminUser.setMobile(request.getMobile());
        adminUser.setNotificationEnable(true);
        adminUser.setPasswordChangeRequired(true);
        adminUser.setProfileImageUrl(request.getProfileImageUrl());
        adminUser.setStatus(Statuses.PENDING);
        adminUser.setCreatedDate(new Date());
        adminUser.setTypeId(merchant.getTypeId());

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            adminUser.setRoleGroups(new ArrayList<>(request.getRoles().size()));
            for (int roleId : request.getRoles()) {
                try {
                    RoleGroup roleGroup = roleGroupRepository.getOne(roleId);
                    adminUser.getRoleGroups().add(roleGroup);
                } catch (Exception e) {
                    logger.info("Admin user creation, invalid user role. id : {}", roleId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    response.setMessage(request.getResourceBundle().getString("invalid_user_role"));

                    return response;
                }
            }
        }

        try {
            adminUser = adminUserRepository.save(adminUser);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(adminUser.getAdminUserId());
            response.setData(ModelToApiConverter.convert(adminUser));
            logger.info("Create admin user, create system user. id : {}", adminUser.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create admin user, create system user not successful. error : {}", e.getMessage());

            return response;
        }

        //todo send notification

        return response;
    }

    public UpdateResponse updateAdminUserBasicDetails(AdminUserBasicDetailsUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getAdminUser().getAdminUserId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        old.setFullName(request.getFullName());
        old.setMobile(request.getMobile());
        if (request.getRoles() != null) {
            old.setRoleGroups(new ArrayList<>(request.getRoles().size()));
            for (int roleId : request.getRoles()) {
                try {
                    RoleGroup roleGroup = roleGroupRepository.getOne(roleId);
                    old.getRoleGroups().add(roleGroup);
                } catch (Exception e) {
                    logger.info("Admin user update, invalid user role. id : {}", roleId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    response.setMessage(request.getResourceBundle().getString("invalid_user_role"));

                    return response;
                }
            }
        }

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update admin user basic details successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update admin user basic details not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateAdminUserProfileImage(AdminUserProfileImageUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getAdminUser().getAdminUserId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        old.setProfileImageUrl(request.getProfileImageUrl());

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update admin user profile image successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update admin user profile image not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public ApiSearchResponse getAdminUserByUserId(GetByPrimaryIdRequest request) {
        ApiSearchResponse response = new ApiSearchResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setData(ModelToApiConverter.convert(old));
        logger.info("Get Admin user details by user id. id : {}", old.getAdminUserId());


        return response;
    }

    public UpdateResponse changeAdminUserPassword(AdminUserChangePasswordRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getAdminUser().getAdminUserId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getAdminUser().getAdminUserId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        if (!request.getOldPassword().equals(old.getPassword())) {
            logger.info("Invalid password. id : {}", request.getAdminUser().getAdminUserId());
            response.setMessage("Invalid password.");
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setPassword(request.getNewPassword());

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Change admin user password successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Change admin user password not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approveAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve admin user not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend admin user not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteAdminUser(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<AdminUser> optional = adminUserRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid admin user. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        AdminUser old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            adminUserRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete admin user successful. id : {}", old.getAdminUserId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete admin user not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public AdminUserLoginResponse loginAdminUser(AdminUserLoginRequest request) {
        AdminUserLoginResponse response = new AdminUserLoginResponse();

        List<AdminUser> users = adminUserRepository.findAdminUserByLoginName(request.getLoginName());

        if (users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Login admin user, admin user not found. username : {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }
        AdminUser adminUser = users.get(0);

        Optional<Merchant> optional = merchantRepository.findById(adminUser.getMerchantId());
        if (!optional.isPresent()) {
            logger.info("Login admin user, invalid merchant. id : {}", adminUser.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        Merchant merchant = optional.get();
        if (merchant.getStatus() == Statuses.DELETED) {
            logger.info("Login admin user, can't login for deleted merchant. id : {}", adminUser.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        try {
            boolean validPasswordNew = passwordHandlerFactory.getPasswordHandler(PasswordHandler.MD5)
                    .validatePassword(request.getLoginName(), request.getPassword(), adminUser.getPassword());
            boolean validPassword = request.getPassword().equals(adminUser.getPassword());
            if (!validPassword) {
                logger.info("Login admin user, invalid password for user. username : {}", request.getLoginName());

                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                response.setMessage(request.getResourceBundle().getString("invalid_login_name_or_password"));

                return response;
            }

        } catch (GeneralSecurityException e) {
            logger.info("Login admin user, error occurred. error : {}", e.getMessage());

            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));

            return response;
        }

        List<Integer> roles = new ArrayList<>(100);
        for (RoleGroup roleGroup : adminUser.getRoleGroups()) {
            /*for (Role r : roleGroup.getRoles()) {
                r.getName();
            }*/
            roles.addAll(roleGroup.getRoles()
                    .stream().map(Role::getRoleId).collect(Collectors.toList())
            );
        }
        String sessionId = UUIDUtil.generateToken();

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setAdminUserId(adminUser.getAdminUserId());
        response.setFullName(adminUser.getFullName());
        response.setProfileImageUrl(adminUser.getProfileImageUrl());
        response.setSessionId(sessionId);
        response.setRoles(roles);
        response.setPasswordChangeRequired(adminUser.isPasswordChangeRequired());

        response.setMerchantId(adminUser.getMerchantId());
        response.setMerchantType(merchant.getTypeId());
        response.setMerchantName(merchant.getName());

        sessionUtil.addAdminUserSession(sessionId, adminUser);

        return response;
    }

    public UpdateResponse logoutAdminUser(AdminUserLogoutRequest request) {
        UpdateResponse response = new UpdateResponse();

        if (sessionUtil.removeAdminUserSession(request.getSessionId())) {
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

            return response;
        } else {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage("Invalid admin user session.");

            return response;
        }

    }
}
