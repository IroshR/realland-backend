package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.SystemParameterCreateRequest;
import com.teamtrace.realland.api.request.SystemParameterUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.SystemParameter;
import com.teamtrace.realland.repository.SystemParameterRepository;
import com.teamtrace.realland.service.SystemParameterService;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {
    private static Logger logger = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    public CreationResponse createSystemParameter(SystemParameterCreateRequest request) {
        CreationResponse response = new CreationResponse();

        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setDataType(request.getDataType());
        systemParameter.setKey(request.getKey());
        systemParameter.setValue(request.getValue());

        try {
            systemParameter = systemParameterRepository.save(systemParameter);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(systemParameter.getParameterId());
            response.setData(systemParameter);
            logger.info("Create system parameter successful. id : {}", systemParameter.getParameterId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create system parameter not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateSystemParameter(SystemParameterUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<SystemParameter> optional = systemParameterRepository.findById(request.getParameterId());

        if (!optional.isPresent()) {
            logger.info("Invalid system parameter. id : {}", request.getParameterId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        SystemParameter old = optional.get();
        old.setKey(request.getKey());
        old.setValue(request.getValue());

        try {
            systemParameterRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update system parameter successful. id : {}", old.getParameterId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update system parameter not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
