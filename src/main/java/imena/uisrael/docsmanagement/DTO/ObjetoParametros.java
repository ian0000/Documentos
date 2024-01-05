package imena.uisrael.docsmanagement.DTO;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Parametros;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ObjetoParametros {
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParamHeader {
        public String titulo;
        public String subtitulo;
        public String nombreorganizacion;
        public String logo;
        public String ladologo;
        public String fecha;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParamFooter {
        public String notapiepagina;
        public String informacioncontacto;
        public String firma;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParamGenerics {
        public String font;
        public int fontSize;
        public String fontColor;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParametrosCrear {
        public AccessToken accessToken;
        public Parametros parametros;
        public ParamHeader header;
        public ParamFooter footer;
        public ParamGenerics generics;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParametrosJson {
        public String nombreParametro;
        public ParamHeader header;
        public ParamFooter footer;
        public ParamGenerics generics;
    }

}