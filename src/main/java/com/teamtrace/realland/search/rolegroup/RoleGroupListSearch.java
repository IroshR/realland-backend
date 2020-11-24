package com.teamtrace.realland.search.rolegroup;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.StaticDataOnlySearchTemplate;
import com.teamtrace.realland.search.mapper.ListMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class RoleGroupListSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.role_group_id AS id, A.name AS name " +
            " FROM role_group A where A.status = 2 AND A.merchant_id = ? ";

    public RoleGroupListSearch() {
        super(SELECT_SQL, new ListMapper());
    }

    public PreparedStatementSetter getDataStatement(SearchCriteria criteria) {
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, criteria.getMerchantId());
            }
        };
    }
}
