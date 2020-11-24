package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.search.rolegroup.RoleGroupListSearch;
import com.teamtrace.realland.search.rolegroup.RoleGroupSearch;
import com.teamtrace.realland.service.RoleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("role_groups")
public class RoleGroupController extends AbstractController {
    @Autowired
    private RoleGroupService roleGroupServiceImpl;
    @Autowired
    private RoleGroupListSearch roleGroupListSearch;
    @Autowired
    private RoleGroupSearch roleGroupSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createRoleGroup(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody RoleGroupCreateRequest api,
                                             HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.createRoleGroup(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.createRoleGroup(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRoleGroup(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody RoleGroupUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.updateRoleGroup(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.updateRoleGroup(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveRoleGroup(@RequestHeader HttpHeaders httpHeaders,
                                              @RequestBody StatusUpdateRequest api,
                                              HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.approveRoleGroup(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.approveRoleGroup(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendRoleGroup(@RequestHeader HttpHeaders httpHeaders,
                                              @RequestBody StatusUpdateRequest api,
                                              HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.suspendRoleGroup(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.suspendRoleGroup(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteRoleGroup(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.deleteRoleGroup(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.deleteRoleGroup(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "{merchantId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoleGroupList(@RequestHeader HttpHeaders httpHeaders,
                                              @PathVariable int merchantId,
                                              HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();
        api.setMerchantId(merchantId);

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processDataSearchResponse(roleGroupListSearch.findByCriteria(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processDataSearchResponse(roleGroupListSearch.findByCriteria(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findRoleGroupsByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                      @RequestBody SearchCriteria api,
                                                      HttpServletResponse servletResponse) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(roleGroupSearch.findByCriteria(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(roleGroupSearch.findByCriteria(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "roles", method = RequestMethod.PATCH)
    public ResponseEntity<?> getRoleGroupRolesByRoleGroupId(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestBody RoleGroupRolesFindRequest api,
                                                            HttpServletResponse servletResponse) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.getRoleGroupRolesByRoleGroupId(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.getRoleGroupRolesByRoleGroupId(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }
}
