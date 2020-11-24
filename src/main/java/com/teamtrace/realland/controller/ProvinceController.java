package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.ProvinceCreateRequest;
import com.teamtrace.realland.api.request.ProvinceUpdateRequest;
import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.search.province.ProvinceSearch;
import com.teamtrace.realland.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("provinces")
public class ProvinceController extends AbstractController {
    @Autowired
    private ProvinceService provinceServiceImpl;
    @Autowired
    private ProvinceSearch provinceSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createProvince(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody ProvinceCreateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(provinceServiceImpl.createProvince(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProvince(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody ProvinceUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(provinceServiceImpl.updateProvince(api), servletResponse);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveProvince(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(provinceServiceImpl.approveProvince(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendProvince(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(provinceServiceImpl.suspendProvince(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteProvince(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody StatusUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(provinceServiceImpl.deleteProvince(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findProvincesByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                     @RequestBody SearchCriteria api,
                                                     HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(provinceSearch.findByCriteria(api), servletResponse);
    }
}
