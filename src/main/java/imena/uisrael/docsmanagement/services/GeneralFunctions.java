package imena.uisrael.docsmanagement.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;

public class GeneralFunctions {

    public static ResponseEntity<Object> convertJSON(Object objeto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(objeto);
            return ResponseEntity.ok(json);
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
            if (List.class.isAssignableFrom(objectClass)) {
                return objectMapper.readValue(objeto, new TypeReference<List<Object>>() {
                });
            } else {
                // Deserialize the JSON string into a single object of the specified class
                return objectMapper.readValue(objeto, objectClass);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static ResponseEntity<Object> DevolverRespuesta(String resultado, Class<?> objectClass,
            Map<String, HttpStatus> responseHash) {
        if (resultado != null && !resultado.isEmpty() && !responseHash.containsKey(resultado)) {
            return GeneralFunctions.convertJSON(GeneralFunctions.ConverToObject(resultado, objectClass));
        } else {
            HttpStatus httpStatus = responseHash.get(resultado);
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(resultado, httpStatus);
            return responseEntity;
        }
    }

    public static String verificarCorreoPassword(String email, String password) {
        if (email == null || email.isBlank() || email.isEmpty()) {
            return RespuestasUsuarios.USUARIOEMAILBLANCO;
        }
        if (email.length() > 50) {
            return RespuestasUsuarios.EMAILLARGO;
        }
        if (password == null || password.isBlank() || password.isEmpty()) {
            return RespuestasUsuarios.USUARIOPASSWORDBLANCO;
        }
        if (password.length() > 50) {
            return RespuestasUsuarios.PASWORDLARGO;
        }
        return "";
    }
}
