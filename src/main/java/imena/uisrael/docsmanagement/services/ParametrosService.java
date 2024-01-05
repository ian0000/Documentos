package imena.uisrael.docsmanagement.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.DTO.ObjetoParametros;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosActualizar;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosCrear;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasParametros;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.ParametrosRepo;

@Service
public class ParametrosService {

    @Autowired
    private ParametrosRepo parametrosRepo;
    @Autowired
    private AccessTokenRepo accessTokenRepo;

    public String saveParametro(ParametrosCrear objeto) {
        String verificacion = verificarParametrosCrear(objeto);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametro(objeto.parametros.getNombreParametro(),
                objeto.accessToken.getToken());
        if (existeparametro == null) {
            ParametrosJson parametrosJson = new ParametrosJson();
            parametrosJson.header = objeto.header;
            parametrosJson.footer = objeto.footer;
            parametrosJson.generics = objeto.generics;
            String json = GeneralFunctions.ConverToString(parametrosJson);
            if (json != "") {

                Parametros nuevopaParametro = new Parametros();
                nuevopaParametro.setNombreParametro(objeto.parametros.getNombreParametro());
                nuevopaParametro.setJsonParametros(json.getBytes(StandardCharsets.UTF_8));
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

    public String updateParametro(ParametrosCrear objeto) {
        String verificacion = verificarParametrosGenerales(objeto);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametro(objeto.parametros.getNombreParametro(),
                objeto.accessToken.getToken());
        if (existeparametro != null) {
            return verificarValoresUpdate(objeto, existeparametro);
        }
        return RespuestasParametros.ERRORGUARDAR;
    }

    public String stateParametro(ParametrosActualizar objeto) {
        return "";
    }

    public String listParametro(ObjetoParametros objeto) {
        return "";
    }

    public String verificarParametrosCrear(ParametrosCrear objeto) {
        String verificar = verificarParametrosCrear(objeto);
        if (!verificar.isBlank()) {
            return verificar;
        }
        if (objeto.header.titulo == null || objeto.header.titulo.isEmpty() ||
                objeto.header.subtitulo == null || objeto.header.subtitulo.isEmpty() ||
                objeto.header.nombreorganizacion == null || objeto.header.nombreorganizacion.isEmpty() ||
                objeto.header.logo == null || objeto.header.logo.isEmpty() ||
                objeto.header.ladologo == null || objeto.header.ladologo.isEmpty() ||
                objeto.header.fecha == null || objeto.header.fecha.isEmpty()) {
            return RespuestasParametros.HEADERVACIOS;
        }
        if (objeto.footer.notapiepagina == null || objeto.footer.notapiepagina.isEmpty() ||
                objeto.footer.informacioncontacto == null || objeto.footer.informacioncontacto.isEmpty() ||
                objeto.footer.firma == null || objeto.footer.firma.isEmpty()) {
            return RespuestasParametros.FOOTERVACIOS;
        }
        if (objeto.generics.font == null || objeto.generics.font.isEmpty() ||
                objeto.generics.fontColor == null || objeto.generics.fontColor.isEmpty()) {
            return RespuestasParametros.GENERALPARAMS;
        }
        return "";
    }

    public String verificarParametrosGenerales(ParametrosCrear objeto) {
        if (objeto == null || objeto.accessToken == null || objeto.parametros == null
                || objeto.header == null || objeto.footer == null || objeto.generics == null) {
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

    public String verificarValoresUpdate(ParametrosCrear objeto, Parametros parametrosantiguo) {
        try {
            String jsonantiguo = new String(parametrosantiguo.getJsonParametros(), StandardCharsets.UTF_8);

            ParametrosJson parametrosJson = new ParametrosJson();
            parametrosJson.header = objeto.header;
            parametrosJson.footer = objeto.footer;
            parametrosJson.generics = objeto.generics;
            String jsonnuevo = GeneralFunctions.ConverToString(parametrosJson);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> oldMap = mapper.readValue(jsonantiguo, Map.class);
            Map<String, Object> newMap = mapper.readValue(jsonnuevo, Map.class);
            for (Map.Entry<String, Object> entry : newMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (oldMap.containsKey(key)) {
                    oldMap.put(key, value);
                }
            }

            System.out.println("Merged JSON: " + mapper.writeValueAsString(oldMap));

            parametrosantiguo.setNombreParametro(objeto.parametros.getNombreParametro());
            parametrosantiguo.setJsonParametros(jsonantiguo.getBytes(StandardCharsets.UTF_8));
            parametrosantiguo.setUltimaModificacion(new Date());

            try {
                Parametros res = new Parametros();
                res = parametrosRepo.save(parametrosantiguo);
                return GeneralFunctions.ConverToString(res);
            } catch (Exception e) {
                return RespuestasParametros.ERRORGUARDAR;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return RespuestasParametros.ERRORTRANSFORMARJSON;
        }
    }
}
