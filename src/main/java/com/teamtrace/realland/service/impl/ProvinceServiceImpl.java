package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.ProvinceCreateRequest;
import com.teamtrace.realland.api.request.ProvinceUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.Province;
import com.teamtrace.realland.repository.ProvinceRepository;
import com.teamtrace.realland.service.ProvinceService;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    private static Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    @Autowired
    ProvinceRepository provinceRepository;

    public CreationResponse createProvince(ProvinceCreateRequest request) {
        CreationResponse response = new CreationResponse();

        Province province = new Province();
        province.setName(request.getName());
        province.setStatus(Statuses.PENDING);

        try {
            province = provinceRepository.save(province);

            response.setId(province.getProvinceId());
            response.setData(province);
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Create province successful. id : {}", province.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create province not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateProvince(ProvinceUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Province> optional = provinceRepository.findById(request.getProvinceId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getProvinceId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Province old = optional.get();
        old.setName(request.getName());

        try {
            provinceRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update province successful. id : {}", old.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update province not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approveProvince(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Province> optional = provinceRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Province old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            provinceRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve province successful. id : {}", old.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve province not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendProvince(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Province> optional = provinceRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Province old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            provinceRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend province successful. id : {}", old.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend province not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteProvince(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Province> optional = provinceRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Province old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            provinceRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete province successful. id : {}", old.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete province not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
