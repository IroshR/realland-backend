package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.RoleGroupCreateRequest;
import com.teamtrace.realland.api.request.RoleGroupRolesFindRequest;
import com.teamtrace.realland.api.request.RoleGroupUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface RoleGroupService {
    CreationResponse createRoleGroup(RoleGroupCreateRequest request);

    UpdateResponse updateRoleGroup(RoleGroupUpdateRequest request);

    UpdateResponse approveRoleGroup(StatusUpdateRequest request);

    UpdateResponse suspendRoleGroup(StatusUpdateRequest request);

    UpdateResponse deleteRoleGroup(StatusUpdateRequest request);

    CreationResponse getRoleGroupRolesByRoleGroupId(RoleGroupRolesFindRequest request);
}
