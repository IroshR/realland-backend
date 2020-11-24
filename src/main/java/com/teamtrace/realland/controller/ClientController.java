package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.api.request.StatusUpdateRequest;
import com.teamtrace.realland.search.client.ClientSearch;
import com.teamtrace.realland.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("clients")
public class ClientController extends AbstractController {
    @Autowired
    private ClientService clientServiceImpl;
    @Autowired
    private ClientSearch clientSearch;

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveClient(@RequestHeader HttpHeaders httpHeaders,
                                           @RequestBody StatusUpdateRequest api,
                                           HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.approveClient(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendClient(@RequestHeader HttpHeaders httpHeaders,
                                           @RequestBody StatusUpdateRequest api,
                                           HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.suspendClient(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteClient(@RequestHeader HttpHeaders httpHeaders,
                                          @RequestBody StatusUpdateRequest api,
                                          HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.deleteClient(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findClientssByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                    @RequestBody SearchCriteria api,
                                                    HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(clientSearch.findByCriteria(api), servletResponse);
    }
}
