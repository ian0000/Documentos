package imena.uisrael.docsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.OrganigramaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/organigrama")
public class OrganigramaController {
    @Autowired
    private OrganigramaService organigramaService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrganigrama(@RequestBody ObjetoOrganigrama objeto) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }

    @PostMapping("/update")
    // TODO: PEDIR SI SE LLEVA LOS USUARIOS O SOLO SE REPARTE
    public ResponseEntity<Object> updateOrganigrama(@RequestBody ObjetoOrganigrama objeto) {
        String respuesatmp = organigramaService.updateOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }
    @PostMapping("/state")
    // TODO: PEDIR SI SE LLEVA LOS USUARIOS O SOLO SE REPARTE
    public ResponseEntity<Object> stateOrganigrama(@RequestBody ObjetoOrganigrama objeto) {
        String respuesatmp = organigramaService.updateOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }
    @PostMapping("/list")
    // TODO: PEDIR SI SE LLEVA LOS USUARIOS O SOLO SE REPARTE
    public ResponseEntity<Object> listOrganigrama(@RequestBody ObjetoOrganigrama objeto) {
        String respuesatmp = organigramaService.updateOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }

}
