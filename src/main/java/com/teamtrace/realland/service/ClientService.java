package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.api.response.ClientLoginResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

public interface ClientService {
    CreationResponse createClient(ClientCreateRequest request);

    UpdateResponse updateClientBasicDetails(ClientBasicDetailsUpdateRequest request);

    UpdateResponse updateClientProfileImage(ClientProfileImageUpdate request);

    UpdateResponse changePassword(ClientChangePasswordRequest request);

    UpdateResponse approveClient(StatusUpdateRequest request);

    UpdateResponse suspendClient(StatusUpdateRequest request);

    UpdateResponse deleteClient(StatusUpdateRequest request);

    ClientLoginResponse loginClient(ClientLoginRequest request);
}
