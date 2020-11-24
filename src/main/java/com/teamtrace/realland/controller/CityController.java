package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.CityCreateRequest;
import com.teamtrace.realland.api.request.CityUpdateRequest;
import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.search.city.CitySearch;
import com.teamtrace.realland.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("cities")
public class CityController extends AbstractController {
    @Autowired
    private CityService cityServiceImpl;
    @Autowired
    private CitySearch citySearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createCity(@RequestHeader HttpHeaders httpHeaders,
                                        @RequestBody CityCreateRequest api,
                                        HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(cityServiceImpl.createCity(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCity(@RequestHeader HttpHeaders httpHeaders,
                                        @RequestBody CityUpdateRequest api,
                                        HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(cityServiceImpl.updateCity(api), servletResponse);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveCity(@RequestHeader HttpHeaders httpHeaders,
                                         @RequestBody StatusUpdateRequest api,
                                         HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(cityServiceImpl.approveCity(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendCity(@RequestHeader HttpHeaders httpHeaders,
                                         @RequestBody StatusUpdateRequest api,
                                         HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(cityServiceImpl.suspendCity(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteCity(@RequestHeader HttpHeaders httpHeaders,
                                        @RequestBody StatusUpdateRequest api,
                                        HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(cityServiceImpl.deleteCity(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findCitiesByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                  @RequestBody SearchCriteria api,
                                                  HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(citySearch.findByCriteria(api), servletResponse);
    }
}
