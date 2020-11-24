package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.DistrictCreateRequest;
import com.teamtrace.realland.api.request.DistrictUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface DistrictService {
    CreationResponse createDistrict(DistrictCreateRequest request);

    UpdateResponse updateDistrict(DistrictUpdateRequest request);

    UpdateResponse approveDistrict(StatusUpdateRequest request);

    UpdateResponse suspendDistrict(StatusUpdateRequest request);

    UpdateResponse deleteDistrict(StatusUpdateRequest request);
}
