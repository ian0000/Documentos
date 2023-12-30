package imena.uisrael.docsmanagement.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import imena.uisrael.docsmanagement.model.ObjetoOrganigrama;
import imena.uisrael.docsmanagement.model.Organigrama;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasOrganigrama;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.OrganigramaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequestMapping("/organigrama")
public class OrganigramaController {
    @Autowired
    private OrganigramaService organigramaService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrganigrama(@RequestBody  ObjetoOrganigrama objeto, @RequestParam String padre) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto, padre);

        if (respuesatmp != null && !respuesatmp.isEmpty() && !Parciales.RespuestasOrganigramaHash.containsKey(respuesatmp)) {
            return GeneralFunctions.convertJSON(
                    GeneralFunctions.ConverToObject(respuesatmp, objeto.organigrama.getClass()));
        } else {
            
            HttpStatus httpStatus = Parciales.RespuestasOrganigramaHash.get(respuesatmp);
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(respuesatmp, httpStatus);
    
            return responseEntity;
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateOrganigrama(@RequestBody ObjetoOrganigrama objeto, @RequestParam String padre) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto, padre);
        
        if (respuesatmp != null && !respuesatmp.isEmpty() && !Parciales.RespuestasOrganigramaHash.containsKey(respuesatmp)) {
            return GeneralFunctions.convertJSON(
                    GeneralFunctions.ConverToObject(respuesatmp, objeto.organigrama.getClass()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesatmp);
        }
    }
}
