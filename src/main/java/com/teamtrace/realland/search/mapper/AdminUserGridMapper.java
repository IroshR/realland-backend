package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.AdminUserGridApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AdminUserGridMapper extends AbstractMapper<AdminUserGridApi> {
    public AdminUserGridMapper() {
        columnMap = new HashMap<>(11);
        columnMap.put("adminUserId", "A.admin_user_id");
        columnMap.put("merchantId", "A.merchant_id");
        columnMap.put("email", "A.email");
        columnMap.put("mobile", "A.mobile");
        columnMap.put("loginName", "A.login_name");
        columnMap.put("fullName", "A.full_name");
        columnMap.put("profileImageUrl", "A.profile_image_url");
        columnMap.put("isPasswordChangeRequired", "A.is_password_change_required");
        columnMap.put("isNotificationEnable", "A.is_notification_enable");
        columnMap.put("status", "A.status");
        columnMap.put("createdDate", "A.created_date");
    }

    @Override
    public AdminUserGridApi mapRow(ResultSet row, int rowNum) throws SQLException {
        AdminUserGridApi api = new AdminUserGridApi();
        api.setAdminUserId(row.getInt("A.admin_user_id"));
        api.setMerchantId(row.getInt("A.merchant_id"));
        api.setEmail(row.getString("A.email"));
        api.setMobile(row.getString("A.mobile"));
        api.setLoginName(row.getString("A.login_name"));
        api.setFullName(row.getString("A.full_name"));
        api.setProfileImageUrl(row.getString("A.profile_image_url"));
        api.setPasswordChangeRequired(row.getBoolean("is_password_change_required"));
        api.setNotificationEnable(row.getBoolean("A.is_notification_enable"));
        api.setStatus(row.getShort("A.status"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));

        return api;
    }
}
