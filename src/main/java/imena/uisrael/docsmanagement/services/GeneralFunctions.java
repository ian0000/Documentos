package imena.uisrael.docsmanagement.services;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.model.Organigrama;

public class GeneralFunctions {

    public static ResponseEntity<Object> convertJSON(Object objeto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String accessTokenJson = objectMapper.writeValueAsString(objeto);
            return ResponseEntity.ok(accessTokenJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallo al generar json");
        }
    }

    
    public static String ConverToString(Object objeto) {
        ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(objeto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        
    }
    
    public static Object ConverToObject(String objeto, Class<?> objectClass) {
        ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(objeto, objectClass);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        
    }

}
