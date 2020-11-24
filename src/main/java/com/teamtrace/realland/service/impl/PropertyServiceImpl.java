package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.*;
import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.*;
import com.teamtrace.realland.repository.*;
import com.teamtrace.realland.service.PropertyService;
import com.teamtrace.realland.util.ModelToApiConverter;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    private static Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyTypeRepository propertyTypeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PropertyAmenityRepository propertyAmenityRepository;
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    @Transactional(rollbackFor = Exception.class)
    public CreationResponse createProperty(PropertyCreateRequest request) {
        CreationResponse response = new CreationResponse();

        Optional<PropertyType> propertyTypeOptional = propertyTypeRepository.findById(request.getTypeId());
        if (!propertyTypeOptional.isPresent()) {
            logger.info("Invalid property type. id : {}", request.getTypeId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        PropertyType propertyType = propertyTypeOptional.get();

        Optional<Province> provinceOptional = provinceRepository.findById(request.getProvinceId());
        if (!provinceOptional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getProvinceId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        Province province = provinceOptional.get();

        Optional<District> districtOptional = districtRepository.findById(request.getDistrictId());
        if (!districtOptional.isPresent()) {
            logger.info("Invalid district. id : {}", request.getDistrictId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        District district = districtOptional.get();

        Optional<City> cityOptional = cityRepository.findById(request.getCityId());
        if (!cityOptional.isPresent()) {
            logger.info("Invalid city. id : {}", request.getCityId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        City city = cityOptional.get();

        Property property = new Property();
        property.setTypeId(propertyType.getTypeId());
        property.setStatus(Statuses.PENDING);
        property.setProvinceId(province.getProvinceId());
        property.setDistrictId(district.getDistrictId());
        property.setCityId(city.getCityId());
        property.setAddress(request.getAddress());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setFloorSpace(request.getFloorSpace());
        property.setNumOfBathrooms(request.getNumOfBathrooms());
        property.setNumOfBeds(request.getNumOfBeds());
        property.setNumOfGarages(request.getNumOfGarages());
        property.setNumOfParkingSpaces(request.getNumOfParkingSpaces());
        property.setPrice(request.getPrice());
        property.setYearBuilt(request.getYearBuilt());
        property.setCompleted(request.isCompleted());
        property.setExpectedCompletionYear(request.getExpectedCompletionYear());
        property.setLikes(0);
        property.setViews(0);
        property.setCreatedDate(new Date());

        if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
            property.setAmenities(new ArrayList<>(request.getAmenities().size()));
            for (int amenityId : request.getAmenities()) {
                Optional<PropertyAmenity> optional = propertyAmenityRepository.findById(amenityId);
                if (!optional.isPresent()) {
                    logger.info("Invalid property amenity. id : {}", amenityId);
                    response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                    return response;
                }
                PropertyAmenity propertyAmenity = optional.get();
                property.getAmenities().add(propertyAmenity);
            }
        }
        if (request.getAdminUser() != null) {
            property.setAdminUserId(request.getAdminUser().getAdminUserId());
            property.setMerchantId(request.getMerchantId());
        } else if (request.getClient() != null) {
            property.setClientId(request.getClient().getClientId());
        }

        property = propertyRepository.saveAndFlush(property);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (PropertyImageRequestApi api : request.getImages()) {
                PropertyImage image = new PropertyImage();
                image.setImageUrl(api.getImageUrl());
                image.setPropertyId(property.getPropertyId());
                image.setSequence(api.getSequence());

                propertyImageRepository.save(image);
            }
        }

        //push notification to admin

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        logger.info("Create property successful. id : {}", property.getPropertyId());

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateResponse updateProperty(PropertyUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Property property = propertyRepository.findByPropertyId(request.getPropertyId());
        if (property == null) {
            logger.info("Invalid property. id : {}", request.getPropertyId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiSearchResponse getPropertyByPropertyId(GetByPrimaryIdRequest request) {
        ApiSearchResponse response = new ApiSearchResponse();

        Property property = propertyRepository.findByPropertyId(request.getPrimaryId());
        if (property == null) {
            logger.info("Invalid property. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Optional<PropertyType> propertyTypeOptional = propertyTypeRepository.findById(property.getTypeId());
        if (!propertyTypeOptional.isPresent()) {
            logger.info("Invalid property type. id : {}", property.getTypeId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        PropertyType propertyType = propertyTypeOptional.get();

        Optional<Province> provinceOptional = provinceRepository.findById(property.getProvinceId());
        if (!provinceOptional.isPresent()) {
            logger.info("Invalid province. id : {}", property.getProvinceId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        Province province = provinceOptional.get();

        Optional<District> districtOptional = districtRepository.findById(property.getDistrictId());
        if (!districtOptional.isPresent()) {
            logger.info("Invalid district. id : {}", property.getDistrictId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        District district = districtOptional.get();

        Optional<City> cityOptional = cityRepository.findById(property.getCityId());
        if (!cityOptional.isPresent()) {
            logger.info("Invalid city. id : {}", property.getCityId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }
        City city = cityOptional.get();

        PropertyDetailsApi detailsApi = ModelToApiConverter.convert(property);

        PropertyTypeApi type = new PropertyTypeApi();
        type.setTypeId(propertyType.getTypeId());
        type.setName(propertyType.getName());
        detailsApi.setType(type);

        PropertyProvinceApi provinceApi = new PropertyProvinceApi();
        provinceApi.setProvinceId(province.getProvinceId());
        provinceApi.setName(province.getName());
        detailsApi.setProvince(provinceApi);

        PropertyDistrictApi districtApi = new PropertyDistrictApi();
        districtApi.setDistrictId(district.getDistrictId());
        districtApi.setName(district.getName());
        detailsApi.setDistrict(districtApi);

        PropertyCityApi cityApi = new PropertyCityApi();
        cityApi.setCityId(city.getCityId());
        cityApi.setName(city.getName());
        detailsApi.setCity(cityApi);

        if (property.getClientId() > 0) {
            Optional<Client> optional = clientRepository.findById(property.getClientId());
            if (!optional.isPresent()) {
                logger.info("Invalid client. id : {}", request.getPrimaryId());
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                return response;
            }
            Client client = optional.get();

            PropertyClientApi clientApi = new PropertyClientApi();
            clientApi.setClientId(client.getClientId());
            clientApi.setName(client.getFullName());
            clientApi.setEmail(client.getEmail());
            clientApi.setMobile(client.getMobile());
            detailsApi.setClient(clientApi);
        }
        if (property.getMerchantId() > 0) {
            Optional<Merchant> optional = merchantRepository.findById(property.getAdminUserId());
            if (!optional.isPresent()) {
                logger.info("Invalid merchant. id : {}", request.getPrimaryId());
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                return response;
            }
            Merchant merchant = optional.get();
            PropertyMerchantApi merchantApi = new PropertyMerchantApi();
            merchantApi.setMerchantId(merchant.getMerchantId());
            merchantApi.setName(merchant.getName());
            merchantApi.setTelephone(merchant.getTelephone());
            detailsApi.setMerchant(merchantApi);

        }
        if (property.getAdminUserId() > 0) {
            Optional<AdminUser> optional = adminUserRepository.findById(property.getAdminUserId());
            if (!optional.isPresent()) {
                logger.info("Invalid admin user. id : {}", request.getPrimaryId());
                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                return response;
            }
            AdminUser adminUser = optional.get();
            PropertyAdminUserApi adminUserApi = new PropertyAdminUserApi();
            adminUserApi.setAdminUserId(adminUser.getAdminUserId());
            adminUserApi.setName(adminUser.getFullName());
            adminUserApi.setMobile(adminUser.getMobile());
            adminUserApi.setEmail(adminUser.getEmail());
            detailsApi.setAdminUser(adminUserApi);
        }

        property.setViews(1 + property.getViews());
        propertyRepository.save(property);

        logger.info("Property viewed. id : {}", request.getPrimaryId());

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setData(detailsApi);
        return response;
    }

    public UpdateResponse approveProperty(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Property> optional = propertyRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Property old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve province. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            propertyRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve property successful. id : {}", old.getProvinceId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve property not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendProperty(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Property> optional = propertyRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid property. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Property old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            propertyRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend property successful. id : {}", old.getPropertyId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend property not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteProperty(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Property> optional = propertyRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid property. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Property old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            propertyRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete property successful. id : {}", old.getPropertyId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete property not successful. error : {}", e.getMessage());
        }

        return response;
    }
}
