package com.teamtrace.realland.api.request;

import java.util.List;

public class PropertyCreateRequest extends Request {
    private short typeId;
    private int provinceId;
    private int districtId;
    private int cityId;
    private String address;
    private double longitude;
    private double latitude;
    private String description;
    private String title;
    private double floorSpace;
    private short numOfBeds;
    private short numOfBathrooms;
    private short numOfGarages;
    private short numOfParkingSpaces;
    private double price;
    private int yearBuilt;

    private boolean isCompleted;
    private String expectedCompletionYear;

    private List<Integer> amenities;
    private List<PropertyImageRequestApi> images;

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Integer> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Integer> amenities) {
        this.amenities = amenities;
    }

    public List<PropertyImageRequestApi> getImages() {
        return images;
    }

    public void setImages(List<PropertyImageRequestApi> images) {
        this.images = images;
    }
}
