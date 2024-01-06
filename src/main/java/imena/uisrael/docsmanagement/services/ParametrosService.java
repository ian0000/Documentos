package imena.uisrael.docsmanagement.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParamFooter;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParamGenerics;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParamHeader;
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

    public String stateParametro(ParametrosCrear objeto) {
        String verificacion = verificarParametrosGenerales(objeto, false);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        Parametros existeparametro = parametrosRepo.findByNombreParametro(objeto.parametros.getNombreParametro(),
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

    public String listParametro(ParametrosCrear objeto) {
        String verificacion = verificarParametrosGenerales(objeto, false);
        if (!verificacion.isEmpty()) {
            return verificacion;
        }
        List<Parametros> parametros = parametrosRepo.findByToken(
                objeto.accessToken.getToken());
        if (parametros != null) {
            List<ParametrosJson> res = new ArrayList<ParametrosJson>();
            for (Parametros param : parametros) {
                byte[] blob = param.getJsonParametros();
                String jsonString = new String(blob, StandardCharsets.UTF_8);

                // Parse the JSON string manually
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONObject headerJson = jsonObject.getJSONObject("header");
                JSONObject footerJson = jsonObject.getJSONObject("footer");
                JSONObject genericsJson = jsonObject.getJSONObject("generics");

                String titulo = headerJson.getString("titulo");
                String subtitulo = headerJson.getString("subtitulo");
                String nombreorganizacion = headerJson.getString("nombreorganizacion");
                String logo = headerJson.getString("logo");
                String ladologo = headerJson.getString("ladologo");
                String fecha = headerJson.getString("fecha");

                String notapiepagina = footerJson.getString("notapiepagina");
                String informacioncontacto = footerJson.getString("informacioncontacto");
                String firma = footerJson.getString("firma");

                String font = genericsJson.getString("font");
                int fontSize = genericsJson.getInt("fontSize");
                String fontColor = genericsJson.getString("fontColor");

                ParamHeader h = new ParamHeader(titulo, subtitulo, nombreorganizacion, logo, ladologo, fecha);
                ParamFooter f = new ParamFooter(notapiepagina, informacioncontacto, firma);
                ParamGenerics g = new ParamGenerics(font, fontSize, fontColor);

                ParametrosJson parametrosJson = new ParametrosJson();
                parametrosJson.header = h;
                parametrosJson.footer = f;
                parametrosJson.generics = g;
                parametrosJson.nombreParametro = param.getNombreParametro();
                res.add(parametrosJson);

            }

            return GeneralFunctions.ConverToString(res);
        }

        return RespuestasParametros.SINREGISTROSTOKEN;
    }

    public String verificarParametrosCrear(ParametrosCrear objeto) {
        String verificar = verificarParametrosGenerales(objeto, true);
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

    public String verificarParametrosGenerales(ParametrosCrear objeto, boolean verjson) {
        if (objeto == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        if (verjson && (objeto.parametros == null
                || objeto.header == null || objeto.footer == null || objeto.generics == null)) {

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
            // TODO: haver el guardado de la version aqui

            parametrosantiguo.setNombreParametro(objeto.parametros.getNombreParametro());
            parametrosantiguo.setJsonParametros(mapper.writeValueAsString(oldMap).getBytes(StandardCharsets.UTF_8));
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
