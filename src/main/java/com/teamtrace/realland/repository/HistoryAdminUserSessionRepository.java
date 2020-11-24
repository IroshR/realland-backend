package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.HistoryAdminUserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryAdminUserSessionRepository extends JpaRepository<HistoryAdminUserSession, String> {
}
