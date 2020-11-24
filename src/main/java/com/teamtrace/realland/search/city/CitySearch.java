package com.teamtrace.realland.search.city;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CityMapper;
import com.teamtrace.realland.search.mapper.CountMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CitySearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.city_id, A.name, A.zip_code, A.status, A.district_id, B.name, B.province_id, C.name " +
            " FROM city A INNER JOIN district B USING(district_id)" +
            " LEFT JOIN province C USING(province_id)";
    static String COUNT_SQL = "SELECT COUNT(A.city_id) AS ct FROM city A INNER JOIN district B USING(district_id)";

    public CitySearch() {
        super(SELECT_SQL, COUNT_SQL, new CityMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
