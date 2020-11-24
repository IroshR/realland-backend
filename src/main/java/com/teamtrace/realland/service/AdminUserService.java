package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.api.response.AdminUserLoginResponse;
import com.teamtrace.realland.api.response.ApiSearchResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;

import java.security.GeneralSecurityException;

public interface AdminUserService {
    CreationResponse createAdminUser(AdminUserCreateRequest request) throws GeneralSecurityException;

    UpdateResponse updateAdminUserBasicDetails(AdminUserBasicDetailsUpdateRequest request);

    UpdateResponse updateAdminUserProfileImage(AdminUserProfileImageUpdateRequest request);

    ApiSearchResponse getAdminUserByUserId(GetByPrimaryIdRequest request);

    UpdateResponse changeAdminUserPassword(AdminUserChangePasswordRequest request);

    UpdateResponse approveAdminUser(StatusUpdateRequest request);

    UpdateResponse suspendAdminUser(StatusUpdateRequest request);

    UpdateResponse deleteAdminUser(StatusUpdateRequest request);

    AdminUserLoginResponse loginAdminUser(AdminUserLoginRequest request);

    UpdateResponse logoutAdminUser(AdminUserLogoutRequest request);

}
