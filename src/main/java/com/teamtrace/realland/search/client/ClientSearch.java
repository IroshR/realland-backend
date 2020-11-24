package com.teamtrace.realland.search.client;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.ClientGridMapper;
import com.teamtrace.realland.search.mapper.CountMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClientSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "" +
            " SELECT A.client_id, A.channel_id, A.email, A.mobile, A.full_name, A.reference, A.address, A.login_name, " +
            " A.is_notification_enable, A.status, A.created_date, A.auth_type, A.subscription_expiry_date " +
            " FROM client A ";
    static String COUNT_SQL = "" +
            " SELECT COUNT(A.client_id) AS ct " +
            " FROM client A ";

    public ClientSearch() {
        super(SELECT_SQL, COUNT_SQL, new ClientGridMapper(), new CountMapper());
        this.merchantLevel = true;
    }
}
