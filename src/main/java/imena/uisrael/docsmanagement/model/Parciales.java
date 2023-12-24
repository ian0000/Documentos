package imena.uisrael.docsmanagement.model;


public class Parciales {

    public static class RespuestasOrganigrama {
        public static String USUARIO0 = "EL USUARIO PRINCIPAL DE NIVEL 0 YA EXISTE, SOLO PUEDE EXISTIR UNO.";
        public static String USUARIOEXISTE = "EL USUARIO YA EXISTE.";
        public static String FALLOGUARDADO = "FALLO AL GUARDAR.";
        public static String FALLOPADRE = "FALLO AL GUARDAR. SE DEBE ASIGNAR UN USUARIO PADRE.";
        public static String FALLOFALTAPADRE = "NO SE ENCONTRO CODIGO DE PADRE ASIGNADO. NO ES POSIBLE GUARDAR.";
        public static String FALLOSENECESITAREGISTRO0 = "NO EXISTEN REGISTROS, SE NECESITA UN USUARIO CON NIVEL 0. NO ES POSIBLE GUARDAR.";
        public static String FALLOCAMPONIVEL = "SE INGRESO UN VALOR INCORRECTO EN EL CAMPO NIVEL, PORFAVOR INGRESE UN NUMERO.";
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
