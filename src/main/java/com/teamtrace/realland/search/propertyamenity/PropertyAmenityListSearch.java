package com.teamtrace.realland.search.propertyamenity;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.StaticDataOnlySearchTemplate;
import com.teamtrace.realland.search.mapper.ListMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class PropertyAmenityListSearch extends StaticDataOnlySearchTemplate {
    static String SELECT_SQL = "SELECT A.amenity_id AS id, A.name AS name, A.type_id AS typeId " +
            " FROM property_amenity A where A.status = 2 AND A.type_id = ? ";

    public PropertyAmenityListSearch() {
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
