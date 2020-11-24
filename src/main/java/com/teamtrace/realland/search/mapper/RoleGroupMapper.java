package com.teamtrace.realland.search.mapper;

import com.teamtrace.realland.api.RoleGroupApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RoleGroupMapper extends AbstractMapper<RoleGroupApi> {
    public RoleGroupMapper() {
        columnMap = new HashMap<>(4);
        columnMap.put("roleGroupId", "A.role_group_id");
        columnMap.put("name", "A.name");
        columnMap.put("status", "A.status");
        columnMap.put("createdDate", "A.created_date");
    }

    @Override
    public RoleGroupApi mapRow(ResultSet row, int rowNum) throws SQLException {
        RoleGroupApi api = new RoleGroupApi();
        api.setRoleGroupId(row.getInt("A.role_group_id"));
        api.setName(row.getString("A.name"));
        api.setStatus(row.getShort("A.status"));
        api.setCreatedDate(row.getTimestamp("A.created_date"));
        return api;
    }
}
