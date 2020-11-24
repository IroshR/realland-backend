package com.teamtrace.realland.api;

import java.util.Date;
import java.util.List;

public class PropertyDetailsApi {
    private int propertyId;
    private PropertyTypeApi type;
    private PropertyClientApi client;
    private PropertyAdminUserApi adminUser;
    private PropertyMerchantApi merchant;
    private short status;

    private PropertyProvinceApi province;
    private PropertyDistrictApi district;
    private PropertyCityApi city;
    private String address;
    private double longitude;
    private double latitude;

    private String title;
    private String description;
    private double floorSpace;
    private short numOfBeds;
    private short numOfBathrooms;
    private short numOfGarages;
    private short numOfParkingSpaces;
    private double price;
    private int yearBuilt;

    private boolean isCompleted;
    private String expectedCompletionYear;

    private int likes;
    private int views;

    private Date createdDate;
    private Date updatedDate;

    private List<PropertyAmenityListApi> amenities;
    private List<PropertyImageApi> images;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public PropertyTypeApi getType() {
        return type;
    }

    public void setType(PropertyTypeApi type) {
        this.type = type;
    }

    public PropertyClientApi getClient() {
        return client;
    }

    public void setClient(PropertyClientApi client) {
        this.client = client;
    }

    public PropertyAdminUserApi getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(PropertyAdminUserApi adminUser) {
        this.adminUser = adminUser;
    }

    public PropertyMerchantApi getMerchant() {
        return merchant;
    }

    public void setMerchant(PropertyMerchantApi merchant) {
        this.merchant = merchant;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public PropertyProvinceApi getProvince() {
        return province;
    }

    public void setProvince(PropertyProvinceApi province) {
        this.province = province;
    }

    public PropertyDistrictApi getDistrict() {
        return district;
    }

    public void setDistrict(PropertyDistrictApi district) {
        this.district = district;
    }

    public PropertyCityApi getCity() {
        return city;
    }

    public void setCity(PropertyCityApi city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(double floorSpace) {
        this.floorSpace = floorSpace;
    }

    public short getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(short numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public short getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(short numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }

    public short getNumOfGarages() {
        return numOfGarages;
    }

    public void setNumOfGarages(short numOfGarages) {
        this.numOfGarages = numOfGarages;
    }

    public short getNumOfParkingSpaces() {
        return numOfParkingSpaces;
    }

    public void setNumOfParkingSpaces(short numOfParkingSpaces) {
        this.numOfParkingSpaces = numOfParkingSpaces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getExpectedCompletionYear() {
        return expectedCompletionYear;
    }

    public void setExpectedCompletionYear(String expectedCompletionYear) {
        this.expectedCompletionYear = expectedCompletionYear;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<PropertyAmenityListApi> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<PropertyAmenityListApi> amenities) {
        this.amenities = amenities;
    }

    public List<PropertyImageApi> getImages() {
        return images;
    }

    public void setImages(List<PropertyImageApi> images) {
        this.images = images;
    }
}
