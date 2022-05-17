package com.wellsfargo.hackathon.pronunciation.service;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PronunciationService {

    public static final String EN_US = "en-US";
    public static final String ES_US = "es-US";

    public ByteString pronounceUSEnglishGenderNeutral(String text) {
        try {
            return Synthesizer.synthesize(text, EN_US, SsmlVoiceGender.NEUTRAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteString pronounceUSEnglishGenderMale(String text) {
        try {
            return Synthesizer.synthesize(text, EN_US, SsmlVoiceGender.MALE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteString pronounceUSEnglishGenderFemale(String text) {
        try {
            return Synthesizer.synthesize(text, EN_US, SsmlVoiceGender.FEMALE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteString pronounceOnDemand(String text, String langCode, SsmlVoiceGender gender) {
        try {
            return Synthesizer.synthesize(text, langCode, gender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * Demonstrates using the Text-to-Speech API.
//     */
//    public static void main(String... args) throws Exception {
//        // Instantiates a client
//        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
//            // Set the text input to be synthesized
//            final List<String> names = List.of("Lal Sah", "Arno Baccam", "Vishal Barot", "Natalie Hong", "Ryan Spice", "Cobra Cai");
////            SynthesisInput input = SynthesisInput.newBuilder().setText("Hello, World!").build();
//            synthesizeX(names, textToSpeechClient, SsmlVoiceGender.NEUTRAL);
//            synthesizeX(names, textToSpeechClient, SsmlVoiceGender.MALE);
//            synthesizeX(names, textToSpeechClient, SsmlVoiceGender.FEMALE);
//        }
//    }
//
//    private static void synthesizeX(List<String> names, TextToSpeechClient textToSpeechClient, SsmlVoiceGender ssmlVoiceGender) {
//        // Build the voice request, select the language code ("en-US") and the ssml voice gender
//        // ("neutral")
//        VoiceSelectionParams voice =
//                VoiceSelectionParams.newBuilder()
//                        .setLanguageCode("en-US")
//                        .setSsmlGender(ssmlVoiceGender)
//                        .build();
//
//        names.forEach(name -> {
//            synthesize(textToSpeechClient, voice, name);
//        });
//    }
//
//    private static void synthesize(TextToSpeechClient textToSpeechClient, VoiceSelectionParams voice, String name) {
//        SynthesisInput synthesisInput = SynthesisInput.newBuilder().setText(name).build();
//
//        // Select the type of audio file you want returned
//        AudioConfig audioConfig =
//                AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
//
//        // Perform the text-to-speech request on the text input with the selected voice parameters and
//        // audio file type
//        SynthesizeSpeechResponse response =
//                textToSpeechClient.synthesizeSpeech(synthesisInput, voice, audioConfig);
//
//        // Get the audio contents from the response
//        ByteString audioContents = response.getAudioContent();
//
//        // Write the response to the output file.
//        final String audioFileName = "output_" + name + "_" + voice.getSsmlGender().name() + ".mp3";
//        try (OutputStream out = new FileOutputStream(audioFileName /*"output.mp3"*/)) {
//            out.write(audioContents.toByteArray());
//            System.out.println("Audio content written to file \"" + audioFileName + "\"");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
