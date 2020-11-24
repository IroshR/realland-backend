package com.teamtrace.realland.search.adminuser;

import com.teamtrace.realland.search.DynamicSearchTemplate;
import com.teamtrace.realland.search.mapper.AdminUserGridMapper;
import com.teamtrace.realland.search.mapper.CountMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserSearch extends DynamicSearchTemplate {
    static String SELECT_SQL = "SELECT A.admin_user_id, A.merchant_id, A.email, A.mobile, A.login_name, " +
            "A.full_name,A.profile_image_url,A.is_password_change_required,A.is_notification_enable,A.status, " +
            "A.created_date FROM admin_user A ";
    static String COUNT_SQL = "SELECT COUNT(A.admin_user_id) AS ct FROM admin_user A ";

    public AdminUserSearch() {
        super(SELECT_SQL, COUNT_SQL, new AdminUserGridMapper(), new CountMapper());
        this.merchantLevel = true;
    }
}
