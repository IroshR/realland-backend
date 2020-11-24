package com.teamtrace.realland.search.property;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.PropertyGridMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyGridSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.property_id, A.address, A.title, A.created_date, A.updated_date, " +
            " A.likes, A.views, A.price, A.status, B.name " +
            " FROM property A " +
            " INNER JOIN property_type B USING(type_id)";
    static String COUNT_SQL = "" +
            " SELECT COUNT(A.property_id) AS ct A " +
            " FROM property A " +
            " INNER JOIN property_type B USING(type_id)";

    public PropertyGridSearch() {
        super(SELECT_SQL, COUNT_SQL, new PropertyGridMapper(), new CountMapper());
        this.merchantLevel = true;
    }
}
