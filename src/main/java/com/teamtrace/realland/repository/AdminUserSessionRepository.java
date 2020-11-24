package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.AdminUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUserSessionRepository extends JpaRepository<AdminUserSession, String> {
    @Query("SELECT a FROM AdminUserSession a WHERE a.adminUser.adminUserId = :adminUserId")
    List<AdminUserSession> findAdminUserSessionsByAdminUserId(@Param("adminUserId") int adminUserId);
}
