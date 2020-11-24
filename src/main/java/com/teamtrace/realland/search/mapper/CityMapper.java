package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.CityApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CityMapper extends AbstractMapper<CityApi> {
    public CityMapper() {
        columnMap = new HashMap<>(6);
        columnMap.put("cityId", "A.city_id");
        columnMap.put("name", "A.name");
        columnMap.put("zipCode", "A.zip_code");
        columnMap.put("status", "A.status");
        columnMap.put("districtId", "A.district_id");
        columnMap.put("districtName", "B.name");
        columnMap.put("provinceId", "B.province_id");
        columnMap.put("provinceName", "C.name");
    }

    @Override
    public CityApi mapRow(ResultSet row, int rowNum) throws SQLException {
        CityApi api = new CityApi();
        api.setCityId(row.getInt("A.city_id"));
        api.setName(row.getString("A.name"));
        api.setZipCode(row.getString("A.zip_code"));
        api.setStatus(row.getShort("A.status"));
        api.setDistrictId(row.getInt("A.district_id"));
        api.setDistrictName(row.getString("B.name"));
        api.setProvinceId(row.getInt("B.province_id"));
        api.setProvinceName(row.getString("C.name"));

        return api;
    }
}
