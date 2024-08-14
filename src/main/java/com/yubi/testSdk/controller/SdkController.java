package com.yubi.testSdk.controller;



import com.fasterxml.jackson.databind.ObjectMapper;

import com.yubi.yubidrive.sdk.client.YubiDriveClient;
import com.yubi.yubidrive.sdk.model.others.DocumentFilterReqParam;
import com.yubi.yubidrive.sdk.model.request.*;
import com.yubi.yubidrive.sdk.model.response.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("file")
public class SdkController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SdkController.class);

    @Autowired
    private YubiDriveClient yubiDriveClient;

    @PostMapping(value = "")
    public ResponseEntity<FileUploadResponse> post(
            @RequestBody CreateFileRequest createFileRequest
            ) {
        FileUploadResponse response = yubiDriveClient.createFile(createFileRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}/getFile")
    public ResponseEntity<GetFileResponse> getFile(
            @PathVariable String id,
            @RequestParam(required = false, name = "expiry_time") Long expiryTime
    ) {

        GetFileRequest getFileRequest = new GetFileRequest();
        getFileRequest.setId(id);
        getFileRequest.setExpiryTime(expiryTime);


        GetFileResponse response = yubiDriveClient.getFileDownloadUrl(getFileRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<GetFileDetailsResponse> getFileDetails(
            @PathVariable String id,
            @RequestParam(required = false, name = "expiry_time") Long expiryTime
    ) {

        GetFileRequest getFileRequest = new GetFileRequest();
        getFileRequest.setId(id);
        getFileRequest.setExpiryTime(expiryTime);
        GetFileDetailsResponse response = yubiDriveClient.getFileDetails(getFileRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "document")
    public ResponseEntity<DocumentDetailResponse> postDocumentData (
             @Valid @RequestBody DocumentCreateRequest documentCreateRequest
            ) {
        DocumentDetailResponse response = yubiDriveClient.createDocument(documentCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "document/{id}")
    public ResponseEntity<DocumentDetailResponse> getDocumentById(
            @PathVariable String id
    ) {
        DocumentDetailResponse response = yubiDriveClient.getDocumentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "document/{id}")
    public ResponseEntity<DocumentDetailResponse> updateDocumentDetails(
            @PathVariable String id,
            @Valid @RequestBody DocumentUpdateRequest documentUpdateRequest
    ) {
        DocumentDetailResponse response = yubiDriveClient.updateDocument(id, documentUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "document/list")
    public ResponseEntity<DocumentDetailsPagedResponse> getDocuments(
            @RequestBody DocumentFilterReqParam documentFilterReqParam
    ) {
        DocumentDetailsPagedResponse response = yubiDriveClient.getDocuments(documentFilterReqParam);
        LOGGER.info("Received request for document fetch list, documentFilterReqParam :: {}", documentFilterReqParam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "document/{id}")
    public ResponseEntity<CommonStatusResponse> deleteDocumentById(
            @PathVariable String id
    ) {
        CommonStatusResponse response = yubiDriveClient.deleteDocumentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "document/add-tag")
    public ResponseEntity<DocumentDetailResponse> addTagTOExistingDocument(
            @RequestBody DocumentTagAddRequest documentTagAddRequest
            ) {
        DocumentDetailResponse response = yubiDriveClient.addTagToDocument(documentTagAddRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "document/delete-tag")
    public ResponseEntity<DocumentDetailResponse> removeTagFromExistingDocument(
            @RequestBody DocumentTagRemoveRequest documentTagRemoveRequest
    ) {
        DocumentDetailResponse response = yubiDriveClient.removeTagFromDocument(documentTagRemoveRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "document/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDetailResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestPart("createFileRequest") String createFileRequestJson,
            @RequestPart("documentCreateRequest") String documentCreateRequestJson
    ) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        CreateFileRequest createFileRequest = mapper.readValue(createFileRequestJson, CreateFileRequest.class);
        DocumentCreateRequest documentCreateRequest = mapper.readValue(documentCreateRequestJson, DocumentCreateRequest.class);
        DocumentDetailResponse response = yubiDriveClient.createFileAndDocument(file, createFileRequest, documentCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "fileUpload")
    public ResponseEntity<DocumentDetailResponse> fileUploadWithInternalDocument(
            @RequestParam("file") MultipartFile file
    ) throws IOException, InterruptedException {
        DocumentDetailResponse response = yubiDriveClient.uploadFileWithDefaultDocument(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
