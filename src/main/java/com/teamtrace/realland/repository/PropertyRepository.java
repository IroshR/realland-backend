package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Property a WHERE a.propertyId = :propertyId")
    Property findByPropertyId(@Param("propertyId") Integer propertyId);
}
