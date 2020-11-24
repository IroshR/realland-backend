package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.ProvinceCreateRequest;
import com.teamtrace.realland.api.request.ProvinceUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface ProvinceService {
    CreationResponse createProvince(ProvinceCreateRequest request);

    UpdateResponse updateProvince(ProvinceUpdateRequest request);

    UpdateResponse approveProvince(StatusUpdateRequest request);

    UpdateResponse suspendProvince(StatusUpdateRequest request);

    UpdateResponse deleteProvince(StatusUpdateRequest request);
}
