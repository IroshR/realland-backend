package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.PropertyGridApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PropertyGridMapper extends AbstractMapper<PropertyGridApi> {
    public PropertyGridMapper() {
        columnMap = new HashMap<>(10);
        columnMap.put("propertyId", "A.property_id");
        columnMap.put("address", "A.address");
        columnMap.put("title", "A.title");
        columnMap.put("likes", "A.likes");
        columnMap.put("views", "A.views");
        columnMap.put("createdDate", "A.created_date");
        columnMap.put("updatedDate", "A.updated_date");
        columnMap.put("propertyTypeName", "B.name");
        columnMap.put("status", "A.status");
    }

    @Override
    public PropertyGridApi mapRow(ResultSet row, int rowNum) throws SQLException {
        PropertyGridApi api = new PropertyGridApi();
        api.setPropertyId(row.getInt("A.property_id"));
        api.setAddress(row.getString("A.address"));
        api.setTitle(row.getString("A.title"));
        api.setLikes(row.getInt("A.likes"));
        api.setViews(row.getInt("A.views"));
        api.setStatus(row.getShort("A.status"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));
        api.setUpdatedDate(row.getTimestamp("A.updated_date"));
        api.setPropertyTypeName(row.getString("B.name"));

        return api;
    }
}
