package com.teamtrace.realland.search.district;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.StaticDataOnlySearchTemplate;
import com.teamtrace.realland.search.mapper.ListMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DistrictListSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.district_id AS id, A.name AS name, A.province_id AS provinceId " +
            " FROM district A where A.status = 2 AND A.province_id = ? ";

    public DistrictListSearch() {
        super(SELECT_SQL, new ListMapper());
    }

    public PreparedStatementSetter getDataStatement(SearchCriteria criteria) {
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, criteria.getPK());
            }
        };
    }
}
