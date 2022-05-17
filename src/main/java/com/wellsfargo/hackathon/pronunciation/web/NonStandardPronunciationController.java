package com.wellsfargo.hackathon.pronunciation.web;

import com.google.protobuf.ByteString;
import com.wellsfargo.hackathon.pronunciation.PronunciationConstants;
import com.wellsfargo.hackathon.pronunciation.service.DownloadObjectIntoMemory;
import com.wellsfargo.hackathon.pronunciation.service.UploadMultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Tag(name = "Non-standard pronunciations")
@RestController
@RequestMapping("/bit24/api/pronunciation/non-standard")
public class NonStandardPronunciationController {

    @CrossOrigin("*")
    @PostMapping
    public ResponseEntity save(@RequestParam("file") MultipartFile file,
                               @RequestParam("userName") String userName//,
                               /*@RequestParam("lang") String lang,
                               @RequestParam("country") String country,
                               @RequestParam("gender") String gender,
                               @RequestParam("docType") String docType*/) throws IOException {
//        String fileName = documneStorageService.storeFile(file, UserId, docType);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
        final String objectName = userName + /*lang + country + gender +*/ "_Pronunciation.mp3";
        UploadMultipartFile.uploadObjectFromMultipartFile(objectName, file);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("{\"status\": \"Success\"}");
//        return new PronunciationRecord(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
    }

    @CrossOrigin("*")
    @GetMapping
    public Mono<ResponseEntity<byte[]>> retrieve(
            @RequestParam("userName") String userName,
            /*@RequestParam("lang") String lang,
            @RequestParam("country") String country,
            @RequestParam("gender") String gender,
            @RequestParam("docType") String docType,*/
            @RequestHeader(value = "Range", required = false) String httpRangeList
            /*HttpServletRequest request,
                         @RequestParam(value = "file", required = false) String path,
                         HttpServletResponse response*/) {
        //        @RequestMapping(name = "file-download", path = "download",
        //                method = RequestMethod.GET)
        //        public ResponseEntity<ByteArrayResource> fileDownload(
        //    ) {
        final String objectName = userName /*+ lang + country + gender*/ + "_Pronunciation.mp3";
        byte[] pronunciation = DownloadObjectIntoMemory
                .downloadObjectIntoMemory(PronunciationConstants.PROJECT_ID,
                        PronunciationConstants.BUCKET_NAME,
                        objectName);
        //            StorageObject object = googleStorageClientAdapter.download(path);
        //
        //
        //            byte[] res = Files.toByteArray((File) object.get("file"));
        //            ByteArrayResource resource = new ByteArrayResource(res);

        //                return ResponseEntity.ok()
        //                        .contentLength(res.length)
        //                        .header("Content-type", "application/octet-stream")
        //                        .header("Content-disposition", "attachment; filename=\"" + path + "\"").body(resource);
        return Mono.just(StandardPronunciationController.getContent(ByteString.copyFrom(pronunciation), httpRangeList));
    }
}