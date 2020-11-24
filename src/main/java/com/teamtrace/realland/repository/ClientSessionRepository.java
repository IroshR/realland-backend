package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.ClientSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientSessionRepository extends JpaRepository<ClientSession, String> {
    @Query("SELECT a FROM ClientSession a WHERE a.client.clientId = :clientId")
    List<ClientSession> findClientSessionsByAdminUserId(@Param("clientId") int clientId);
}
