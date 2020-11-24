package com.teamtrace.realland.search.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class ListMapper extends AbstractMapper<HashMap> {
    public ListMapper() {
        columnMap = new HashMap<>(1);
    }

    @Override
    public HashMap mapRow(ResultSet row, int rowNum) throws SQLException {
        ResultSetMetaData metaData = row.getMetaData();
        int columns = metaData.getColumnCount();
        HashMap<String, Object> api = new HashMap<>(columns);
        for (int i = 1; i <= columns; i++) {
            if (row.getObject(i) == null)
                api.put(metaData.getColumnLabel(i), "");
            else
                api.put(metaData.getColumnLabel(i), row.getObject(i));
        }

        return api;
    }
}
