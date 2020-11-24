package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.SystemParameterCreateRequest;
import com.teamtrace.realland.api.request.SystemParameterUpdateRequest;
import com.teamtrace.realland.search.systemstatement.SystemStatementSearch;
import com.teamtrace.realland.service.SystemStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("system_statements")
public class SystemStatementController extends AbstractController {

    @Autowired
    private SystemStatementService systemStatementServiceImpl;
    @Autowired
    private SystemStatementSearch systemStatementSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createSystemStatement(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody SystemParameterCreateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(systemStatementServiceImpl.createSystemStatement(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSystemStatement(@RequestHeader HttpHeaders httpHeaders,
                                                   @RequestBody SystemParameterUpdateRequest api,
                                                   HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(systemStatementServiceImpl.updateSystemStatement(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findSystemStatementsByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestBody SearchCriteria api,
                                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(systemStatementSearch.findByCriteria(api), servletResponse);
    }
}
