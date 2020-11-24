package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.GetByPrimaryIdRequest;
import com.teamtrace.realland.api.request.MerchantCreateRequest;
import com.teamtrace.realland.api.request.MerchantUpdateRequest;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

import java.security.GeneralSecurityException;

public interface MerchantService {
    CreationResponse createMerchant(MerchantCreateRequest request) throws GeneralSecurityException;

    UpdateResponse updateMerchant(MerchantUpdateRequest request);

    ApiSearchResponse getMerchantById(GetByPrimaryIdRequest request);

    UpdateResponse approveMerchant(StatusUpdateRequest request);

    UpdateResponse suspendMerchant(StatusUpdateRequest request);

    UpdateResponse deleteMerchant(StatusUpdateRequest request);
}
