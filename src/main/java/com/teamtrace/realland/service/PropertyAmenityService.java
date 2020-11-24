package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.PropertyAmenityCreateRequest;
import com.teamtrace.realland.api.request.PropertyAmenityUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface PropertyAmenityService {
    CreationResponse createPropertyAmenity(PropertyAmenityCreateRequest request);

    UpdateResponse updatePropertyAmenity(PropertyAmenityUpdateRequest request);

    UpdateResponse approvePropertyAmenity(StatusUpdateRequest request);

    UpdateResponse suspendPropertyAmenity(StatusUpdateRequest request);

    UpdateResponse deletePropertyAmenity(StatusUpdateRequest request);
}
