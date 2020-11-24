package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.SystemParameterApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SystemParameterMapper extends AbstractMapper<SystemParameterApi> {
    public SystemParameterMapper() {
        columnMap = new HashMap<>(6);
        columnMap.put("parameterId", "A.parameter_id");
        columnMap.put("dataType", "A.data_type");
        columnMap.put("key", "A.sys_key");
        columnMap.put("value", "A.sys_value");
    }

    @Override
    public SystemParameterApi mapRow(ResultSet row, int rowNum) throws SQLException {
        SystemParameterApi api = new SystemParameterApi();
        api.setParameterId(row.getInt("A.parameter_id"));
        api.setDataType(row.getShort("A.data_type"));
        api.setKey(row.getString("A.sys_key"));
        api.setValue(row.getString("A.sys_value"));
        return api;
    }
}
