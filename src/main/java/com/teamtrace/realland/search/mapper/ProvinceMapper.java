package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.ProvinceApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProvinceMapper extends AbstractMapper<ProvinceApi> {
    public ProvinceMapper() {
        columnMap = new HashMap<>(6);
        columnMap.put("provinceId", "A.province_id");
        columnMap.put("name", "A.name");
        columnMap.put("status", "A.status");
    }

    @Override
    public ProvinceApi mapRow(ResultSet row, int rowNum) throws SQLException {
        ProvinceApi api = new ProvinceApi();
        api.setProvinceId(row.getInt("A.province_id"));
        api.setName(row.getString("A.name"));
        api.setStatus(row.getShort("A.status"));
        return api;
    }
}
