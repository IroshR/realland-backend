package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.DistrictCreateRequest;
import com.teamtrace.realland.api.request.DistrictUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.District;
import com.teamtrace.realland.model.Province;
import com.teamtrace.realland.repository.DistrictRepository;
import com.teamtrace.realland.repository.ProvinceRepository;
import com.teamtrace.realland.service.DistrictService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService {
    private static Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    public CreationResponse createDistrict(DistrictCreateRequest request) {
        CreationResponse response = new CreationResponse();

        Optional<Province> optional = provinceRepository.findById(request.getProvinceId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getProvinceId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        Province province = optional.get();
        if (province.getStatus() == Statuses.DELETED) {
            logger.info("Can't create district for deleted province. id : {}", request.getProvinceId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        District district = new District();
        district.setProvinceId(request.getProvinceId());
        district.setName(request.getName());
        district.setStatus(Statuses.PENDING);

        try {
            district = districtRepository.save(district);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(district.getDistrictId());
            response.setData(ModelToApiConverter.convert(district, province));
            logger.info("Create district successful. id : {}", district.getDistrictId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create district not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateDistrict(DistrictUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<District> optional = districtRepository.findById(request.getDistrictId());

        if (!optional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getDistrictId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        District old = optional.get();
        old.setName(request.getName());

        try {
            districtRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update district successful. id : {}", old.getDistrictId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update district not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approveDistrict(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<District> optional = districtRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        District old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve district. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            districtRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve district successful. id : {}", old.getDistrictId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve district not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendDistrict(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<District> optional = districtRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        District old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend district. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            districtRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend district successful. id : {}", old.getDistrictId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend district not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteDistrict(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<District> optional = districtRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        District old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            districtRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete district successful. id : {}", old.getDistrictId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete district not successful. error : {}", e.getMessage());
        }

        return response;
    }

}
