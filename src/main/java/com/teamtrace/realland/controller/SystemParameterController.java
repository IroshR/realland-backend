package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.SystemParameterCreateRequest;
import com.teamtrace.realland.api.request.SystemParameterUpdateRequest;
import com.teamtrace.realland.search.systemparameter.SystemParameterSearch;
import com.teamtrace.realland.service.SystemParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("system_parameters")
public class SystemParameterController extends AbstractController {

    @Autowired
    private SystemParameterService systemParameterServiceImpl;
    @Autowired
    private SystemParameterSearch systemParameterSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createSystemParameter(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody SystemParameterCreateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(systemParameterServiceImpl.createSystemParameter(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSystemParameter(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody SystemParameterUpdateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(systemParameterServiceImpl.updateSystemParameter(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findsyStemParametersByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestBody SearchCriteria api,
                                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(systemParameterSearch.findByCriteria(api), servletResponse);
    }
}
