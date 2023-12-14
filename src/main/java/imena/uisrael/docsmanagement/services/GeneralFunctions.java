package imena.uisrael.docsmanagement.services;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneralFunctions {

    public static ResponseEntity<Object> convertJSOn(Object objeto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String accessTokenJson = objectMapper.writeValueAsString(objeto);
            return ResponseEntity.ok(accessTokenJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallo al generar json");
        }
    }
    // serializa la cadena de texto
    // public static byte[] serializeString(String password) {
    // try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // ObjectOutput out = new ObjectOutputStream(bos)) {
    // out.writeObject(password);
    // log.atInfo().log("Serializado" + out.toString());
    // log.atInfo().log("Serializado" + bos.toByteArray());

    // return bos.toByteArray();
    // } catch (IOException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }

    // // deserializa los bytes en un string
    // public static String deserializeString(byte[] serializedData) {
    // try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
    // ObjectInput in = new ObjectInputStream(bis)) {
    // return (String) in.readObject();
    // } catch (IOException | ClassNotFoundException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
}
