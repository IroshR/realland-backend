package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("web")
public class WebController extends AbstractController {
    @Autowired
    private ClientService clientServiceImpl;

    @RequestMapping(value = "client", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@RequestHeader HttpHeaders httpHeaders,
                                          @RequestBody ClientCreateRequest api,
                                          HttpServletResponse servletResponse) {

        return processResponse(clientServiceImpl.createClient(api), servletResponse);
    }

    @RequestMapping(value = "client/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginClient(@RequestHeader HttpHeaders httpHeaders,
                                         @RequestBody ClientLoginRequest api,
                                         HttpServletResponse servletResponse) {

        return processResponse(clientServiceImpl.loginClient(api), servletResponse);
    }

    @RequestMapping(value = "client/basic_details", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateClientBasicDetails(@RequestHeader HttpHeaders httpHeaders,
                                                      @RequestBody ClientBasicDetailsUpdateRequest api,
                                                      HttpServletResponse servletResponse) {
        if (!isAuthorizedClient(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.updateClientBasicDetails(api), servletResponse);
    }

    @RequestMapping(value = "client/profile_image", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateClientProfileImage(@RequestHeader HttpHeaders httpHeaders,
                                                      @RequestBody ClientProfileImageUpdate api,
                                                      HttpServletResponse servletResponse) {
        if (!isAuthorizedClient(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.updateClientProfileImage(api), servletResponse);
    }

    @RequestMapping(value = "client/change_password", method = RequestMethod.PATCH)
    public ResponseEntity<?> changePassword(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody ClientChangePasswordRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedClient(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(clientServiceImpl.changePassword(api), servletResponse);
    }
}
