package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.SystemParameterCreateRequest;
import com.teamtrace.realland.api.request.SystemParameterUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface SystemStatementService {
    CreationResponse createSystemStatement(SystemParameterCreateRequest request);

    UpdateResponse updateSystemStatement(SystemParameterUpdateRequest request);
}
