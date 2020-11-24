package com.teamtrace.realland.search.property;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.PropertyGridBySystemOwnerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyGridBySystemOwnerSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.property_id, A.address, A.title, A.created_date, A.updated_date, " +
            " A.likes, A.views, A.price, A.status, B.name, C.client_id, C.full_name, D.merchant_id, D.name " +
            " FROM property A " +
            " INNER JOIN property_type B USING(type_id)" +
            " LEFT JOIN client C USING(client_id)" +
            " LEFT JOIN merchant D USING(merchant_id)";
    static String COUNT_SQL = "" +
            " SELECT COUNT(A.property_id) AS ct A " +
            " FROM property A " +
            " INNER JOIN property_type B USING(type_id)";

    public PropertyGridBySystemOwnerSearch() {
        super(SELECT_SQL, COUNT_SQL, new PropertyGridBySystemOwnerMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
