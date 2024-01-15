package imena.uisrael.docsmanagement.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosPlaceholders;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.Placeholder;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasParametros;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.ParametrosRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParametrosService {

    @Autowired
    private ParametrosRepo parametrosRepo;
    @Autowired
    private AccessTokenRepo accessTokenRepo;

    public String saveParametro(ParametrosPlaceholders objeto) {
        String verificacion = verificarParametrosCrear(objeto);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametro(objeto.parametros.getNombreParametro(),
                objeto.accessToken.getToken());
        if (existeparametro == null) {
            ParametrosJson parametrosJson = new ParametrosJson();

            // estos parametros son convertidos a json para poder ser guardados
            parametrosJson.placeholders = objeto.placeholders;
            parametrosJson.documents = objeto.documents;
            parametrosJson.generales = objeto.generales;
            parametrosJson.nombreParametro = objeto.parametros.getNombreParametro();

            String verificarPlaceholder = verificarPlaceholderDoc(objeto, parametrosJson);
            if(!verificarPlaceholder.isBlank() || !verificarPlaceholder.isEmpty()){
                return verificarPlaceholder;
            }
            String json = GeneralFunctions.ConverToString(parametrosJson);
            if (json != "") {

                Parametros nuevopaParametro = new Parametros();
                nuevopaParametro.setNombreParametro(objeto.parametros.getNombreParametro());
                nuevopaParametro.setJson(json.getBytes(StandardCharsets.UTF_8));
                nuevopaParametro.setUltimaModificacion(new Date());
                nuevopaParametro.setActive(true);

                AccessToken accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
                if (accessToken != null) {
                    nuevopaParametro.setAccessToken(accessToken);
                    try {
                        Parametros res = new Parametros();
                        res = parametrosRepo.save(nuevopaParametro);
                        return GeneralFunctions.ConverToString(res);
                    } catch (Exception e) {
                        return RespuestasParametros.ERRORGUARDAR;
                    }
                } else {
                    return RespuestasAccessToken.TOKENNOENCONTRADO;
                }
            } else {
                return RespuestasParametros.ERRORGUARDAR;
            }

        } else {
            return RespuestasParametros.NOMBREPARAMETROEXISTE;
        }
    }

    public String updateParametro(ParametrosPlaceholders objeto) {
        String verificacion = verificarParametrosGenerales(objeto, true);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametro(objeto.parametros.getNombreParametro(),
                objeto.accessToken.getToken());
        if (existeparametro != null) {
            return verificarValoresUpdate(objeto, existeparametro);
        }
        return RespuestasParametros.PARAMETRONOEXISTE;
    }

    public String stateParametro(ParametrosPlaceholders objeto) {
        String verificacion = verificarParametrosGenerales(objeto, false);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametroSinActive(
                objeto.parametros.getNombreParametro(),
                objeto.accessToken.getToken());
        if (existeparametro != null) {
            try {
                existeparametro.setActive(!existeparametro.isActive());
                existeparametro.setUltimaModificacion(new Date());
                parametrosRepo.save(existeparametro);
                return existeparametro.isActive() ? RespuestasParametros.PARAMETROACTIVADO
                        : RespuestasParametros.PARAMETRODESACTIVADO;
            } catch (Exception e) {
                return RespuestasParametros.ERRORGUARDAR;
            }
        }
        return RespuestasParametros.PARAMETRONOEXISTE;
    }

    public String listParametro(ParametrosPlaceholders objeto) {
        String verificacion = verificarParametrosGenerales(objeto, false);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        List<Parametros> parametros = parametrosRepo.findByToken(
                objeto.accessToken.getToken());
        if (parametros != null) {
            List<ParametrosJson> res = new ArrayList<ParametrosJson>();
            for (Parametros param : parametros) {
                ParametrosJson parametrosJson = GeneralFunctions.converParamToJSON(param);
                res.add(parametrosJson);

            }

            return GeneralFunctions.ConverToString(res);
        }

        return RespuestasParametros.SINREGISTROSTOKEN;
    }

    public String verificarParametrosCrear(ParametrosPlaceholders objeto) {
        String verificar = verificarParametrosGenerales(objeto, true);
        if (!verificar.isBlank()) {
            return verificar;
        }
        if (objeto.documents.documentName == null || objeto.documents.documentName.isEmpty() ||
                objeto.documents.document == null) {

            return RespuestasParametros.DOCUMENTOINCORRECTO;
        }

        return "";
    }

    public String verificarParametrosGenerales(ParametrosPlaceholders objeto, boolean verjson) {
        if (objeto == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        if (verjson && (objeto.parametros == null
                || objeto.placeholders == null || objeto.documents == null || objeto.parametros == null)) {

            return RespuestasGenerales.JSONINCORRECTO;
        }
        if (objeto.accessToken.getToken() == null || objeto.accessToken.getToken().isEmpty()
                || objeto.accessToken.getToken().isBlank()) {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }
        if (objeto.parametros.getNombreParametro() == null || objeto.parametros.getNombreParametro().isEmpty()
                || objeto.parametros.getNombreParametro().isBlank()) {
            return RespuestasParametros.NOMBREPARAMETROVACIO;
        }
        return "";
    }

    public String verificarValoresUpdate(ParametrosPlaceholders objeto, Parametros parametrosantiguo) {
        try {

            ParametrosJson parametrosJson = new ParametrosJson();
            parametrosJson.documents = objeto.documents;
            parametrosJson.placeholders = objeto.placeholders;
            parametrosJson.generales = objeto.generales;
            parametrosJson.nombreParametro = objeto.parametros.getNombreParametro();

            ParametrosJson parametrosJsonantiguo = GeneralFunctions.converParamToJSON(parametrosantiguo);
            if (parametrosJson.documents != null) {
                parametrosJsonantiguo.documents = parametrosJson.documents;
            }
            if (parametrosJson.placeholders != null && parametrosJson.placeholders.size() > 0) {
                parametrosJsonantiguo.placeholders = parametrosJson.placeholders;
            }
            if (parametrosJson.nombreParametro != null && !parametrosJson.nombreParametro.isEmpty()) {
                parametrosJsonantiguo.nombreParametro = parametrosJson.nombreParametro;
            }
            if (parametrosJson.generales != null) {
                parametrosJsonantiguo.generales = parametrosJson.generales;
            }

            String verificarPlaceholder = verificarPlaceholderDoc(objeto, parametrosJsonantiguo);
            if(!verificarPlaceholder.isBlank() || !verificarPlaceholder.isEmpty()){
                return verificarPlaceholder;
            }
            parametrosantiguo.setNombreParametro(objeto.parametros.getNombreParametro());

            parametrosantiguo.setUltimaModificacion(new Date());
            parametrosantiguo.setJson(GeneralFunctions.ConverToString(parametrosJsonantiguo).getBytes());

            try {
                Parametros res = new Parametros();
                res = parametrosRepo.save(parametrosantiguo);
                return GeneralFunctions.ConverToString(res);
            } catch (Exception e) {
                return RespuestasParametros.ERRORGUARDAR;
            }
        } catch (Exception e) {
            return RespuestasParametros.ERRORGUARDAR;
        }
    }

    private String verificarPlaceholderDoc(ParametrosPlaceholders objeto, ParametrosJson parametrosJsonantiguo) {

        Set<String> receivedPlaceholders;
        try {
            receivedPlaceholders = extractPlaceholdersFromBase64(objeto);
            Set<String> missingPlaceholders = new HashSet<>();
            for (Placeholder placeholder : parametrosJsonantiguo.placeholders) {
                missingPlaceholders.add(placeholder.placeholderName);
            }
            receivedPlaceholders.removeAll(missingPlaceholders);
    
            if (receivedPlaceholders.size() > 0) {
                return RespuestasParametros.CAMPOSDOCUMENTO;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespuestasParametros.ERRORGUARDAR;
        }

       
        return "";
    }

    public static Set<String> extractPlaceholdersFromBase64(ParametrosPlaceholders objeto) throws IOException {
        Set<String> placeholders = new HashSet<>();

        // Decode the base64-encoded document
        try (InputStream is = new ByteArrayInputStream(objeto.documents.document)) {
            XWPFDocument document = new XWPFDocument(is);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null && text.contains("[") && text.contains("]")) {
                        // Extract placeholder from the text and add to the set
                        String placeholder = text.substring(text.indexOf("[") + 1, text.indexOf("]"));
                        placeholders.add(placeholder);
                    }
                }
            }
            document.close();
        }

        return placeholders;
    }
}
