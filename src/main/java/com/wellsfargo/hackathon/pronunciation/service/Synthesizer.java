package com.wellsfargo.hackathon.pronunciation.service;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;

public class Synthesizer {

    public static ByteString synthesize(String text, String lang, SsmlVoiceGender gender) throws IOException {
        TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();
        SynthesisInput synthesisInput = SynthesisInput.newBuilder().setText(text).build();

        // Select the type of audio file you want returned
        AudioConfig audioConfig =
                AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

        // Build the voice request, select the language code ("en-US") and the ssml voice gender
        // ("neutral")
        VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                        .setLanguageCode(lang)
                        .setSsmlGender(gender)
                        .build();

        // Perform the text-to-speech request on the text input with the selected voice parameters and
        // audio file type
        SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(synthesisInput, voice, audioConfig);

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();

        return audioContents;
    }
}