package imena.uisrael.docsmanagement.DTO;

import java.util.List;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Parametros;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ObjetoParametros {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParamGenerics {
        public String font;
        public int fontSize;
        public String fontColor;
    }

    // esto van a ser una lista de cada placeholder del documento
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Placeholder {
        public String placeholderName;
        public String placeholderValue;
        public byte[] placeholderValueImagen;// si este viene null no le presto atecion caso contrario le muestro como
                                             // imagen
        public ParamGenerics parametros;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Documento {
        public String documentName;
        public byte[] document;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParametrosPlaceholders {
        public AccessToken accessToken;
        public List<Placeholder> placeholders;
        public Documento documents;
        public Parametros parametros;
        public ParamGenerics generales;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParametrosJson {
        public List<Placeholder> placeholders;
        public Documento documents;
        public String nombreParametro;
        public ParamGenerics generales;
    }
}