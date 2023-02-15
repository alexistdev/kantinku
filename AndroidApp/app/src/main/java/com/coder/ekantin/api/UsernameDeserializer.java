package com.coder.ekantin.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class UsernameDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return json.getAsJsonObject().get("name").getAsString();
    }
}
