package com.teamtrace.realland.search.systemstatement;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.SystemStatementMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SystemStatementSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.statement_id, A.sys_key, A.sys_value FROM sys_statement A ";
    static String COUNT_SQL = "SELECT COUNT(A.statement_id) AS ct FROM sys_statement A ";

    public SystemStatementSearch() {
        super(SELECT_SQL, COUNT_SQL, new SystemStatementMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
