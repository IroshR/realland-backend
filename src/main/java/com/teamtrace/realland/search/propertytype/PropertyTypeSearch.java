package com.teamtrace.realland.search.propertytype;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.StaticDataOnlySearchTemplate;
import com.teamtrace.realland.search.mapper.ListMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class PropertyTypeSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.type_id AS id, A.name AS name " +
            " FROM property_type A where A.status = 2";

    public PropertyTypeSearch() {
        super(SELECT_SQL, new ListMapper());
    }

    public PreparedStatementSetter getDataStatement(SearchCriteria criteria) {
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
            }
        };
    }
}
