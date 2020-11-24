package com.teamtrace.realland.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
@Cacheable
public class Client {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer clientId;
    @Column(name = "channel_id", updatable = false, nullable = false, columnDefinition = "SMALLINT(2) UNSIGNED")
    private int channelId;
    @Column(name = "reference", nullable = false, columnDefinition = "VARCHAR(10)")
    private String reference;
    @Column(name = "email", columnDefinition = "VARCHAR(45)")
    private String email;
    @Column(name = "login_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String loginName;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(400)")
    private String password;
    @Column(name = "mobile", nullable = false, columnDefinition = "VARCHAR(20)")
    private String mobile;
    @Column(name = "full_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String fullName;
    @Column(name = "address", columnDefinition = "VARCHAR(200)")
    private String address;
    @Column(name = "profile_image_url", columnDefinition = "VARCHAR(100)")
    private String profileImageUrl;
    @Column(name = "is_notification_enable", nullable = false, columnDefinition = "TINYINT(1) UNSIGNED DEFAULT '0'")
    private boolean isNotificationEnable;
    @Column(name = "auth_type", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short authType;
    @Column(name = "device_type", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short deviceType;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "subscription_expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionExpiryDate;
    @Column(name = "otp", columnDefinition = "VARCHAR(8)")
    private String otp;
    @Column(name = "otp_status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED default '0'")
    private short otpStatus;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isNotificationEnable() {
        return isNotificationEnable;
    }

    public void setNotificationEnable(boolean notificationEnable) {
        isNotificationEnable = notificationEnable;
    }

    public short getAuthType() {
        return authType;
    }

    public void setAuthType(short authType) {
        this.authType = authType;
    }

    public short getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(short deviceType) {
        this.deviceType = deviceType;
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

    public Date getSubscriptionExpiryDate() {
        return subscriptionExpiryDate;
    }

    public void setSubscriptionExpiryDate(Date subscriptionExpiryDate) {
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
        this.otp = "2222"; //todo remove
    }

    public short getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(short otpStatus) {
        this.otpStatus = otpStatus;
    }
}
