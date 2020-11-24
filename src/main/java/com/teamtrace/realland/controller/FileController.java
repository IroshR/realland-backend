package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.FileDeleteRequest;
import com.teamtrace.realland.api.request.Request;
import com.teamtrace.realland.api.response.FileUploadResponse;
import com.teamtrace.realland.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("flies")
public class FileController extends AbstractController {
    @Autowired
    private FileService fileServiceImpl;

    @RequestMapping(value = "upload/{merchantId}/{primaryId}/{category}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadImage(@RequestHeader HttpHeaders httpHeaders,
                                         @RequestPart("file") final MultipartFile multipartFile,
                                         @PathVariable int merchantId, @PathVariable int primaryId,
                                         @PathVariable String category, HttpServletResponse servletResponse) {

        Request request = new Request();
        request.setMerchantId(merchantId);

        FileUploadResponse response = fileServiceImpl.uploadImageS3(multipartFile, merchantId, primaryId, category);
        return processResponse(response, servletResponse);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteImage(@RequestHeader HttpHeaders httpHeaders, @RequestBody FileDeleteRequest api,
                                         HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(fileServiceImpl.deleteImageS3(api), servletResponse);
    }
}
