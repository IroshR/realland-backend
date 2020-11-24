package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.search.adminuser.AdminUserSearch;
import com.teamtrace.realland.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("admin_users")
public class AdminUserController extends AbstractController {
    @Autowired
    private AdminUserService adminUserServiceImpl;
    @Autowired
    private AdminUserSearch adminUserSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createAdminUser(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody AdminUserCreateRequest api,
                                             HttpServletResponse servletResponse) throws GeneralSecurityException {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.createAdminUser(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.createAdminUser(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "basic_details", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAdminUserBasicDetails(@RequestHeader HttpHeaders httpHeaders,
                                                         @RequestBody AdminUserBasicDetailsUpdateRequest api,
                                                         HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserBasicDetails(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserBasicDetails(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "profile_image", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAdminUserProfileImage(@RequestHeader HttpHeaders httpHeaders,
                                                         @RequestBody AdminUserProfileImageUpdateRequest api,
                                                         HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserProfileImage(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserProfileImage(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "merchant_id/{merchantId}/admin_user_id/{adminUserId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAdminUserUserId(@RequestHeader HttpHeaders httpHeaders,
                                                @PathVariable int merchantId,
                                                @PathVariable int adminUserId,
                                                HttpServletResponse servletResponse) {
        GetByPrimaryIdRequest request = new GetByPrimaryIdRequest();
        request.setMerchantId(merchantId);
        request.setPrimaryId(adminUserId);

        if (isAuthorizedSystemOwner(httpHeaders, request)) {
            return processResponse(adminUserServiceImpl.getAdminUserByUserId(request), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, request)) {
            return processResponse(adminUserServiceImpl.getAdminUserByUserId(request), servletResponse);
        }
        servletResponse.setHeader(ETAG, request.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "change_password", method = RequestMethod.PATCH)
    public ResponseEntity<?> changeAdminUserPassword(@RequestHeader HttpHeaders httpHeaders,
                                                     @RequestBody AdminUserChangePasswordRequest api,
                                                     HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.changeAdminUserPassword(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.changeAdminUserPassword(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveAdminUser(@RequestHeader HttpHeaders httpHeaders,
                                              @RequestBody StatusUpdateRequest api,
                                              HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.approveAdminUser(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.approveAdminUser(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendAdminUser(@RequestHeader HttpHeaders httpHeaders,
                                              @RequestBody StatusUpdateRequest api,
                                              HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.suspendAdminUser(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.suspendAdminUser(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteAdminUser(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.deleteAdminUser(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.deleteAdminUser(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> loginAdminUser(@RequestBody AdminUserLoginRequest api,
                                            HttpServletResponse servletResponse) {

        return processResponse(adminUserServiceImpl.loginAdminUser(api), servletResponse);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<?> logoutSystemUser(@RequestHeader HttpHeaders httpHeaders,
                                              HttpServletResponse servletResponse) {
        AdminUserLogoutRequest api = new AdminUserLogoutRequest();
        if (httpHeaders != null && httpHeaders.get("sessionId") != null) {
            api.setSessionId(httpHeaders.get("sessionId").get(0));
        }

        return processResponse(adminUserServiceImpl.logoutAdminUser(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findAdminUsersByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                      @RequestBody SearchCriteria api,
                                                      HttpServletResponse servletResponse) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(adminUserSearch.findByCriteria(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(adminUserSearch.findByCriteria(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }
}
