package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.PropertyCreateRequest;
import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.property.PropertyGridBySystemOwnerSearch;
import com.teamtrace.realland.search.property.PropertyGridSearch;
import com.teamtrace.realland.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("property")
public class PropertyController extends AbstractController {
    @Autowired
    private PropertyService propertyServiceImpl;
    @Autowired
    private PropertyGridBySystemOwnerSearch propertyGridBySystemOwnerSearch;
    @Autowired
    private PropertyGridSearch propertyGridSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createProperty(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody PropertyCreateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedAdminUser(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
        return processResponse(propertyServiceImpl.createProperty(api), servletResponse);

    }

    @RequestMapping(value = "find_by_criteria/system_owner", method = RequestMethod.POST)
    public ResponseEntity<?> findPropertiesBySystemOwnerByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                                   @RequestBody SearchCriteria api,
                                                                   HttpServletResponse servletResponse) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(propertyGridBySystemOwnerSearch.findByCriteria(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(propertyGridBySystemOwnerSearch.findByCriteria(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findPropertiesByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                      @RequestBody SearchCriteria api,
                                                      HttpServletResponse servletResponse) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(propertyGridSearch.findByCriteria(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(propertyGridSearch.findByCriteria(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }
}
