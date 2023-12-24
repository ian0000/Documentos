package imena.uisrael.docsmanagement.model;

import imena.uisrael.docsmanagement.services.GeneralFunctions;

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

}
