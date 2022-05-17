package com.wellsfargo.hackathon.pronunciation.web;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.protobuf.ByteString;
import com.wellsfargo.hackathon.pronunciation.service.PronunciationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Tag(name = "_Standard pronunciations")
@RestController
@RequestMapping("/bit24/api/pronunciation/standard")
public class StandardPronunciationController {

    public static final int BYTE_RANGE = 128; // increase the byte range from here
    private static final Logger LOGGER = Logger.getLogger(StandardPronunciationController.class.getName());
    private PronunciationService pronunciationService;

    public StandardPronunciationController(PronunciationService pronunciationService) {
        this.pronunciationService = pronunciationService;
    }

    @CrossOrigin("*")
    @GetMapping
    public Mono<ResponseEntity<byte[]>> streamAudio(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @RequestParam("userName") String userName,
                                                    @RequestParam("lang") String lang,
                                                    @RequestParam("country") String country,
                                                    @RequestParam("gender") String gender) {

        LOGGER.info("Pronouncing " + userName + " by " + gender + " in " + lang + " in " + country);

        ByteString byteString;
        SsmlVoiceGender ssmlVoiceGender;

        if (StringUtils.isBlank(userName)) {
            userName = "Greetings from Pronunciation API!";
        }
        if (StringUtils.isBlank(lang)) {
            lang = "en";
        }
        if (StringUtils.isBlank(country)) {
            country = "US";
        }
        if (StringUtils.isBlank(gender)) {
            ssmlVoiceGender = SsmlVoiceGender.NEUTRAL;
        } else {
            if (gender.equalsIgnoreCase("male")) {
                ssmlVoiceGender = SsmlVoiceGender.MALE;
            } else if (gender.equalsIgnoreCase("female")) {
                ssmlVoiceGender = SsmlVoiceGender.FEMALE;
            } else {
                ssmlVoiceGender = SsmlVoiceGender.NEUTRAL;
            }
        }

        final String langCode = lang + "-" + country;
        byteString = pronunciationService.pronounceOnDemand(userName, langCode, ssmlVoiceGender);

        //        switch (gender.toLowerCase(Locale.ROOT)) {
        //            case "female":
        //                byteString = pronunciationService.pronounceUSEnglishGenderFemale(text);
        //                break;
        //            case "male":
        //                byteString = pronunciationService.pronounceUSEnglishGenderMale(text);
        //                break;
        //            default:
        //                byteString = pronunciationService.pronounceUSEnglishGenderNeutral(text);
        //                break;
        //        }

        //        pronunciationService.pronounceUSEnglishGenderNeutral(text);

        return Mono.just(getContent(byteString, httpRangeList));
    }

    public static ResponseEntity<byte[]> getContent(ByteString audioContents, String range) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        int dataSize = audioContents.size();
        try {

            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", "audio/mp3")
                        .header("Content-Length", String.valueOf(dataSize))
                        .body(readByteRange(audioContents, rangeStart, dataSize - 1));
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = dataSize - 1;
            }
            if (dataSize < rangeEnd) {
                rangeEnd = dataSize - 1;
            }
            data = readByteRange(audioContents, rangeStart, rangeEnd);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "audio/mp3")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", contentLength)
                .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + dataSize)
                .body(data);
    }

    public static byte[] readByteRange(ByteString audioContents, long start, long end) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(audioContents.toByteArray());
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }

}
