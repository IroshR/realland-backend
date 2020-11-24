package com.teamtrace.realland.api;

import java.util.List;

public class RoleGroupRoleApi {
    private List<Integer> unassignedRoles;
    private List<Integer> assignedRoles;

    public List<Integer> getUnassignedRoles() {
        return unassignedRoles;
    }

    public void setUnassignedRoles(List<Integer> unassignedRoles) {
        this.unassignedRoles = unassignedRoles;
    }

    public List<Integer> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<Integer> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }
}
