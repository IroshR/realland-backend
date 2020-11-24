package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("SELECT a FROM Client a WHERE a.loginName = :loginName")
    List<Client> findClientByLoginName(@Param("loginName") String loginName);

    @Query("SELECT a FROM Client a WHERE a.loginName = :loginName AND a.mobile = :mobile")
    List<Client> findClientByLoginNameAndMobile(@Param("loginName") String loginName, @Param("mobile") String mobile);
}
