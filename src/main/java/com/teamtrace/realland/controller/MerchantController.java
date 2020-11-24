package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.search.merchant.MerchantSearch;
import com.teamtrace.realland.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("merchants")
public class MerchantController extends AbstractController {
    @Autowired
    private MerchantService merchantServiceImpl;
    @Autowired
    private MerchantSearch merchantSearch;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createMerchant(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody MerchantCreateRequest api,
                                            HttpServletResponse servletResponse) throws GeneralSecurityException {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.createMerchant(api), servletResponse);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMerchant(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody MerchantUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.updateMerchant(api), servletResponse);
    }

    @RequestMapping(value = "{merchantId}", method = RequestMethod.GET)
    public ResponseEntity<?> updateMerchant(@RequestHeader HttpHeaders httpHeaders,
                                            @PathVariable int merchantId,
                                            HttpServletResponse servletResponse) {
        GetByPrimaryIdRequest request = new GetByPrimaryIdRequest();
        request.setPrimaryId(merchantId);

        if (!isAuthorizedSystemOwner(httpHeaders, request)) {
            servletResponse.setHeader(ETAG, request.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.getMerchantById(request), servletResponse);
    }

    @RequestMapping(value = "approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveMerchant(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.approveMerchant(api), servletResponse);
    }

    @RequestMapping(value = "suspend", method = RequestMethod.PATCH)
    public ResponseEntity<?> suspendMerchant(@RequestHeader HttpHeaders httpHeaders,
                                             @RequestBody StatusUpdateRequest api,
                                             HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.suspendMerchant(api), servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteMerchant(@RequestHeader HttpHeaders httpHeaders,
                                            @RequestBody StatusUpdateRequest api,
                                            HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(merchantServiceImpl.deleteMerchant(api), servletResponse);
    }

    @RequestMapping(value = "find_by_criteria", method = RequestMethod.POST)
    public ResponseEntity<?> findMerchantsByCriteria(@RequestHeader HttpHeaders httpHeaders,
                                                     @RequestBody SearchCriteria api,
                                                     HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processSearchResponse(merchantSearch.findByCriteria(api), servletResponse);
    }
}
