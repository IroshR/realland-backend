package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.ClientGridApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientGridMapper extends AbstractMapper<ClientGridApi> {
    public ClientGridMapper() {
        columnMap = new HashMap<>(11);
        columnMap.put("clientId", "A.client_id");
        columnMap.put("channelId", "A.channel_id");
        columnMap.put("reference", "A.reference");
        columnMap.put("fullName", "A.full_name");
        columnMap.put("loginName", "A.login_name");
        columnMap.put("email", "A.email");
        columnMap.put("mobile", "A.mobile");
        columnMap.put("address", "A.address");
        columnMap.put("isNotificationEnable", "A.is_notification_enable");
        columnMap.put("status", "A.status");
        columnMap.put("authType", "A.auth_type");
        columnMap.put("createdDate", "A.created_date");
        columnMap.put("subscriptionExpiryDate", "A.subscription_expiry_date");
    }

    @Override
    public ClientGridApi mapRow(ResultSet row, int rowNum) throws SQLException {
        ClientGridApi api = new ClientGridApi();
        api.setClientId(row.getInt("A.client_id"));
        api.setChannelId(row.getInt("A.merchant_id"));
        api.setReference(row.getString("A.reference"));
        api.setEmail(row.getString("A.email"));
        api.setMobile(row.getString("A.mobile"));
        api.setFullName(row.getString("A.full_name"));
        api.setLoginName(row.getString("A.login_name"));
        api.setAddress(row.getString("A.address"));
        api.setNotificationEnable(row.getBoolean("A.is_notification_enable"));
        api.setStatus(row.getShort("A.status"));
        api.setAuthType(row.getShort("A.auth_type"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));
        api.setSubscriptionExpiryDate(row.getTimestamp("A.subscription_expiry_date"));

        return api;
    }
}
