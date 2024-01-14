package imena.uisrael.docsmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosPlaceholders;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.ParametrosService;

@Controller
@RequestMapping("/api/parametros")
public class ParametrosController {
 @Autowired
    private ParametrosService parametrosService;

    @PostMapping("/create")
    public ResponseEntity<Object> createParametros(@RequestBody ParametrosPlaceholders objeto) {
        String respuestmp = parametrosService.saveParametro(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Parametros.class, Parciales.RespuestasParametrosHash);
    }

    @PostMapping("/state")
    public ResponseEntity<Object> changeStateParametros(@RequestBody ParametrosPlaceholders objeto) {
        String respuestmp = parametrosService.stateParametro(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Parametros.class, Parciales.RespuestasParametrosHash);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateParametros(@RequestBody ParametrosPlaceholders objeto) {
        String respuestmp = parametrosService.updateParametro(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Parametros.class, Parciales.RespuestasParametrosHash);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getParametrosList(@RequestBody ParametrosPlaceholders objeto) {
        String respuestmp = parametrosService.listParametro(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, List.class, Parciales.RespuestasParametrosHash);
    }
}
