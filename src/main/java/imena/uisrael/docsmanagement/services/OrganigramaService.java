package imena.uisrael.docsmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoCrearOrganigrama;
import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoUpdateOrganigrama;
import imena.uisrael.docsmanagement.model.Departamento;
import imena.uisrael.docsmanagement.model.Organigrama;
import imena.uisrael.docsmanagement.model.Parciales.RespuestaDepartamentos;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasOrganigrama;
import imena.uisrael.docsmanagement.repo.DepartamentoRepo;
import imena.uisrael.docsmanagement.repo.OrganigramaRepo;

@Service
public class OrganigramaService {
    @Autowired
    private OrganigramaRepo organigramaRepo;
    @Autowired
    private DepartamentoRepo departamentoRepo;

    public String saveOrganigrama(ObjetoCrearOrganigrama objeto) {
        String verificaciones = verificacionesCreacion(objeto);
        if (!verificaciones.isBlank() || !verificaciones.isEmpty()) {
            return verificaciones;
        }
        Organigrama existeorganigrama = organigramaRepo.findByCodigoPersona(objeto.organigramanuevo.getCodigoPersona(),
                objeto.accessToken.getToken());
        if (existeorganigrama == null) {
            // ver que el nivel 0 no se repita
            Organigrama organigramanuevo = new Organigrama();
            organigramanuevo = objeto.organigramanuevo;
            organigramanuevo.setActive(true);
            String codSuper = objeto.codSuper;

            if (organigramanuevo.getNivel().equals("0") && codSuper == null) {
                // se busca si ya no existen niveles 0
                var tmp = organigramaRepo.findByNivel(organigramanuevo.getNivel(), objeto.accessToken.getToken())
                        .toArray();
                if (tmp.length != 0) {
                    return RespuestasOrganigrama.FALLONIVEL0;
                }
                return guardarOrganigrama(organigramanuevo, objeto.departamento, null);
            } else if (organigramanuevo.getNivel().equals("0") && codSuper != null) {
                return RespuestasOrganigrama.FALLOPADRE0;
            } else if (!organigramanuevo.getNivel().equals("0") && codSuper == null) {
                return RespuestasOrganigrama.FALLOPADRE;
            } else if (!organigramanuevo.getNivel().equals("0") && codSuper != null) {
                return guardarOrganigrama(organigramanuevo, objeto.departamento, codSuper);
            } else {
                return RespuestasOrganigrama.FALLOGUARDAR;
            }
        } else {
            return RespuestasOrganigrama.USUARIOEXISTE;
        }
    }

    public String updateOrganigrama(ObjetoUpdateOrganigrama objeto) {
        //!si muevo y llevarorganigramas es true se los mueve con el --> solo repartire por que esto lleva a que tenga que obtener los previos y los siguientes y asi recursivamente
        //pero si muevo y llevar organigramas es false se reparte 
        //dos funcionalidades
        return "";
    }

    public String stateOrganigrama(ObjetoCrearOrganigrama objeto) {
        return "";
    }

    public String listOrganigrama(ObjetoCrearOrganigrama objeto) {
        return "";
    }

    public String guardarOrganigrama(Organigrama organigramanuevo, Departamento departamentoenvio, String codsuper) {

        Departamento departamento = departamentoRepo.findById(departamentoenvio.getDepartamentoID()).get();

        if (departamento != null && departamento.isActive()) {
            organigramanuevo.setDepartamento(departamento);
            if (codsuper != null) {
                Organigrama organigramasuper = organigramaRepo.findByCodigoPersona(codsuper,
                        departamento.getAccessToken().getToken());
                if (organigramasuper != null) {
                    organigramanuevo.setPadre(organigramasuper);
                } else {
                    return RespuestasOrganigrama.FALLOFALTAPADRE;
                }
            }
            try {
                var res = organigramaRepo.save(organigramanuevo);
                return GeneralFunctions.ConverToString(res);
            } catch (Exception e) {
                return RespuestasOrganigrama.FALLOGUARDAR;
            }
        } else if (departamento == null) {
            return RespuestaDepartamentos.DEPARTAMENTONOENCONTRADO;
        } else {
            return RespuestaDepartamentos.DEPARTAMENTODESACTIVADO;
        }
    }

