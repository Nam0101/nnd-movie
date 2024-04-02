package com.nndmove.app.ultils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;

    public static ObjectMapper get() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
            //            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //            Hibernate5Module module = new Hibernate5Module();
            //            module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
            //            module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
            //            module.enable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
            //            module.enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS);

            objectMapper.findAndRegisterModules();
        }
        return objectMapper;
    }
}
