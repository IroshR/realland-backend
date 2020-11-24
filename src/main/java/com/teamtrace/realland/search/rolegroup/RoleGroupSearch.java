package com.teamtrace.realland.search.rolegroup;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.RoleGroupMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RoleGroupSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.role_group_id, A.name, A.status, A.created_date FROM role_group A ";
    static String COUNT_SQL = "SELECT COUNT(A.role_group_id) AS ct FROM role_group A ";

    public RoleGroupSearch() {
        super(SELECT_SQL, COUNT_SQL, new RoleGroupMapper(), new CountMapper());
        this.merchantLevel = true;
    }
}
