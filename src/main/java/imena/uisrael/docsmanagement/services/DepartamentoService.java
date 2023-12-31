package imena.uisrael.docsmanagement.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoDepartamento;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Departamento;
import imena.uisrael.docsmanagement.model.Parciales.RespuestaDepartamentos;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.DepartamentoRepo;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepo departamentoRepo;
    @Autowired
    private AccessTokenRepo accessTokenRepo;

    public String createDepartamento(ObjetoDepartamento objeto) {
        if (objeto == null || objeto.departamento == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        AccessToken accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
        if (accessToken != null) {
            Departamento departamentoexiste = departamentoRepo.findByNombre(objeto.departamento.getNombreDepartamento(),
                    accessToken.getToken());
            if (departamentoexiste == null) {
                Departamento nuevo = new Departamento();
                nuevo.setNombreDepartamento(objeto.departamento.getNombreDepartamento());
                nuevo.setActive(true);
                nuevo.setAccessToken(accessToken);
                try {
                    var res = departamentoRepo.save(nuevo);
                    return GeneralFunctions.ConverToString(res);
                } catch (Exception e) {
                    return RespuestasGenerales.ERRORINTERNO;
                }
            } else {
                return RespuestaDepartamentos.DEPARTAMENTOEXISTE;
            }
        } else {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }
    }

    public String updateDepartamento(ObjetoDepartamento objeto) {
        if (objeto == null || objeto.departamento == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        AccessToken accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
        if (accessToken != null) {
            Departamento departamentoexiste = departamentoRepo.findByNombre(objeto.departamento.getNombreDepartamento(),
                    accessToken.getToken());
            if (departamentoexiste != null) {
                departamentoexiste.setNombreDepartamento(objeto.departamento.getNombreDepartamento());
                departamentoexiste.setActive(objeto.departamento.isActive());
                try {
                    var res = departamentoRepo.save(departamentoexiste);
                    return GeneralFunctions.ConverToString(res);
                } catch (Exception e) {
                    return RespuestasGenerales.ERRORINTERNO;
                }
            } else {
                return RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO;
            }
        } else {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }
    }

    public String stateDepartamento(ObjetoDepartamento objeto) {
        if (objeto == null || objeto.departamento == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        AccessToken accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
        if (accessToken != null) {
            Departamento departamentoexiste = departamentoRepo.findByNombre(objeto.departamento.getNombreDepartamento(),
                    accessToken.getToken());
            if (departamentoexiste != null) {

                departamentoexiste.setActive(!departamentoexiste.isActive());
                try {
                    var res = departamentoRepo.save(departamentoexiste);
                    return (res.isActive()) ? RespuestaDepartamentos.DEPARTAMENTOACTIVADO
                            : RespuestaDepartamentos.DEPARTAMENTODESACTIVADO;
                } catch (Exception e) {
                    return RespuestasGenerales.ERRORINTERNO;
                }
            } else {
                return RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO;
            }
        } else {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }
    }

    public String listDepartamento(ObjetoDepartamento objeto) {
        if (objeto == null || objeto.accessToken == null || objeto.departamento == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        AccessToken accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
        if (accessToken != null) {
            String keyword = objeto.departamento.getNombreDepartamento();
            if (accessToken.getDepartamentos() != null && !accessToken.getDepartamentos().isEmpty()) {
                List<Departamento> departamentoOpcional = accessToken.getDepartamentos().stream()
                        .collect(Collectors.toList());
                if (keyword != null && keyword == "" && keyword.isBlank()) {
                    departamentoOpcional = accessToken.getDepartamentos().stream()
                            .filter(x -> x.getNombreDepartamento().contains(keyword))
                            .collect(Collectors.toList());
                }
                return GeneralFunctions.ConverToString(departamentoOpcional);
            } else {
                return RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO;
            }
        } else {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }
    }
}
