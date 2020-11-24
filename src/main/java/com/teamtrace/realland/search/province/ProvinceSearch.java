package com.teamtrace.realland.search.province;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.ProvinceMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinceSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.province_id, A.name, A.status FROM province A ";
    static String COUNT_SQL = "SELECT COUNT(A.province_id) AS ct FROM province A ";

    public ProvinceSearch() {
        super(SELECT_SQL, COUNT_SQL, new ProvinceMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
