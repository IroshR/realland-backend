package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {
    @Query("SELECT a FROM AdminUser a WHERE a.loginName = :loginName")
    List<AdminUser> findAdminUserByLoginName(@Param("loginName") String loginName);
}
