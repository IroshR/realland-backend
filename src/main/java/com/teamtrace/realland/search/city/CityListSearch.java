package com.teamtrace.realland.search.city;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.StaticDataOnlySearchTemplate;
import com.teamtrace.realland.search.mapper.ListMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CityListSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.city_id AS id, A.name AS name, A.district_id AS districtId " +
            " FROM city A where A.status = 2 AND A.district_id = ? ";

    public CityListSearch() {
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
