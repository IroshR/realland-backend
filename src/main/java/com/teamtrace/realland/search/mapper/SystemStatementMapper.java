package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.SystemStatementApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SystemStatementMapper extends AbstractMapper<SystemStatementApi> {
    public SystemStatementMapper() {
        columnMap = new HashMap<>(3);
        columnMap.put("statementId", "A.statement_id");
        columnMap.put("key", "A.sys_key");
        columnMap.put("value", "A.sys_value");
    }

    @Override
    public SystemStatementApi mapRow(ResultSet row, int rowNum) throws SQLException {
        SystemStatementApi api = new SystemStatementApi();
        api.setStatementId(row.getInt("A.statement_id"));
        api.setKey(row.getString("A.sys_key"));
        api.setValue(row.getString("A.sys_value"));
        return api;
    }
}
