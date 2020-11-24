package com.teamtrace.realland.controller;


import com.teamtrace.realland.api.request.PropertyAmenityCreateRequest;
import com.teamtrace.realland.api.request.PropertyAmenityUpdateRequest;
import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.search.propertyamenity.PropertyAmenitySearch;
import com.teamtrace.realland.service.PropertyAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("property_amenities")
public class PropertyAmenityController extends AbstractController {
    @Autowired
    private PropertyAmenityService propertyAmenityServiceImpl;
    @Autowired
    private PropertyAmenitySearch propertyAmenitySearch;

    @RequestMapping(value = "", method = RequestMethod.POST)

    public ResponseEntity<?> createPropertyAmenity(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody PropertyAmenityCreateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(propertyAmenityServiceImpl.createPropertyAmenity(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePropertyAmenity(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody PropertyAmenityUpdateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(propertyAmenityServiceImpl.updatePropertyAmenity(api), servletResponse);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approvePropertyAmenity(@RequestHeader HttpHeaders httpHeaders,
                                                    @RequestBody StatusUpdateRequest api,
                                                    HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(propertyAmenityServiceImpl.approvePropertyAmenity(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendPropertyAmenity(@RequestHeader HttpHeaders httpHeaders,
                                                    @RequestBody StatusUpdateRequest api,
                                                    HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(propertyAmenityServiceImpl.suspendPropertyAmenity(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deletePropertyAmenity(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody StatusUpdateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(propertyAmenityServiceImpl.deletePropertyAmenity(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findPropertyAmenitiesByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                             @RequestBody SearchCriteria api,
                                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(propertyAmenitySearch.findByCriteria(api), servletResponse);
    }
}
