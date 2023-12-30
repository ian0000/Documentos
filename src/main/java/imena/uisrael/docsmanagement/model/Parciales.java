package imena.uisrael.docsmanagement.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Parciales {

    public static class RespuestasOrganigrama {
        public static String USUARIO0 = "EL USUARIO PRINCIPAL DE NIVEL 0 YA EXISTE, SOLO PUEDE EXISTIR UNO.";
        public static String USUARIOEXISTE = "EL USUARIO YA HA SIDO ASIGNADO UN NIVEL.";
        public static String FALLOGUARDADO = "FALLO AL GUARDAR.";
        public static String FALLOPADRE = "FALLO AL GUARDAR. SE DEBE ASIGNAR UN USUARIO PADRE.";
        public static String FALLOFALTAPADRE = "NO SE ENCONTRO CODIGO DE PADRE ASIGNADO. NO ES POSIBLE GUARDAR.";
        public static String FALLOSENECESITAREGISTRO0 = "NO EXISTEN REGISTROS, SE NECESITA UN USUARIO CON NIVEL 0. NO ES POSIBLE GUARDAR.";
        public static String FALLOCAMPONIVEL = "SE INGRESO UN VALOR INCORRECTO EN EL CAMPO NIVEL, PORFAVOR INGRESE UN NUMERO.";
        // public static Set<String> validResponses = new HashSet<>(Arrays.asList(
        // RespuestasOrganigrama.USUARIO0,
        // RespuestasOrganigrama.USUARIOEXISTE,
        // RespuestasOrganigrama.FALLOGUARDADO,
        // RespuestasOrganigrama.FALLOPADRE,
        // RespuestasOrganigrama.FALLOSENECESITAREGISTRO0,
        // RespuestasOrganigrama.FALLOFALTAPADRE,
        // RespuestasOrganigrama.FALLOCAMPONIVEL
        // ));
    }

    public static Map<String, HttpStatus> RespuestasOrganigramaHash = new HashMap<>();

    static {
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.USUARIO0, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.USUARIOEXISTE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOGUARDADO, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOPADRE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOFALTAPADRE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOSENECESITAREGISTRO0, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOCAMPONIVEL, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasAccessToken.TOKENNOENCONTRADO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class RespuestasAccessToken {
        public static String TOKENEXISTE = "YA EXISTE TOKEN CON ESA PALABRA CLAVE.";
        public static String FALLOGENERARTOKEN = "FALLO AL GENERAR TOKEN.";
        public static String TOKENACTIVADO = "TOKEN ACTIVADO.";
        public static String TOKENDESACTIVADO = "TOKEN DESACTIVADO.";
        public static String TOKENNOENCONTRADO = "TOKEN NO ENCONTRADO.";
        public static String PARAMETROKEYWORDNULO = "PARÁMETRO 'KEYWORD' NO PUEDE SER NULO.";
    }

    public static class RespuestasUsuarios {
        public static String USUARIOCONTRASENIAINCORRECTOS = "USUARIO Y/O CONTRASEÑA INCORRECTOS";
        public static String USUARIONUEVO = "USUARIO NUEVO.";
        public static String USUARIOEXISTE = "USUARIO YA EXISTE.";
        public static String USUARIONOENCONTRADO = "USUARIO NO ENCONTRADO.";
    }

}
