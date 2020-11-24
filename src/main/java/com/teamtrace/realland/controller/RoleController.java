package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.Request;
import com.teamtrace.realland.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("roles")
public class RoleController extends AbstractController {
    @Autowired
    private RoleService roleServiceImpl;

    @RequestMapping(value = "merchant_id/{merchantId}", method = RequestMethod.GET)
    public ResponseEntity<?> getApprovedRolesByMerchantId(@RequestHeader HttpHeaders httpHeaders,
                                                          @PathVariable int merchantId,
                                                          HttpServletResponse servletResponse) {
        Request api = new Request();
        api.setMerchantId(merchantId);

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleServiceImpl.getApprovedRolesByMerchantId(api), servletResponse);
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleServiceImpl.getApprovedRolesByMerchantId(api), servletResponse);
        }
        servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }
}
