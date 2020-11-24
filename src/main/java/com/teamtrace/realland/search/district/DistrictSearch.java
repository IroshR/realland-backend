package com.teamtrace.realland.search.district;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.DistrictMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DistrictSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.district_id, A.name, A.status, A.province_id, B.name FROM district A INNER JOIN province B USING(province_id)";
    static String COUNT_SQL = "SELECT COUNT(A.district_id) AS ct FROM district A INNER JOIN province B USING(province_id)";

    public DistrictSearch() {
        super(SELECT_SQL, COUNT_SQL, new DistrictMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
