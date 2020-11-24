package com.teamtrace.realland.search.merchant;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.CountMapper;
import com.teamtrace.realland.search.mapper.MerchantGridMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.merchant_id, A.type_id, B.name, A.category_id, C.name, A.name, A.registration_no, A.email," +
            " A.address, A.telephone, A.telephone2, A.logo_url, A.status, A.created_date " +
            " FROM merchant A " +
            " LEFT JOIN merchant_type B USING(type_id)" +
            " LEFT JOIN merchant_category C USING(category_id)";
    static String COUNT_SQL = "SELECT COUNT(A.merchant_id) AS ct FROM merchant A ";

    public MerchantSearch() {
        super(SELECT_SQL, COUNT_SQL, new MerchantGridMapper(), new CountMapper());
        this.merchantLevel = false;
    }
}
