package com.teamtrace.realland.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtrace.realland.model.AdminUser;
import com.teamtrace.realland.model.Client;

import java.util.ResourceBundle;

public class Request {
    protected int merchantId;
    @JsonIgnore
    protected ResourceBundle resourceBundle;
    @JsonIgnore
    protected AdminUser adminUser;
    @JsonIgnore
    protected Client client;

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ResourceBundle getResourceBundle() {
        if (this.resourceBundle == null) {
            this.resourceBundle = ResourceBundle.getBundle("messages");
        }
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