    public String verificacionesCreacion(ObjetoCrearOrganigrama objeto) {
        if (objeto == null || objeto.organigramanuevo == null ||
                objeto.departamento == null || objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        if (objeto.organigramanuevo.getCodigoPersona().isEmpty()
                || objeto.organigramanuevo.getCodigoPersona().isBlank()) {
            return RespuestasOrganigrama.CODIGOPEROSNAVACIO;
        }
        if (objeto.organigramanuevo.getNombrePersona().isEmpty()
                || objeto.organigramanuevo.getNombrePersona().isBlank()) {

            return RespuestasOrganigrama.NOMBREPEROSNAVACIO;
        }
        if (objeto.departamento.getDepartamentoID() == null) {

            return RespuestaDepartamentos.DEPARTAMENTOIDINVALIDO;
        }
        if (objeto.accessToken.getToken().isEmpty() || objeto.accessToken.getToken().isBlank()) {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }

        return "";
    }
}

// public String updateOrganigrama(ObjetoOrganigrama objeto) {
// // si existe genial tenemos para actualizar//el objeto no puede traer cod
// // padre//bueno en teoria ya puede

// Organigrama organigrama = new Organigrama();
// organigrama = objeto.organigrama;
// Organigrama existe =
// organigramaRepo.findByIDOrganigrama(organigrama.getOrganigramaID(),
// objeto.accessToken.getToken());
// try {
// int nivelexiste = Integer.parseInt(existe.getNivel());
// int nivelOrganigrama = Integer.parseInt(organigrama.getNivel());

// if (nivelexiste == 0 && nivelOrganigrama == nivelexiste) {
// // es caso unico
// existe.setCodigoPersona(organigrama.getCodigoPersona());
// existe.setNombrePersona(organigrama.getNombrePersona());

// } else if (nivelexiste == 0 && nivelOrganigrama != nivelexiste) {
// return RespuestasOrganigrama.FALLOPADRE0;
// } else if (nivelexiste != 0 && nivelOrganigrama != 0) {
// // de aqui solo me importan si son != 0
// // si ocurre eso trabajamos buscando el padre del nivel que queremos
// // tengo que verificar que si voy a subir debe existir al menos otro con ese
// // nivel
// int countOrganigramasNivelX = organigramaRepo
// .findByNivelOrganigrama(nivelOrganigrama, objeto.accessToken.getToken())
// .size();
// // QUE PASA CON LOS USUARIOS QUE ESTABAN ASIGANDOS A ESE NIVEL DE USUARIO?SE
// // MUEVEN O SE CAMBIAN AL OTRO?
// // si hay mas? como los mando?
// // TODO: PARA EVITAR PROBLEMAS SE QUEDAN CON EL OTRO Y ESO ANTES DE GUARDAR
// ESTE
// if (countOrganigramasNivelX > 1) {
// existe.setCodigoPersona(organigrama.getCodigoPersona());
// existe.setNombrePersona(organigrama.getNombrePersona());
// return " ";
// } else {
// return RespuestasOrganigrama.EXISTEUSUARIOSNIVELX;
// }

// } else {
// return RespuestasOrganigrama.FALLONIVEL0;
// }
// try {

// var res = organigramaRepo.save(existe);
// return GeneralFunctions.ConverToString(res);
// } catch (Exception e) {
// // TODO: handle exception
// return RespuestasOrganigrama.FALLOACTUALIZAR;
// }
// } catch (Exception e) {
// // TODO: handle exception
// return RespuestasOrganigrama.FALLOACTUALIZAR;
// }

// }

// public Organigrama verificarExiste(ObjetoOrganigrama objeto) {
// var existe = new Organigrama();
// existe =
// organigramaRepo.findByCodigoPersona(objeto.organigrama.getCodigoPersona(),
// objeto.accessToken.getToken(), true);
// if (existe != null) {
// return existe;
// } else {
// return null;
// }
// }
// }
