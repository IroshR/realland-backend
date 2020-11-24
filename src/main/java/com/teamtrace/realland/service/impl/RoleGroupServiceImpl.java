package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.RoleGroupRoleApi;
import com.teamtrace.realland.api.request.RoleGroupCreateRequest;
import com.teamtrace.realland.api.request.RoleGroupRolesFindRequest;
import com.teamtrace.realland.api.request.RoleGroupUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.Merchant;
import com.teamtrace.realland.model.Role;
import com.teamtrace.realland.model.RoleGroup;
import com.teamtrace.realland.repository.MerchantRepository;
import com.teamtrace.realland.repository.RoleGroupRepository;
import com.teamtrace.realland.repository.RoleRepository;
import com.teamtrace.realland.service.RoleGroupService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.MerchantType;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleGroupServiceImpl implements RoleGroupService {
    private static Logger logger = LoggerFactory.getLogger(RoleGroupServiceImpl.class);

    @Autowired
    private RoleGroupRepository roleGroupRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    public CreationResponse createRoleGroup(RoleGroupCreateRequest request) {
        CreationResponse response = new CreationResponse();

        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setMerchantId(request.getMerchantId());
        roleGroup.setStatus(Statuses.PENDING);
        roleGroup.setName(request.getName());
        roleGroup.setCreatedDate(new Date());

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (Integer id : request.getRoles()) {
                Optional<Role> optional = roleRepository.findById(id);

                if (!optional.isPresent()) {
                    logger.info("Role group creation, invalid role. id : {}", id);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    return response;
                }

                roleGroup.getRoles().add(optional.get());
            }
        }

        try {

            roleGroup = roleGroupRepository.save(roleGroup);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(roleGroup.getRoleGroupId());
            response.setData(ModelToApiConverter.convert(roleGroup));
            logger.info("Create role group successful. id : {}", roleGroup.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create role group not successful. error : {}", e.getMessage());
        }

        return response;

    }

    public UpdateResponse updateRoleGroup(RoleGroupUpdateRequest request) {
        CreationResponse response = new CreationResponse();

        Optional<RoleGroup> optional = roleGroupRepository.findById(request.getRoleGroupId());

        if (!optional.isPresent()) {
            logger.info("Invalid role group. id : {}", request.getRoleGroupId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        RoleGroup old = optional.get();
        old.setName(request.getName());

        old.setRoles(new ArrayList<>());
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (Integer id : request.getRoles()) {
                Optional<Role> optionalRole = roleRepository.findById(id);

                if (!optionalRole.isPresent()) {
                    logger.info("Role group update, invalid role. id : {}", id);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    return response;
                }

                old.getRoles().add(optionalRole.get());
            }
        }

        try {

            old = roleGroupRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update role group not successful. error : {}", e.getMessage());
        }

        return response;

    }

    public UpdateResponse approveRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<RoleGroup> optional = roleGroupRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        RoleGroup old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            roleGroupRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve role group not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<RoleGroup> optional = roleGroupRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        RoleGroup old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            roleGroupRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend role group not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteRoleGroup(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<RoleGroup> optional = roleGroupRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid role group. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        RoleGroup old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            roleGroupRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete role group successful. id : {}", old.getRoleGroupId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete role group not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public CreationResponse getRoleGroupRolesByRoleGroupId(RoleGroupRolesFindRequest request) {
        CreationResponse response = new CreationResponse();

        try {
            RoleGroupRoleApi api = new RoleGroupRoleApi();
            Optional<Merchant> optionalMerchant = merchantRepository.findById(request.getMerchantId());

            if (!optionalMerchant.isPresent()) {
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                response.setMessage(request.getResourceBundle().getString("merchant_not_found"));
                logger.info("Get role group roles, merchant not found. id : {}", request.getMerchantId());

                return response;
            }
            Merchant merchant = optionalMerchant.get();


            Optional<RoleGroup> optional = roleGroupRepository.findById(request.getRoleGroupId());

            if (!optional.isPresent()) {
                logger.info("Get role group roles, invalid role group. id : {}", request.getRoleGroupId());
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                return response;
            }
            RoleGroup roleGroup = optional.get();

            List<Integer> roles = new ArrayList<>(1);
            for (Role e : roleGroup.getRoles()) {
                roles.add(e.getRoleId());
            }

            api.setAssignedRoles(roles);
            if (roles.isEmpty()) {//if empty list
                roles.add(-1);
            }

            List<Integer> types = new ArrayList<>(4);
            if (merchant.getTypeId() == MerchantType.SUPER_ADMIN) {
                types.add(1);
                types.add(3);
                types.add(5);
                types.add(7);
            } else if (merchant.getTypeId() == MerchantType.DEALER) {
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
            api.setUnassignedRoles(roleRepository.unassignedRolesByMerchantType(roles, types));

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setData(api);

        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
        }

        return response;
    }
}
