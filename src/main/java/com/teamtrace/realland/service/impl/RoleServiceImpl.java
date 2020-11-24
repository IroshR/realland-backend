package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.Request;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.model.Merchant;
import com.teamtrace.realland.model.Role;
import com.teamtrace.realland.repository.MerchantRepository;
import com.teamtrace.realland.repository.RoleRepository;
import com.teamtrace.realland.service.RoleService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.MerchantType;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    public ApiSearchResponse getApprovedRolesByMerchantId(Request request) {
        ApiSearchResponse response = new ApiSearchResponse();

        Optional<Merchant> optionalMerchant = merchantRepository.findById(request.getMerchantId());

        if (!optionalMerchant.isPresent()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("merchant_not_found"));
            logger.info("Get approved roles, merchant not found. id : {}", request.getMerchantId());

            return response;
        }
        Merchant merchant = optionalMerchant.get();

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

        List<Role> roles = roleRepository.getRolesByMerchantType(types);

        if (roles != null && !roles.isEmpty()) {

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setData(ModelToApiConverter.convertRoleList(roles));
            logger.info("Get approved roles successful.");
        } else {
            response.setStatus(Statuses.RESPONSE_STATUS_NO_DATA);
        }

        return response;
    }
}
