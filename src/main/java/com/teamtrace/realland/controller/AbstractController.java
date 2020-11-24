package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.Request;
import com.teamtrace.realland.api.response.ApiResponse;
import com.teamtrace.realland.api.response.SearchResponse;
import com.teamtrace.realland.model.AdminUser;
import com.teamtrace.realland.model.Client;
import com.teamtrace.realland.util.SessionUtil;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {
    protected static final String ETAG = "ETag";
    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Autowired
    SessionUtil sessionUtil;

    protected boolean isAuthorizedSystemOwner(HttpHeaders httpHeaders, Request request) {
        if (httpHeaders == null || httpHeaders.get("sessionId") == null) {
            return false;
        }
        String sessionId = httpHeaders.get("sessionId").get(0);
        AdminUser adminUser = sessionUtil.getAdminUserSession(sessionId);

        /*if (adminUser == null) {
            logger.info("Try to access without valid session. Session : {}", sessionId);
            return false;
        }
        if (adminUser.getStatus() != Statuses.APPROVED) {
            logger.info("Un-approved admin. Session : {}", sessionId);
            return false;
        }
        //todo check system owner*/

        request.setAdminUser(adminUser);

        return true;
    }

    protected boolean isAuthorizedAdminUser(HttpHeaders httpHeaders, Request request) {
        if (httpHeaders == null || httpHeaders.get("sessionId") == null) {
            return false;
        }
        String sessionId = httpHeaders.get("sessionId").get(0);
        AdminUser adminUser = sessionUtil.getAdminUserSession(sessionId);

        /*
        if (adminUser == null) {
            logger.info("Try to access without valid session. Session : {}", sessionId);
            return false;
        }
        *//*if (adminUser.getMerchantId() != request.getMerchantId()) {
            logger.info("Admin not authorized for merchant. Id : {}", request.getMerchantId());
            return false;
        }*//*
        if (adminUser.getStatus() != Statuses.APPROVED) {
            logger.info("Un-approved admin. Session : {}", sessionId);
            return false;
        }*/

        request.setAdminUser(adminUser);

        return true;
    }

    protected boolean isAuthorizedClient(HttpHeaders httpHeaders, Request request) {
        if (httpHeaders == null || httpHeaders.get("sessionId") == null) {
            return false;
        }
        String sessionId = httpHeaders.get("sessionId").get(0);
        Client client = sessionUtil.getClientSession(sessionId);

        if (client == null) {
            logger.info("Try to access without valid session. Session : {}", sessionId);
            return false;
        }
        if (client.getStatus() != Statuses.APPROVED) {
            logger.info("Un-approved admin. Session : {}", sessionId);
            return false;
        }

        request.setClient(client);

        return true;
    }

    protected ResponseEntity<?> processResponse(ApiResponse response, HttpServletResponse servletResponse) {
        if (response.getStatus() == Statuses.RESPONSE_STATUS_FAIL) {
            servletResponse.setHeader(ETAG, response.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    protected ResponseEntity<?> processSearchResponse(SearchResponse response, HttpServletResponse servletResponse) {
        switch (response.getStatus()) {
            case Statuses.RESPONSE_STATUS_SUCCESS: {
                if (response.getData() == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                } else if (response.getData().isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
            case Statuses.RESPONSE_STATUS_NO_DATA: {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            case Statuses.RESPONSE_STATUS_FAIL: {
                servletResponse.setHeader(ETAG, response.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
            default: {
                servletResponse.setHeader(ETAG, response.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
    }

    protected ResponseEntity<?> processDataSearchResponse(SearchResponse searchResponse, HttpServletResponse servletResponse) {
        switch (searchResponse.getStatus()) {
            case Statuses.RESPONSE_STATUS_SUCCESS: {
                if (searchResponse.getData() == null || searchResponse.getData().isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(searchResponse.getData(), HttpStatus.OK);
                }
            }
            case Statuses.RESPONSE_STATUS_NO_DATA: {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            case Statuses.RESPONSE_STATUS_FAIL: {
                servletResponse.setHeader(ETAG, searchResponse.getMessage());
                return new ResponseEntity<>(searchResponse.getMessage(), HttpStatus.NOT_MODIFIED);
            }
            default: {
                servletResponse.setHeader(ETAG, searchResponse.getMessage());
                return new ResponseEntity<>(searchResponse.getMessage(), HttpStatus.NOT_MODIFIED);
            }
        }
    }
}
