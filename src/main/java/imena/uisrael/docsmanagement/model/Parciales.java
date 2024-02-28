package imena.uisrael.docsmanagement.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class Parciales {

    public static class RespuestasGenerales {
        public static String JSONINCORRECTO = "HAY CAMPOS FALTANTES EN EL JSON.";

        public static String ERRORINTERNO = "ERROR INTERNO.";
    }

    public static class RespuestasUsuarios {
        public static String USUARIOCONTRASENIAINCORRECTOS = "USUARIO Y/O CONTRASEÑA INCORRECTOS";
        public static String USUARIONUEVO = "USUARIO NUEVO.";
        public static String USUARIOEXISTE = "USUARIO YA EXISTE.";
        public static String USUARIONOENCONTRADO = "USUARIO NO ENCONTRADO.";
        public static String USUARIOACTIVADO = "USUARIO ACTIVADO.";
        public static String USUARIODESACTIVADO = "USUARIO DESACTIVADO.";
        public static String USUARIOINACTIVO = "USUARIO INACTIVO.";
        public static String USUARIOEMAIL = "EMAIL INVALIDO.";

        public static String USUARIOEMAILBLANCO = "EMAIL NO PUEDE ESTAR VACIO.";
        public static String USUARIOPASSWORDBLANCO = "PASSWORD NO PUEDE ESTAR VACIA.";

        public static String EMAILLARGO = "EMAIL DEMASIADO LARGO SOLO PUEDE SER MAXIMO 50 CARACTERES";
        public static String PASWORDLARGO = "PASSWORD DEMASIADO LARGO SOLO PUEDE SER MAXIMO 50 CARACTERES";

    }

    public static class RespuestasAccessToken {
        public static String TOKENEXISTE = "YA EXISTE TOKEN CON ESA PALABRA CLAVE.";
        public static String FALLOGENERARTOKEN = "FALLO AL GENERAR TOKEN.";
        public static String TOKENACTIVADO = "TOKEN ACTIVADO.";
        public static String TOKENDESACTIVADO = "TOKEN DESACTIVADO.";
        public static String TOKENNOENCONTRADO = "TOKEN NO ENCONTRADO.";
        public static String PARAMETROKEYWORDNULO = "PARÁMETRO 'KEYWORD' NO PUEDE SER NULO.";

    }

    public static class RespuestaDepartamentos {
        public static String NOMBREDEPARTAMENTOVACIO = "SE ENVIO UN NOMBRE VACIO O INVALIDO.";
        public static String DEPARTAMENTONOENCONTRADO = "DEPARTAMENTO NO ENCONTRADO.";
        public static String DEPARTAMENTOEXISTE = "DEPARTAMENTO YA EXISTE.";
        public static String DEPARTAMENTOACTIVADO = "DEPARTAMENTO ACTIVADO.";
        public static String DEPARTAMENTODESACTIVADO = "DEPARTAMENTO DESACTIVADO.";

        public static String DEPARTAMENTOIDINVALIDO = "EL CODIGO DE DEPARTAMENTO ES INVALIDO.";

    }

    public static class RespuestasOrganigrama {
        public static String USUARIO0 = "EL USUARIO PRINCIPAL DE NIVEL 0 YA EXISTE, SOLO PUEDE EXISTIR UNO.";
        public static String USUARIOEXISTE = "EL USUARIO YA HA SIDO ASIGNADO UN NIVEL.";
        public static String FALLOPADRE = "FALLO AL GUARDAR. SE DEBE ASIGNAR UN USUARIO PADRE.";
        public static String FALLOFALTAPADRE = "NO SE ENCONTRO CODIGO DE PADRE ASIGNADO. NO ES POSIBLE GUARDAR.";
        public static String FALLOSENECESITAREGISTRO0 = "NO EXISTEN REGISTROS, SE NECESITA UN USUARIO CON NIVEL 0. NO ES POSIBLE GUARDAR.";
        public static String FALLOCAMPONIVEL = "SE INGRESO UN VALOR INCORRECTO EN EL CAMPO NIVEL, PORFAVOR INGRESE UN NUMERO.";
        public static String FALLOPADRE0 = "NO SE DEBE ASIGNAR UN CODIGO PADRE A USUARIOS NIVEL 0.";
        public static String FALLOACTUALIZAR = "ERROR AL ACTUALIZAR INTENTE NUEVAMENTE.";
        public static String FALLOGUARDAR = "ERROR AL GUARDAR INTENTE NUEVAMENTE.";
        public static String FALLONIVEL0 = "NO PUEDEN EXISTIR MAS DE UN USUARIO CON NIVEL 0.";
        public static String EXISTEUSUARIOSNIVELX = "DEBEN EXISITIR AL MENOS 2 USUARIOS CON ESTE NIVEL, O TENER LLEVARORGANIGRAMAS COMO VERDAD PARA PODER MOVER DE NIVEL ESTE ITEM.";
        public static String ORGANIGRAMANOEXISTE = "NO ES POSIBLE ACTUALIZAR.ORGANIGRAMA NO EXISTE.";
        public static String CODIGOPEROSNAVACIO = "EL CODIGO DE LA PERSONA NO PUEDE ESTAR VACIO";
        public static String NOMBREPEROSNAVACIO = "EL NOMBRE DE LA PERSONA NO PUEDE ESTAR VACIO";
        public static String NIVELVACIO = "FORMATO DE NIVEL INCORRECTO";
        public static String NIVEL0 = "NO SE PUEDE MODIFICAR EL NIVEL DE UN USUARIO NIVEL 0.";
        public static String ORGANIGRAMAACTIVADO = "ORGANIGRAMA ACTIVADO.";
        public static String ORGANIGRAMADESACTIVADO = "ORGANIGRAMA DESACTIVADO.";
        public static String ORGANIGRAMAVACIO = "NO EXISTEN REGISTROS CON EL TOKEN.";

    }

    public static class RespuestasParametros {
        public static String PARAMETRONOEXISTE = "NOMBRE PARAMETRO NO ENCONTRADO";
        public static String GENERALPARAMS = "EXISTEN CAMPOS DE PARAMETROS VACIOS.";
        public static String NOMBREPARAMETROVACIO = "NOMBRE PARAMETRO VACIO/INCORRECTO";
        public static String NOMBREPARAMETROEXISTE = "NOMBRE PARAMETRO YA HA SIDO ASIGNADO";
        public static String ERRORGUARDAR = "ERROR AL GUARDAR PARAMETROS. INTENTE NUEVAMENTE.";
        public static String ERRORTRANSFORMARJSON = "HUBO UN ERRO EL GENERAR UN JSON DE REPSUESTA";
        public static String PARAMETROACTIVADO = "PARAMETRO ACTIVADO";
        public static String PARAMETRODESACTIVADO = "PARAMETRO DESACTIVADO";
        public static String SINREGISTROSTOKEN = "NO EXISTEN REGISTROS CON EL TOKEN.";
        public static String DOCUMENTOINCORRECTO = "DOCUMENTO INCORRECTO O VACIO.";
        public static String CAMPOSDOCUMENTO = "CAMPOS DE DOCUMENTO Y CAMPOS ESPERADOS NO SON LOS MISMOS.";
    }

    public static class RespuestasDocumentos {

    }

    public static Map<String, HttpStatus> RespuestasUsuariosHash = new HashMap<>();

    static {
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS, HttpStatus.UNAUTHORIZED);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIONUEVO, HttpStatus.CREATED);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOEXISTE, HttpStatus.CONFLICT);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIONOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOINACTIVO, HttpStatus.FORBIDDEN);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOEMAIL, HttpStatus.BAD_REQUEST);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOACTIVADO, HttpStatus.OK);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIODESACTIVADO, HttpStatus.OK);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOEMAILBLANCO, HttpStatus.BAD_REQUEST);
        RespuestasUsuariosHash.put(RespuestasUsuarios.USUARIOPASSWORDBLANCO, HttpStatus.BAD_REQUEST);
        RespuestasUsuariosHash.put(RespuestasUsuarios.EMAILLARGO, HttpStatus.BAD_REQUEST);
        RespuestasUsuariosHash.put(RespuestasUsuarios.PASWORDLARGO, HttpStatus.BAD_REQUEST);

        RespuestasUsuariosHash.put(RespuestasGenerales.JSONINCORRECTO, HttpStatus.BAD_REQUEST);
        RespuestasUsuariosHash.put(RespuestasGenerales.ERRORINTERNO, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    public static Map<String, HttpStatus> RespuestasAccessTokenHash = new HashMap<>();

    static {
        RespuestasAccessTokenHash.put(RespuestasAccessToken.TOKENEXISTE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasAccessTokenHash.put(RespuestasAccessToken.FALLOGENERARTOKEN, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasAccessTokenHash.put(RespuestasAccessToken.TOKENACTIVADO, HttpStatus.OK);
        RespuestasAccessTokenHash.put(RespuestasAccessToken.TOKENDESACTIVADO, HttpStatus.OK);
        RespuestasAccessTokenHash.put(RespuestasAccessToken.TOKENNOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasAccessTokenHash.put(RespuestasAccessToken.PARAMETROKEYWORDNULO, HttpStatus.BAD_REQUEST);

        RespuestasAccessTokenHash.put(RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS, HttpStatus.NOT_FOUND);
        RespuestasAccessTokenHash.put(RespuestasUsuarios.USUARIODESACTIVADO, HttpStatus.CONFLICT);

        RespuestasAccessTokenHash.put(RespuestasGenerales.JSONINCORRECTO, HttpStatus.BAD_REQUEST);
        RespuestasAccessTokenHash.put(RespuestasGenerales.ERRORINTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static Map<String, HttpStatus> RespuestasDepartamentosHash = new HashMap<>();

    static {
        RespuestasDepartamentosHash.put(RespuestaDepartamentos.NOMBREDEPARTAMENTOVACIO, HttpStatus.BAD_REQUEST);
        RespuestasDepartamentosHash.put(RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasDepartamentosHash.put(RespuestaDepartamentos.DEPARTAMENTOEXISTE, HttpStatus.CONFLICT);
        RespuestasDepartamentosHash.put(RespuestaDepartamentos.DEPARTAMENTOACTIVADO, HttpStatus.CONFLICT);
        RespuestasDepartamentosHash.put(RespuestaDepartamentos.DEPARTAMENTODESACTIVADO, HttpStatus.CONFLICT);

        RespuestasDepartamentosHash.put(RespuestasAccessToken.TOKENNOENCONTRADO, HttpStatus.NOT_FOUND);

        RespuestasDepartamentosHash.put(RespuestasGenerales.JSONINCORRECTO, HttpStatus.BAD_REQUEST);
        RespuestasDepartamentosHash.put(RespuestasGenerales.ERRORINTERNO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public static Map<String, HttpStatus> RespuestasOrganigramaHash = new HashMap<>();

    static {
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.USUARIO0, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.USUARIOEXISTE, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOPADRE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOFALTAPADRE, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOSENECESITAREGISTRO0, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOCAMPONIVEL, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOPADRE0, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOACTUALIZAR, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLONIVEL0, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.EXISTEUSUARIOSNIVELX, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.FALLOGUARDAR, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.ORGANIGRAMANOEXISTE, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.NIVEL0, HttpStatus.CONFLICT);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.ORGANIGRAMAACTIVADO, HttpStatus.OK);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.ORGANIGRAMADESACTIVADO, HttpStatus.OK);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.ORGANIGRAMAVACIO, HttpStatus.CONFLICT);

        RespuestasOrganigramaHash.put(RespuestasGenerales.JSONINCORRECTO, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestasGenerales.ERRORINTERNO, HttpStatus.INTERNAL_SERVER_ERROR);

        RespuestasOrganigramaHash.put(RespuestasOrganigrama.CODIGOPEROSNAVACIO, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestasOrganigrama.NOMBREPEROSNAVACIO, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestaDepartamentos.DEPARTAMENTOIDINVALIDO, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestasAccessToken.TOKENNOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasOrganigramaHash.put(RespuestasAccessToken.TOKENDESACTIVADO, HttpStatus.BAD_REQUEST);

        RespuestasOrganigramaHash.put(RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasOrganigramaHash.put(RespuestaDepartamentos.DEPARTAMENTOIDINVALIDO, HttpStatus.BAD_REQUEST);
        RespuestasOrganigramaHash.put(RespuestaDepartamentos.DEPARTAMENTODESACTIVADO, HttpStatus.BAD_REQUEST);

    }

    public static Map<String, HttpStatus> RespuestasParametrosHash = new HashMap<>();
    static {
        RespuestasParametrosHash.put(RespuestasParametros.PARAMETRONOEXISTE, HttpStatus.BAD_REQUEST);
        RespuestasParametrosHash.put(RespuestasParametros.DOCUMENTOINCORRECTO, HttpStatus.BAD_REQUEST);
        RespuestasParametrosHash.put(RespuestasParametros.GENERALPARAMS, HttpStatus.BAD_REQUEST);
        RespuestasParametrosHash.put(RespuestasParametros.NOMBREPARAMETROVACIO, HttpStatus.CONFLICT);
        RespuestasParametrosHash.put(RespuestasParametros.NOMBREPARAMETROEXISTE, HttpStatus.CONFLICT);
        RespuestasParametrosHash.put(RespuestasParametros.ERRORGUARDAR, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasParametrosHash.put(RespuestasParametros.ERRORTRANSFORMARJSON, HttpStatus.INTERNAL_SERVER_ERROR);
        RespuestasParametrosHash.put(RespuestasParametros.PARAMETROACTIVADO, HttpStatus.NOT_FOUND);
        RespuestasParametrosHash.put(RespuestasParametros.PARAMETRODESACTIVADO, HttpStatus.OK);
        RespuestasParametrosHash.put(RespuestasParametros.SINREGISTROSTOKEN, HttpStatus.OK);
        RespuestasParametrosHash.put(RespuestasParametros.CAMPOSDOCUMENTO, HttpStatus.CONFLICT);

        RespuestasParametrosHash.put(RespuestasAccessToken.TOKENNOENCONTRADO, HttpStatus.NOT_FOUND);
        RespuestasParametrosHash.put(RespuestasGenerales.JSONINCORRECTO, HttpStatus.BAD_REQUEST);
    }
    public static Map<String, HttpStatus> RespuestasDocumentosHash = new HashMap<>();
    static {
        RespuestasDocumentosHash.put(RespuestasParametros.PARAMETRONOEXISTE, HttpStatus.BAD_REQUEST);
    }

}
