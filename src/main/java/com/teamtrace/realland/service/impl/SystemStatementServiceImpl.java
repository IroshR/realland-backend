package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.SystemParameterCreateRequest;
import com.teamtrace.realland.api.request.SystemParameterUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.SystemStatement;
import com.teamtrace.realland.repository.SystemStatementRepository;
import com.teamtrace.realland.service.SystemStatementService;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemStatementServiceImpl implements SystemStatementService {
    private static Logger logger = LoggerFactory.getLogger(SystemStatementServiceImpl.class);

    @Autowired
    private SystemStatementRepository systemStatementRepository;

    public CreationResponse createSystemStatement(SystemParameterCreateRequest request) {
        CreationResponse response = new CreationResponse();

        SystemStatement systemStatement = new SystemStatement();
        systemStatement.setKey(request.getKey());
        systemStatement.setValue(request.getValue());

        try {
            systemStatement = systemStatementRepository.save(systemStatement);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(systemStatement.getStatementId());
            response.setData(systemStatement);
            logger.info("Create system statement successful. id : {}", systemStatement.getStatementId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create system statement not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateSystemStatement(SystemParameterUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<SystemStatement> optional = systemStatementRepository.findById(request.getParameterId());

        if (!optional.isPresent()) {
            logger.info("Invalid system statement. id : {}", request.getParameterId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        SystemStatement old = optional.get();
        old.setKey(request.getKey());
        old.setValue(request.getValue());

        try {
            systemStatementRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update system statement successful. id : {}", old.getStatementId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update system statement not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
