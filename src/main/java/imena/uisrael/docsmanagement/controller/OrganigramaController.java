package imena.uisrael.docsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama;
import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoCrearOrganigrama;
import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoUpdateOrganigrama;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.OrganigramaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/organigrama")
public class OrganigramaController {
    @Autowired
    private OrganigramaService organigramaService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrganigrama(@RequestBody ObjetoCrearOrganigrama objeto) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }

    @PostMapping("/update")
    // TODO: PEDIR SI SE LLEVA LOS USUARIOS O SOLO SE REPARTE
    public ResponseEntity<Object> updateOrganigrama(@RequestBody ObjetoUpdateOrganigrama objeto) {
        String respuesatmp = organigramaService.updateOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }
    @PostMapping("/state")
    public ResponseEntity<Object> stateOrganigrama(@RequestBody ObjetoCrearOrganigrama objeto) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }
    @PostMapping("/list")
    public ResponseEntity<Object> listOrganigrama(@RequestBody ObjetoCrearOrganigrama objeto) {
        String respuesatmp = organigramaService.saveOrganigrama(objeto);
        return GeneralFunctions.DevolverRespuesta(respuesatmp, objeto.organigramanuevo.getClass(),
                Parciales.RespuestasOrganigramaHash);
    }

}
