package imena.uisrael.docsmanagement.services;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralFunctions {

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
