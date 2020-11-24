package com.teamtrace.realland.search.systemparameter;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.SystemParameterMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SystemParameterSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.parameter_id, A.data_type, A.sys_key, A.sys_value FROM sys_parameter A ";
    static String COUNT_SQL = "SELECT COUNT(A.parameter_id) AS ct FROM sys_parameter A ";

    public SystemParameterSearch() {
        super(SELECT_SQL, COUNT_SQL, new SystemParameterMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
