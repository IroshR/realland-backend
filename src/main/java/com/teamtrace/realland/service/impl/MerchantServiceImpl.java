package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.GetByPrimaryIdRequest;
import com.teamtrace.realland.api.request.MerchantCreateRequest;
import com.teamtrace.realland.api.request.MerchantUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.AdminUser;
import com.teamtrace.realland.model.Merchant;
import com.teamtrace.realland.model.RoleGroup;
import com.teamtrace.realland.repository.AdminUserRepository;
import com.teamtrace.realland.repository.MerchantRepository;
import com.teamtrace.realland.repository.RoleGroupRepository;
import com.teamtrace.realland.repository.RoleRepository;
import com.teamtrace.realland.service.MerchantService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.UUIDUtil;
import com.teamtrace.realland.util.constant.MerchantType;
import com.teamtrace.realland.util.constant.Statuses;
import com.teamtrace.realland.util.constant.SystemParameters;
import com.teamtrace.realland.util.password.PasswordHandler;
import com.teamtrace.realland.util.password.PasswordHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {
    private static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private RoleGroupRepository roleGroupRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordHandlerFactory passwordHandlerFactory;

    @Transactional
    public CreationResponse createMerchant(MerchantCreateRequest request) throws GeneralSecurityException {
        CreationResponse response = new CreationResponse();

        List<AdminUser> users = adminUserRepository.findAdminUserByLoginName(request.getLoginName());

        if (!users.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Create merchant, login name already exists. {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }

        Merchant merchant = new Merchant();
        merchant.setName(request.getName());
        merchant.setEmail(request.getEmail());
        merchant.setAddress(request.getAddress());
        merchant.setRegistrationNo(request.getRegistrationNo());
        merchant.setTelephone(request.getTelephone());
        merchant.setTelephone2(request.getTelephone2());
        merchant.setStatus(Statuses.PENDING);
        merchant.setCreatedDate(new Date());
        merchant.setBannerUrl(request.getBannerUrl());
        merchant.setLogoUrl(request.getLogoUrl());
        merchant.setWebsites(request.getWebsites());
        merchant.setCategoryId(request.getCategoryId());
        merchant.setHtmlProfileInfo(request.getHtmlProfileInfo());
        merchant.setLatitude(request.getLatitude());
        merchant.setLongitude(request.getLongitude());

        String pwd = UUIDUtil.generateRandomPassword(SystemParameters.AUTO_PASSWORD_LENGTH);

        String newPassword = passwordHandlerFactory.getPasswordHandler(PasswordHandler.MD5).
                hashPassword(request.getLoginName(), pwd);
        AdminUser adminUser = new AdminUser();
        adminUser.setEmail(request.getEmail());
        adminUser.setLoginName(request.getLoginName());
        adminUser.setPassword(newPassword);
        adminUser.setPwd(pwd);
        adminUser.setFullName(request.getName());
        adminUser.setNotificationEnable(true);
        adminUser.setPasswordChangeRequired(true);
        adminUser.setStatus(Statuses.PENDING);
        adminUser.setCreatedDate(new Date());

        RoleGroup roleGroup = new RoleGroup();

        roleGroup.setStatus(Statuses.PENDING);
        roleGroup.setName("Super Admin");
        roleGroup.setCreatedDate(new Date());
        //todo set roles for role gorup
        List<Integer> types = new ArrayList<>(4);
        if (merchant.getTypeId() == MerchantType.DEALER) {
            types.add(2);
            types.add(3);
            types.add(6);
            types.add(7);
        } else if (merchant.getTypeId() == MerchantType.SERVICE_PROVIDER) {
            types.add(4);
            types.add(5);
            types.add(6);
            types.add(7);
        }
        roleGroup.setRoles(roleRepository.getRolesByMerchantType(types));


        merchant = merchantRepository.saveAndFlush(merchant);

        roleGroup.setMerchantId(merchant.getMerchantId());
        roleGroup = roleGroupRepository.saveAndFlush(roleGroup);

        adminUser.setMerchantId(merchant.getMerchantId());
        adminUser.setRoleGroups(new ArrayList<>(1));
        adminUser.getRoleGroups().add(roleGroup);
        adminUser = adminUserRepository.save(adminUser);

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setId(merchant.getMerchantId());
        response.setData(ModelToApiConverter.convert(merchant));
        logger.info("Create merchant, create merchant successful. : {}", merchant.getMerchantId());


        //todo send email notification

        return response;
    }

    public UpdateResponse updateMerchant(MerchantUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Merchant> optional = merchantRepository.findById(request.getMerchantId());

        if (!optional.isPresent()) {
            logger.info("Update merchant, invalid merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Merchant old = optional.get();
        if (old.getStatus() != Statuses.DELETED) {
            logger.info("Update merchant, can't update merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setName(request.getName());
        old.setAddress(request.getAddress());
        old.setRegistrationNo(request.getRegistrationNo());
        old.setTelephone(request.getTelephone());
        old.setTelephone2(request.getTelephone2());
        old.setBannerUrl(request.getBannerUrl());
        old.setLogoUrl(request.getLogoUrl());
        old.setWebsites(request.getWebsites());
        old.setHtmlProfileInfo(request.getHtmlProfileInfo());
        old.setLatitude(request.getLatitude());
        old.setLongitude(request.getLongitude());

        try {
            merchantRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update merchant, update merchant successful. id : {}", old.getMerchantId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update merchant, update merchant not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public ApiSearchResponse getMerchantById(GetByPrimaryIdRequest request) {
        ApiSearchResponse response = new ApiSearchResponse();

        Optional<Merchant> optional = merchantRepository.findById(request.getMerchantId());

        if (!optional.isPresent()) {
            logger.info("Update merchant, invalid merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Merchant old = optional.get();
        if (old.getStatus() != Statuses.DELETED) {
            logger.info("Update merchant, can't update merchant. id : {}", request.getMerchantId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setData(ModelToApiConverter.convert(old));
        logger.info("Get merchant details by id. id : {}", old.getMerchantId());

        return response;
    }

    public UpdateResponse approveMerchant(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Merchant> optional = merchantRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid merchant. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Merchant old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve merchant. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            merchantRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve merchant successful. id : {}", old.getMerchantId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve merchant not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendMerchant(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Merchant> optional = merchantRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid merchant. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Merchant old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend merchant. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            merchantRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend merchant successful. id : {}", old.getMerchantId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend merchant not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteMerchant(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Merchant> optional = merchantRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid merchant. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Merchant old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            merchantRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete merchant successful. id : {}", old.getMerchantId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete merchant not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
