package imena.uisrael.docsmanagement.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.DTO.ObjetoParametros.Documento;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParamGenerics;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.Placeholder;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public static ParametrosJson converParamToJSON(Parametros param) {
        byte[] blob = param.getJson();
        String jsonString = new String(blob, StandardCharsets.UTF_8);

        // Parse the JSON string manually
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject documentsJson = jsonObject.getJSONObject("documents");
        JSONObject genericsJson = jsonObject.getJSONObject("generales");

        byte[] document = documentsJson.getString("document").getBytes();
        String documentName = documentsJson.getString("documentName");

        JSONArray placeholdersArray = jsonObject.getJSONArray("placeholders");

        // Create a list to store Placeholder objects
        List<Placeholder> placeholdersList = new ArrayList<>();
        for (int i = 0; i < placeholdersArray.length(); i++) {
            JSONObject placeholderJson = placeholdersArray.getJSONObject(i);

            // Extract values from the JSON object
            String placeholderName = placeholderJson.getString("placeholderName");
            String placeholderValue = "";
            if (placeholderJson.has("placeholderValue") && !placeholderJson.isNull("placeholderValue")) {
                placeholderValue = placeholderJson.getString("placeholderValue");
            }
            byte[] placeholderValueImagen = null; // Handle null case appropriately
            if (placeholderJson.has("placeholderValueImagen") && !placeholderJson.isNull("placeholderValueImagen")) {
                // Handle the case when placeholderValueImagen is not null
                placeholderValueImagen = placeholderJson.getString("placeholderValueImagen").getBytes();
            }

            // Assuming ParamGenerics is a simple class with default constructor
            JSONObject parametrosPlaceholderJson = placeholderJson.getJSONObject("parametros");
            String font = parametrosPlaceholderJson.isNull("font") ? null : parametrosPlaceholderJson.getString("font");
            int fontSize = parametrosPlaceholderJson.isNull("fontSize") ? 0
                    : parametrosPlaceholderJson.getInt("fontSize");
            String fontColor = parametrosPlaceholderJson.isNull("fontColor") ? null
                    : parametrosPlaceholderJson.getString("fontColor");

            // Create a ParamGenerics object
            ParamGenerics parametrosinternos = new ParamGenerics(font, fontSize, fontColor);

            // Create a Placeholder object and add it to the list
            Placeholder placeholder = new Placeholder(placeholderName, placeholderValue, placeholderValueImagen,
                    parametrosinternos);
            placeholdersList.add(placeholder);
        }
        Documento d = new Documento(documentName, document);
        String font = genericsJson.isNull("font") ? null : genericsJson.getString("font");
        int fontSize = genericsJson.isNull("fontSize") ? 0
                : genericsJson.getInt("fontSize");
        String fontColor = genericsJson.isNull("fontColor") ? null : genericsJson.getString("fontColor");
        ParamGenerics g = new ParamGenerics(font, fontSize, fontColor);
        ParametrosJson parametrosJson = new ParametrosJson();

        parametrosJson.generales = g;
        parametrosJson.documents = d;
        parametrosJson.placeholders = placeholdersList;
        parametrosJson.nombreParametro = param.getNombreParametro();
        return parametrosJson;
    }
}
