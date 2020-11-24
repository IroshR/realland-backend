package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.Request;
import com.teamtrace.realland.api.response.ApiSearchResponse;

public interface RoleService {
    ApiSearchResponse getApprovedRolesByMerchantId(Request request);
}
