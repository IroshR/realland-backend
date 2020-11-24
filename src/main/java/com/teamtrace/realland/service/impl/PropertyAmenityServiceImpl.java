package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.PropertyAmenityCreateRequest;
import com.teamtrace.realland.api.request.PropertyAmenityUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.PropertyAmenity;
import com.teamtrace.realland.model.PropertyType;
import com.teamtrace.realland.repository.PropertyAmenityRepository;
import com.teamtrace.realland.repository.PropertyTypeRepository;
import com.teamtrace.realland.service.PropertyAmenityService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyAmenityServiceImpl implements PropertyAmenityService {
    private static Logger logger = LoggerFactory.getLogger(PropertyAmenityServiceImpl.class);

    @Autowired
    private PropertyAmenityRepository propertyAmenityRepository;
    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    public CreationResponse createPropertyAmenity(PropertyAmenityCreateRequest request) {
        CreationResponse response = new CreationResponse();

        Optional<PropertyType> optional = propertyTypeRepository.findById(request.getPropertyTypeId());

        if (!optional.isPresent()) {
            logger.info("Invalid property type. id : {}", request.getPropertyTypeId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        PropertyType propertyType = optional.get();
        if (propertyType.getStatus() == Statuses.DELETED) {
            logger.info("Can't create property amenity for deleted property type. id : {}", request.getPropertyTypeId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        PropertyAmenity propertyAmenity = new PropertyAmenity();
        propertyAmenity.setTypeId(request.getPropertyTypeId());
        propertyAmenity.setName(request.getName());
        propertyAmenity.setStatus(Statuses.PENDING);

        try {
            propertyAmenity = propertyAmenityRepository.save(propertyAmenity);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(propertyAmenity.getAmenityId());
            response.setData(ModelToApiConverter.convert(propertyAmenity, propertyType));
            logger.info("Create property amenity successful. id : {}", propertyAmenity.getAmenityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create property amenity not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updatePropertyAmenity(PropertyAmenityUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<PropertyAmenity> optional = propertyAmenityRepository.findById(request.getPropertyAmenityId());

        if (!optional.isPresent()) {
            logger.info("Invalid property amenity. id : {}", request.getPropertyAmenityId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        PropertyAmenity old = optional.get();
        old.setName(request.getName());

        try {
            propertyAmenityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update property amenity successful. id : {}", old.getAmenityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update property amenity not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approvePropertyAmenity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<PropertyAmenity> optional = propertyAmenityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid property amenity. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        PropertyAmenity old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve amenity successful. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            propertyAmenityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve amenity successful successful. id : {}", old.getAmenityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve amenity successful not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendPropertyAmenity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<PropertyAmenity> optional = propertyAmenityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid property amenity. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        PropertyAmenity old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend property amenity. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            propertyAmenityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend property amenity successful. id : {}", old.getAmenityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend amenity successful not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deletePropertyAmenity(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<PropertyAmenity> optional = propertyAmenityRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid property amenity. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        PropertyAmenity old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            propertyAmenityRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete property amenity successful. id : {}", old.getAmenityId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete property amenity not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
