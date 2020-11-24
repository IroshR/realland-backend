package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.CityCreateRequest;
import com.teamtrace.realland.api.request.CityUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.City;
import com.teamtrace.realland.model.District;
import com.teamtrace.realland.repository.CityRepository;
import com.teamtrace.realland.repository.DistrictRepository;
import com.teamtrace.realland.service.CityService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DistrictRepository districtRepository;

    public CreationResponse createCity(CityCreateRequest request) {
        CreationResponse response = new CreationResponse();

        Optional<District> optional = districtRepository.findById(request.getDistrictId());

        if (!optional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getDistrictId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        District district = optional.get();
        if (district.getStatus() == Statuses.DELETED) {
            logger.info("Can't create city for deleted province. id : {}", request.getDistrictId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        City city = new City();
        city.setDistrictId(request.getDistrictId());
        city.setName(request.getName());
        city.setZipCode(request.getZipCode());
        city.setStatus(Statuses.PENDING);

        try {
            city = cityRepository.save(city);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(city.getCityId());
            response.setData(ModelToApiConverter.convert(city, district));
            logger.info("Create city successful. id : {}", city.getCityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create city not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateCity(CityUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<City> optional = cityRepository.findById(request.getCityId());

        if (!optional.isPresent()) {
            logger.info("Invalid city. id : {}", request.getCityId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        City old = optional.get();
        old.setName(request.getName());
        old.setZipCode(request.getZipCode());

        try {
            cityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update city successful. id : {}", old.getCityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update city not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approveCity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<City> optional = cityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid city. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        City old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve city. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            cityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve city successful. id : {}", old.getCityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve city not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendCity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<City> optional = cityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid city. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        City old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend city. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            cityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend city successful. id : {}", old.getCityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend city not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteCity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<City> optional = cityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid city. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        City old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            cityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete city successful. id : {}", old.getCityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete city not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
