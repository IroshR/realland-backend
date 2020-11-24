package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.DistrictApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DistrictMapper extends AbstractMapper<DistrictApi> {
    public DistrictMapper() {
        columnMap = new HashMap<>(6);
        columnMap.put("districtId", "A.district_id");
        columnMap.put("name", "A.name");
        columnMap.put("status", "A.status");
        columnMap.put("provinceId", "A.province_id");
        columnMap.put("provinceName", "B.name");
    }

    @Override
    public DistrictApi mapRow(ResultSet row, int rowNum) throws SQLException {
        DistrictApi api = new DistrictApi();
        api.setDistrictId(row.getInt("A.district_id"));
        api.setName(row.getString("A.name"));
        api.setStatus(row.getShort("A.status"));
        api.setProvinceId(row.getInt("A.province_id"));
        api.setProvinceName(row.getString("B.name"));

        return api;
    }
}
