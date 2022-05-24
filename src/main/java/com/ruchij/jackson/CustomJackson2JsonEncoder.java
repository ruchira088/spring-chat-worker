package com.ruchij.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;

public class CustomJackson2JsonEncoder extends Jackson2JsonEncoder {
    public CustomJackson2JsonEncoder(ObjectMapper mapper, MimeType... mimeTypes) {
        super(mapper, mimeTypes);
    }
}
