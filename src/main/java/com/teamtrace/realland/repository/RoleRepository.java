package com.teamtrace.realland.repository;

import com.teamtrace.realland.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT a.roleId FROM Role a WHERE a.roleId NOT IN :roleIds AND a.merchantTypeId IN :types")
    List<Integer> unassignedRolesByMerchantType(@Param("roleIds") List<Integer> roleIds,
                                                @Param("types") List<Integer> types);

    @Query("SELECT a FROM Role a WHERE a.merchantTypeId IN :types")
    List<Role> getRolesByMerchantType(@Param("types") List<Integer> types);
}
