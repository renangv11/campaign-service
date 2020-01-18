package com.visconde.campaignservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String serializer(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar objeto.");
        }
    }

    public static <T> T desserializer(String json, Class clazz){
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar json.");
        }
    }


}
