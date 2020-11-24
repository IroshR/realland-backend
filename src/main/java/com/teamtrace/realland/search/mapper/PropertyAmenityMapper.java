package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.PropertyAmenityApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PropertyAmenityMapper extends AbstractMapper<PropertyAmenityApi> {
    public PropertyAmenityMapper() {
        columnMap = new HashMap<>(6);
        columnMap.put("amenityId", "A.amenity_id");
        columnMap.put("name", "A.name");
        columnMap.put("status", "A.status");
        columnMap.put("typeId", "A.type_id");
        columnMap.put("typeName", "B.name");
    }

    @Override
    public PropertyAmenityApi mapRow(ResultSet row, int rowNum) throws SQLException {
        PropertyAmenityApi api = new PropertyAmenityApi();
        api.setAmenityId(row.getInt("A.amenity_id"));
        api.setName(row.getString("A.name"));
        api.setStatus(row.getShort("A.status"));
        api.setTypeId(row.getShort("A.type_id"));
        api.setTypeName(row.getString("B.name"));
        return api;
    }
}
