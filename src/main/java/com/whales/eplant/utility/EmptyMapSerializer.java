package com.whales.eplant.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class EmptyMapSerializer extends JsonSerializer<Map<?, ?>> {
    @Override
    public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null || value.isEmpty()) {
            gen.writeStartObject();
            gen.writeEndObject();
        } else {
            gen.writeObject(value);
        }
    }
}