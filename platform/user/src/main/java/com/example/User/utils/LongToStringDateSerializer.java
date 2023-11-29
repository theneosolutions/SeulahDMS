package com.example.User.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongToStringDateSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(final Long value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {
        gen.writeObject(CommonUtils.dateFormatter(value));
    }
}
