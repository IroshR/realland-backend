package com.teamtrace.realland.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
@Cacheable
public class Merchant extends Model {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer merchantId;
    @Column(name = "type_id", nullable = false, columnDefinition = MerchantType.PK_TYPE)
    private short typeId;
    @Column(name = "category_id", nullable = false, columnDefinition = MerchantCategory.PK_TYPE)
    private short categoryId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(name = "registration_no", columnDefinition = "VARCHAR(50)")
    private String registrationNo;
    @Column(name = "email", columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(name = "address", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String address;
    @Column(name = "telephone", columnDefinition = "VARCHAR(20) DEFAULT NULL")
    private String telephone;
    @Column(name = "telephone2", columnDefinition = "VARCHAR(20) DEFAULT NULL")
    private String telephone2;
    @Column(name = "web_sites", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String websites;
    @Column(name = "longitude", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double longitude;
    @Column(name = "latitude", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double latitude;
    @Column(name = "logo_url", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String logoUrl;
    @Column(name = "banner_url", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String bannerUrl;
    @Column(name = "html_profile_info", columnDefinition = "VARCHAR(10000) DEFAULT NULL")
    private String htmlProfileInfo;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "review_rate", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double reviewRate;
    @Column(name = "review_count", nullable = false, columnDefinition = "INT(11) UNSIGNED DEFAULT '0'")
    private int reviewCount;
    @Column(name = "subs_package_id", updatable = false, nullable = false, columnDefinition = "SMALLINT(5) UNSIGNED DEFAULT '0'")
    private int subsPackageId;
    @Column(name = "subscription_expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionExpiryDate;
    @Column(name = "monthly_subscription_amount", columnDefinition = VALUE_TYPE, scale = SCALE, precision = PRECISION)
    private double monthlySubscriptionAmount;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }

    public short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(short categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getWebsites() {
        return websites;
    }

    public void setWebsites(String websites) {
        this.websites = websites;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getHtmlProfileInfo() {
        return htmlProfileInfo;
    }

    public void setHtmlProfileInfo(String htmlProfileInfo) {
        this.htmlProfileInfo = htmlProfileInfo;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(double reviewRate) {
        this.reviewRate = reviewRate;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getSubsPackageId() {
        return subsPackageId;
    }

    public void setSubsPackageId(int subsPackageId) {
        this.subsPackageId = subsPackageId;
    }

    public Date getSubscriptionExpiryDate() {
        return subscriptionExpiryDate;
    }

    public void setSubscriptionExpiryDate(Date subscriptionExpiryDate) {
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }

    public double getMonthlySubscriptionAmount() {
        return monthlySubscriptionAmount;
    }

    public void setMonthlySubscriptionAmount(double monthlySubscriptionAmount) {
        this.monthlySubscriptionAmount = monthlySubscriptionAmount;
    }
}
