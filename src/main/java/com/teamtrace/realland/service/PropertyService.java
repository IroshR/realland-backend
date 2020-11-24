package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.GetByPrimaryIdRequest;
import com.teamtrace.realland.api.request.PropertyCreateRequest;
import com.teamtrace.realland.api.request.PropertyUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface PropertyService {
    CreationResponse createProperty(PropertyCreateRequest request);

    UpdateResponse updateProperty(PropertyUpdateRequest request);

    ApiSearchResponse getPropertyByPropertyId(GetByPrimaryIdRequest request);

    UpdateResponse approveProperty(StatusUpdateRequest request);

    UpdateResponse suspendProperty(StatusUpdateRequest request);

    UpdateResponse deleteProperty(StatusUpdateRequest request);
}
