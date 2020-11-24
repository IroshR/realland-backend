package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.DistrictCreateRequest;
import com.teamtrace.realland.api.request.DistrictUpdateRequest;
import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.search.district.DistrictSearch;
import com.teamtrace.realland.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("districts")
public class DistrictController extends AbstractController {
    @Autowired
    private DistrictService districtServiceImpl;
    @Autowired
    private DistrictSearch districtSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDistrict(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody DistrictCreateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(districtServiceImpl.createDistrict(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDistrict(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody DistrictUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(districtServiceImpl.updateDistrict(api), servletResponse);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveDistrict(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(districtServiceImpl.approveDistrict(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendDistrict(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(districtServiceImpl.suspendDistrict(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteDistrict(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody StatusUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(districtServiceImpl.deleteDistrict(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findDistrictsByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                     @RequestBody SearchCriteria api,
                                                     HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(districtSearch.findByCriteria(api), servletResponse);
    }
}
