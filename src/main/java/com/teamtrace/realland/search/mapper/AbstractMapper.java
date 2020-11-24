package com.teamtrace.realland.search.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;

public abstract class AbstractMapper<T> implements RowMapper<T> {
    protected HashMap<String, String> columnMap;

    public HashMap<String, String> getColumnMap() {
        return columnMap;
    }
}
