package com.teamtrace.realland.util;

import com.teamtrace.realland.api.*;
import com.teamtrace.realland.model.*;

import java.util.ArrayList;
import java.util.List;

public class ModelToApiConverter {
    private ModelToApiConverter() {
    }

    public static AdminUserApi convert(AdminUser adminUser) {
        AdminUserApi api = new AdminUserApi();
        api.setMerchantId(adminUser.getMerchantId());
        api.setAdminUserId(adminUser.getAdminUserId());
        api.setStatus(adminUser.getStatus());
        api.setCreatedDate(adminUser.getCreatedDate());
        api.setEmail(adminUser.getEmail());
        api.setLoginName(adminUser.getLoginName());
        api.setFullName(adminUser.getFullName());
        api.setMobile(adminUser.getMobile());
        api.setNotificationEnable(adminUser.isNotificationEnable());
        api.setPasswordChangeRequired(adminUser.isPasswordChangeRequired());
        api.setProfileImageUrl(adminUser.getProfileImageUrl());
        if (adminUser.getRoleGroups() != null && !adminUser.getRoleGroups().isEmpty()) {
            for (RoleGroup roleGroup : adminUser.getRoleGroups()) {
                api.getRoleGroups().add(roleGroup.getRoleGroupId());
            }
        }

        return api;
    }

    public static MerchantApi convert(Merchant merchant) {
        MerchantApi api = new MerchantApi();
        api.setMerchantId(merchant.getMerchantId());
        api.setTypeId(merchant.getTypeId());
        api.setCategoryId(merchant.getCategoryId());
        api.setCreatedDate(merchant.getCreatedDate());
        api.setStatus(merchant.getStatus());
        api.setName(merchant.getName());
        api.setRegistrationNo(merchant.getRegistrationNo());
        api.setEmail(merchant.getEmail());
        api.setTelephone(merchant.getTelephone());
        api.setTelephone2(merchant.getTelephone2());
        api.setWebsites(merchant.getWebsites());
        api.setHtmlProfileInfo(merchant.getHtmlProfileInfo());
        api.setLogoUrl(merchant.getLogoUrl());
        api.setBannerUrl(merchant.getBannerUrl());
        api.setLatitude(merchant.getLatitude());
        api.setLongitude(merchant.getLongitude());

        return api;
    }

    public static RoleGroupApi convert(RoleGroup roleGroup) {
        RoleGroupApi api = new RoleGroupApi();
        api.setRoleGroupId(roleGroup.getRoleGroupId());
        api.setName(roleGroup.getName());
        api.setStatus(roleGroup.getStatus());
        api.setCreatedDate(roleGroup.getCreatedDate());

        return api;
    }

    public static PropertyDetailsApi convert(Property property) {
        PropertyDetailsApi api = new PropertyDetailsApi();
        api.setPropertyId(property.getPropertyId());
        api.setStatus(property.getStatus());
        api.setAddress(property.getAddress());
        api.setLongitude(property.getLongitude());
        api.setLatitude(property.getLatitude());
        api.setTitle(property.getTitle());
        api.setDescription(property.getDescription());
        api.setFloorSpace(property.getFloorSpace());
        api.setNumOfBeds(property.getNumOfBeds());
        api.setNumOfBathrooms(property.getNumOfBathrooms());
        api.setNumOfGarages(property.getNumOfGarages());
        api.setNumOfParkingSpaces(property.getNumOfParkingSpaces());
        api.setPrice(property.getPrice());
        api.setYearBuilt(property.getYearBuilt());
        api.setCompleted(property.isCompleted());
        api.setExpectedCompletionYear(property.getExpectedCompletionYear());
        api.setViews(property.getViews());
        api.setLikes(property.getLikes());
        api.setCreatedDate(property.getCreatedDate());
        api.setUpdatedDate(property.getUpdatedDate());

        return api;
    }

    public static PropertyAmenityApi convert(PropertyAmenity propertyAmenity, PropertyType propertyType) {
        PropertyAmenityApi api = new PropertyAmenityApi();
        api.setAmenityId(propertyAmenity.getAmenityId());
        api.setName(propertyAmenity.getName());
        api.setStatus(propertyAmenity.getStatus());
        api.setTypeId(propertyType.getTypeId());
        api.setTypeName(propertyType.getName());

        return api;
    }

    public static DistrictApi convert(District district, Province province) {
        DistrictApi api = new DistrictApi();
        api.setDistrictId(district.getDistrictId());
        api.setStatus(district.getStatus());
        api.setName(district.getName());
        api.setProvinceId(province.getProvinceId());
        api.setProvinceName(province.getName());

        return api;
    }

    public static CityApi convert(City city, District district) {
        CityApi api = new CityApi();
        api.setCityId(city.getCityId());
        api.setStatus(city.getStatus());
        api.setName(city.getName());
        api.setZipCode(city.getZipCode());
        api.setDistrictId(district.getDistrictId());
        api.setDistrictName(district.getName());

        return api;
    }

    public static RoleApi convert(Role role) {
        RoleApi api = new RoleApi();
        api.setRoleId(role.getRoleId());
        api.setName(role.getName());

        return api;
    }

    public static List<RoleApi> convertRoleList(List<Role> roles) {
        List list = new ArrayList();
        for (Role role : roles) {
            list.add(convert(role));
        }

        return list;
    }
}
