package imena.uisrael.docsmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import imena.uisrael.docsmanagement.DTO.ObjetoDepartamento;
import imena.uisrael.docsmanagement.model.Departamento;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.DepartamentoService;
import imena.uisrael.docsmanagement.services.GeneralFunctions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/departamento")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping("/create")
    public ResponseEntity<Object> createDepartamento(@RequestBody ObjetoDepartamento objeto) {
        String respuestmp = departamentoService.createDepartamento(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Departamento.class,
                Parciales.RespuestasDepartamentosHash);

    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateDepartamento(@RequestBody ObjetoDepartamento objeto) {
        String respuestmp = departamentoService.updateDepartamento(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Departamento.class,
                Parciales.RespuestasDepartamentosHash);

    }

    @PostMapping("/state")
    public ResponseEntity<Object> changeStateDepartamento(@RequestBody ObjetoDepartamento objeto) {
        String respuestmp = departamentoService.stateDepartamento(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, Departamento.class,
                Parciales.RespuestasDepartamentosHash);

    }

    @GetMapping("/list")
    public ResponseEntity<Object> listDepartamento(@RequestBody ObjetoDepartamento objeto) {
        String respuestmp = departamentoService.listDepartamento(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, List.class,
                Parciales.RespuestasDepartamentosHash);

    }

}
