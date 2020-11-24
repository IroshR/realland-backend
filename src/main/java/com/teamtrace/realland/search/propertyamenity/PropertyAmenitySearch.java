package com.teamtrace.realland.search.propertyamenity;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.PropertyAmenityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyAmenitySearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.amenity_id, A.name, A.status, A.type_id, B.name " +
            " FROM property_amenity A " +
            " INNER JOIN property_type B USING(type_id)";
    static String COUNT_SQL = "" +
            " SELECT COUNT(A.amenity_id) AS ct " +
            " FROM property_amenity A " +
            " INNER JOIN property_type B USING(type_id)";

    public PropertyAmenitySearch() {
        super(SELECT_SQL, COUNT_SQL, new PropertyAmenityMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}

