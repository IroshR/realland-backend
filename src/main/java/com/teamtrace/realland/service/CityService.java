package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.CityCreateRequest;
import com.teamtrace.realland.api.request.CityUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface CityService {
    CreationResponse createCity(CityCreateRequest request);

    UpdateResponse updateCity(CityUpdateRequest request);

    UpdateResponse approveCity(StatusUpdateRequest request);

    UpdateResponse suspendCity(StatusUpdateRequest request);

    UpdateResponse deleteCity(StatusUpdateRequest request);
}
