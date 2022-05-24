package com.ruchij.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JsonObjectMapper {
    public static ObjectMapper objectMapper =
        new ObjectMapper().registerModule(new JodaModule());
}
