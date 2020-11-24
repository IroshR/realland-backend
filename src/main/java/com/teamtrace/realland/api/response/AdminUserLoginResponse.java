package com.teamtrace.realland.api.response;

import java.util.List;

public class AdminUserLoginResponse extends ApiResponse {
    private String sessionId;
    private int adminUserId;
    private String loginName;
    private String fullName;
    private String profileImageUrl;
    private boolean isPasswordChangeRequired;
    private List<Integer> roles;

    private int merchantId;
    private short merchantType;
    private String merchantName;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isPasswordChangeRequired() {
        return isPasswordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        isPasswordChangeRequired = passwordChangeRequired;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public short isMerchantType() {
        return merchantType;
    }

    public void setMerchantType(short merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
