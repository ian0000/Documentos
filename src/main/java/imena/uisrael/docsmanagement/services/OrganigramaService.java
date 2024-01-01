package imena.uisrael.docsmanagement.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                return guardarOrganigrama(organigramanuevo, objeto.departamento, null, false, false);
            } else if (organigramanuevo.getNivel().equals("0") && codSuper != null) {
                return RespuestasOrganigrama.FALLOPADRE0;
            } else if (!organigramanuevo.getNivel().equals("0") && codSuper == null) {
                return RespuestasOrganigrama.FALLOPADRE;
            } else if (!organigramanuevo.getNivel().equals("0") && codSuper != null) {
                return guardarOrganigrama(organigramanuevo, objeto.departamento, codSuper, false, false);
            } else {
                return RespuestasOrganigrama.FALLOGUARDAR;
            }
        } else {
            return RespuestasOrganigrama.USUARIOEXISTE;
        }
    }

    public String updateOrganigrama(ObjetoUpdateOrganigrama objeto) {
        // !si muevo y llevarorganigramas es true se los mueve con el --> solo repartire
        // por que esto lleva a que tenga que obtener los previos y los siguientes y asi
        // recursivamente
        // pero si muevo y llevar organigramas es false se reparte
        // dos funcionalidades
        String verificaciones = verificacionesUpdate(objeto);
        if (!verificaciones.isBlank() || !verificaciones.isEmpty()) {
            return verificaciones;
        }
        Organigrama existeorganigrama = organigramaRepo.findByCodigoPersona(objeto.organigramaviejo.getCodigoPersona(),
                objeto.accessToken.getToken());
        if (existeorganigrama != null) {
            // dos verificaciones el de antiguo que no sea 0 si ya existe un 0 que ya
            // deberia existir por que si o si
            // y que el nuevo no vaya a ser 0 por que a menos de que el viejo y nuevo tengan
            // el mismo nivel

            // si solo existe ese organigrama en ese nivel todos los subs se deben mover con
            // el si se cambia

            if (objeto.organigramanuevo.getNivel() != null) {
                // ver que el nivel 0 no se repita Y que no pueda ser modificado
                existeorganigrama.setCodigoPersona(objeto.organigramanuevo.getCodigoPersona());
                existeorganigrama.setNombrePersona(objeto.organigramanuevo.getNombrePersona());
                //existeorganigrama.setActive(objeto.organigramanuevo.isActive());
                //TODO no puedo modificar el estado por que si
                // esto solo para setear nivel
                // aqui igual irian la funcion para cambiar y reasignar los de abajo
                boolean modificarsub = false;
                if (existeorganigrama.getNivel() != objeto.organigramanuevo.getNivel()) {
                    if (existeorganigrama.getNivel().equals("0")) {
                        return RespuestasOrganigrama.NIVEL0;
                    } else if (objeto.organigramanuevo.getNivel().equals("0")) {
                        return RespuestasOrganigrama.FALLONIVEL0;
                    } else {
                        // aqui los niveles son distintos si o si
                        // tengo que ver si hay mas con este nivel// si solo hay uno debe estar puesta
                        // la opcion de llevarorganigramas para poder cambiar de nivel caso contrario
                        // sigale nomas
                        int cantidadmismonivel = organigramaRepo.findByNivel(existeorganigrama.getNivel(),
                                existeorganigrama.getDepartamento().getAccessToken().getToken()).size();
                        if (cantidadmismonivel == 1 && !objeto.llevarorganigramas) {
                            return RespuestasOrganigrama.EXISTEUSUARIOSNIVELX;
                        } else {
                            existeorganigrama.setNivel(objeto.organigramanuevo.getNivel());
                            // AQUI SI MODIFICAR TODOS LOS USUARIOS DE NIVEL
                            modificarsub = true;
                        }

                    }
                }
                // cambiar el padre va de nuez
                String codSuper = objeto.codSuper;
                if (existeorganigrama.getNivel().equals("0") && codSuper == null) {
                    // no se hace nada//lo dejo para verificaciones nada mas
                } else if (existeorganigrama.getNivel().equals("0") && codSuper != null) {
                    return RespuestasOrganigrama.FALLOPADRE0;
                } else if (!existeorganigrama.getNivel().equals("0") && codSuper == null) {
                    return RespuestasOrganigrama.FALLOPADRE;
                } else if (!existeorganigrama.getNivel().equals("0") && codSuper != null) {

                    // TODO: si organigrama mismo nivel no se puede modificar el nivel a menos de
                    // que si selleve todos
                    return guardarOrganigrama(existeorganigrama, existeorganigrama.getDepartamento(), codSuper,
                            objeto.llevarorganigramas, modificarsub);
                    // aqui ya viene encriptado
                } else {
                    return RespuestasOrganigrama.FALLOGUARDAR;
                }
            }

        } else {
            return RespuestasOrganigrama.ORGANIGRAMANOEXISTE;
        }
        return "";
    }

    public String stateOrganigrama(ObjetoCrearOrganigrama objeto) {
        return "";
    }

    public String listOrganigrama(ObjetoCrearOrganigrama objeto) {
        return "";
    }

    public String guardarOrganigrama(Organigrama organigramanuevo, Departamento departamentoenvio, String codsuper,
            boolean llevarorganigramas, boolean modificarsub) {

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
                // hasta aqui se guardo
                if (modificarsub) {
                    modificarSub(organigramanuevo, llevarorganigramas);
                }
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

    public void modificarSub(Organigrama organigrama, boolean llevarorganigramas) {
        // dos casos //si llevarorganigramas tengo que hacer que cada uno de los subs
        // para abajo se empiecen a actualizar el nivel
        // else que solo se reorganicen los subs proximos a los sup restantes en el
        // nivel
        if (llevarorganigramas) {
            // ahora modificar todos los niveles de los anteriores buscar todos los que
            // tengan de padre a x y de ahi cambiar nivel y ya
            cambiarNivelSUb(organigrama);

        } else {
            List<Organigrama> organigramasmismonivel = organigramaRepo.findByNivel(organigrama.getNivel(),
                    organigrama.getDepartamento().getAccessToken().getToken());
            List<Organigrama> organigramassubs = organigramaRepo.findByIDSuper(organigrama.getOrganigramaID(),
                    organigrama.getDepartamento().getAccessToken().getToken());
            int nivelactual = Integer.parseInt(organigrama.getNivel()) + 1;
            for (Organigrama organigramaitem : organigramassubs) {
                Organigrama mejorsuper = mejorSuper(organigramasmismonivel);
                if (mejorsuper != null) {
                    organigramaitem.setPadre(mejorsuper);
                }
                organigramaitem.setNivel(String.valueOf(nivelactual));
            }
            organigramaRepo.saveAll(organigramassubs);
        }

    }

    public void cambiarNivelSUb(Organigrama organigramasuper) {
        List<Organigrama> listaOrganigramasSub = organigramaRepo.findByIDSuper(organigramasuper.getOrganigramaID(),
                organigramasuper.getDepartamento().getAccessToken().getToken());
        if (listaOrganigramasSub != null) {
            int nivelactual = Integer.parseInt(organigramasuper.getNivel()) + 1;
            for (Organigrama organigrama : listaOrganigramasSub) {
                organigramaRepo.updateOrganigramaNivel(String.valueOf(nivelactual), organigrama.getOrganigramaID());
                cambiarNivelSUb(organigrama);
            }
        }
    }

    public Organigrama mejorSuper(List<Organigrama> listasupers) {
        Organigrama mejorsuper = null;
        int minimoasignado = Integer.MAX_VALUE;
        Map<Long, Integer> resultMap = new HashMap<>();

        for (Organigrama organigrama : listasupers) {
            long orgid = organigrama.getOrganigramaID();
            resultMap.put(orgid, organigramaRepo.findContByIDSuper(orgid,
                    organigrama.getDepartamento().getAccessToken().getToken()));
        }
        for (Organigrama supervisor : listasupers) {
            int valoractual = resultMap.get(supervisor.getOrganigramaID());
            if (valoractual < minimoasignado) {
                minimoasignado = valoractual;
                mejorsuper = supervisor;
            }
        }
        return mejorsuper;
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

    public String verificacionesUpdate(ObjetoUpdateOrganigrama objeto) {
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
