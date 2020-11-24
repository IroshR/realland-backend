package com.teamtrace.realland.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "property")
public class Property extends Model {
    public static final String PK_TYPE = "SMALLINT(11) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer propertyId;
    @Column(name = "type_id", updatable = false, nullable = false, columnDefinition = PropertyType.PK_TYPE)
    private short typeId;
    @Column(name = "client_id", updatable = false, nullable = false, columnDefinition = Client.PK_TYPE)
    private int clientId;
    @Column(name = "admin_user_id", updatable = false, nullable = false, columnDefinition = "SMALLINT(5) UNSIGNED DEFAULT '0'")
    private int adminUserId;
    @Column(name = "merchant_id", updatable = false, nullable = false, columnDefinition = "SMALLINT(5) UNSIGNED DEFAULT '0'")
    private int merchantId;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;

    @Column(name = "province_id", updatable = false, nullable = false, columnDefinition = Province.PK_TYPE)
    private int provinceId;
    @Column(name = "district_id", updatable = false, nullable = false, columnDefinition = District.PK_TYPE)
    private int districtId;
    @Column(name = "city_id", updatable = false, nullable = false, columnDefinition = City.PK_TYPE)
    private int cityId;
    @Column(name = "address", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String address;
    @Column(name = "longitude", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double longitude;
    @Column(name = "latitude", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double latitude;

    @Column(name = "description", columnDefinition = "VARCHAR(10000) DEFAULT NULL")
    private String description;
    @Column(name = "title", columnDefinition = "VARCHAR(256) DEFAULT NULL")
    private String title;
    @Column(name = "floor_space", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double floorSpace;
    @Column(name = "num_of_beds", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short numOfBeds;
    @Column(name = "num_of_bathrooms", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short numOfBathrooms;
    @Column(name = "num_of_garages", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short numOfGarages;
    @Column(name = "num_of_parking_spaces", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short numOfParkingSpaces;
    @Column(name = "price", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double price;
    @Column(name = "year_built", nullable = false, columnDefinition = "INT(11) UNSIGNED DEFAULT '0'")
    private int yearBuilt;

    @Column(name = "is_completed", nullable = false, columnDefinition = "TINYINT(1) UNSIGNED DEFAULT '0'")
    private boolean isCompleted;
    @Column(name = "expected_completion_year", columnDefinition = "VARCHAR(40) DEFAULT NULL")
    private String expectedCompletionYear;

    @Column(name = "likes", nullable = false, columnDefinition = "INT(11) UNSIGNED DEFAULT '0'")
    private int likes;
    @Column(name = "views", nullable = false, columnDefinition = "INT(11) UNSIGNED DEFAULT '0'")
    private int views;

    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "property_property_amenity",
            joinColumns = @JoinColumn(name = "property_id", columnDefinition = PK_TYPE),
            inverseJoinColumns = @JoinColumn(name = "amenity_id", columnDefinition = PropertyAmenity.PK_TYPE)
    )
    private List<PropertyAmenity> amenities = new ArrayList<>();

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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

    public List<PropertyAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<PropertyAmenity> amenities) {
        this.amenities = amenities;
    }
}
