package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.PropertyGridBySystemOwnerApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PropertyGridBySystemOwnerMapper extends AbstractMapper<PropertyGridBySystemOwnerApi> {
    public PropertyGridBySystemOwnerMapper() {
        columnMap = new HashMap<>(15);
        columnMap.put("propertyId", "A.property_id");
        columnMap.put("address", "A.address");
        columnMap.put("title", "A.title");
        columnMap.put("likes", "A.likes");
        columnMap.put("views", "A.views");
        columnMap.put("createdDate", "A.created_date");
        columnMap.put("updatedDate", "A.updated_date");
        columnMap.put("propertyTypeName", "B.name");
        columnMap.put("status", "A.status");
        columnMap.put("merchantId", "A.merchant_id");
        columnMap.put("merchantName", "D.name");
        columnMap.put("clientId", "A.client_id");
        columnMap.put("clientName", "C.full_name");
    }

    @Override
    public PropertyGridBySystemOwnerApi mapRow(ResultSet row, int rowNum) throws SQLException {
        PropertyGridBySystemOwnerApi api = new PropertyGridBySystemOwnerApi();
        api.setPropertyId(row.getInt("A.property_id"));
        api.setAddress(row.getString("A.address"));
        api.setTitle(row.getString("A.title"));
        api.setLikes(row.getInt("A.likes"));
        api.setViews(row.getInt("A.views"));
        api.setStatus(row.getShort("A.status"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));
        api.setUpdatedDate(row.getTimestamp("A.updated_date"));
        api.setPropertyTypeName(row.getString("B.name"));
        api.setClientId(row.getInt("A.client_id"));
        api.setClientName(row.getString("C.merchant_id"));
        api.setMerchantId(row.getInt("A.views"));
        api.setMerchantName(row.getString("D.name"));

        return api;
    }
}
