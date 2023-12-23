package imena.uisrael.docsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import imena.uisrael.docsmanagement.model.Organigrama;
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
    public ResponseEntity<Object> createOrganigrama(@RequestBody Organigrama entity, @RequestParam String padre) {
        String respuesatmp = organigramaService.saveOrganigrama(entity, padre);
        if (respuesatmp != null && !respuesatmp.equals("") && !respuesatmp.equals(RespuestasOrganigrama.USUARIO0)
                && !respuesatmp.equals(RespuestasOrganigrama.FALLOGUARDADO)) {
            return GeneralFunctions.convertJSOn(GeneralFunctions.ConverToObject(respuesatmp, Organigrama.class));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesatmp);
        }
    }

}
