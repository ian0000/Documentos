package imena.uisrael.docsmanagement.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import imena.uisrael.docsmanagement.DTO.ObjetoDocumentos;
import imena.uisrael.docsmanagement.model.Documentos;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.DocumentosService;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Slf4j
@RestController
@RequestMapping("/api/documentos")
public class DocumentosController {

    // TODO: EN GUARDADO DE VERSIONES SE DEBE ALMACENAR CMO PDF Y AL CREAR COMO WORD
    // ESTOS NO SE DEBEN PODER CAMBIAR

    @Autowired
    private DocumentosService documentosService;

    @PostMapping("/create")
    public String createDocumento(@RequestBody ObjetoDocumentos objeto) {
        String respuestmp = documentosService.saveDocumentos();
        log.info(respuestmp);
        return "";
    }

    @PostMapping("/test")
    public String test(@RequestPart("document") MultipartFile document) {
        try {
            byte[] documentBytes = document.getBytes();

            String respuestmp = documentosService.testDoc(documentBytes);
            return respuestmp;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("/state")
    public ResponseEntity<Object> changeStateDocumento(@RequestBody ObjetoDocumentos objeto) {
        String respuestmp = documentosService.stateDocumentos(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Documentos.class, Parciales.RespuestasAccessTokenHash);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateDocumento(@RequestBody ObjetoDocumentos objeto) {
        String respuestmp = documentosService.updateDocumentos(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Documentos.class, Parciales.RespuestasAccessTokenHash);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getDocumentoList(@RequestBody ObjetoDocumentos objeto) {
        String respuestmp = documentosService.listDocumentos(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, List.class, Parciales.RespuestasAccessTokenHash);
    }
}
