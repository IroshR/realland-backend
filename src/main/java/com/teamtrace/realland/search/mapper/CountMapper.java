package com.teamtrace.realland.search.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountMapper extends AbstractMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet row, int rowNum) throws SQLException {
        return row.getInt("ct");
    }
}
