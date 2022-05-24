package com.ruchij.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeType;

public class CustomJackson2JsonDecoder extends Jackson2JsonDecoder {
    public CustomJackson2JsonDecoder(ObjectMapper mapper, MimeType... mimeTypes) {
        super(mapper, mimeTypes);
    }
}
