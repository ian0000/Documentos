package imena.uisrael.docsmanagement.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoCrearOrganigrama;
import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama.ObjetoStateOrganigrama;
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
                objeto.accessToken.getToken(), objeto.departamento.getDepartamentoID());

        if (existeorganigrama == null) {
            // ver que el nivel 0 no se repita
            Organigrama organigramanuevo = new Organigrama();
            organigramanuevo = objeto.organigramanuevo;
            organigramanuevo.setActive(true);
            String codSuper = objeto.codSuper;

            if ((organigramanuevo.getNivel() != null && organigramanuevo.getNivel().equals("0")) && codSuper == null) {
                // se busca si ya no existen niveles 0

                var tmp = organigramaRepo
                        .findByNivel(organigramanuevo.getNivel(), objeto.accessToken.getToken(),
                                objeto.departamento.getDepartamentoID())
                        .toArray();
                if (tmp.length != 0) {
                    return RespuestasOrganigrama.FALLONIVEL0;
                }
                return guardarOrganigrama(organigramanuevo, objeto.departamento, null, false, false,
                        objeto.accessToken.getToken());
            } else if ((organigramanuevo.getNivel() != null && organigramanuevo.getNivel().equals("0"))
                    && codSuper != null) {
                return RespuestasOrganigrama.FALLOPADRE0;
            } else if ((organigramanuevo.getNivel() != null && !organigramanuevo.getNivel().equals("0"))
                    && codSuper == null) {
                return RespuestasOrganigrama.FALLOPADRE;
            } else if ((organigramanuevo.getNivel() == null || !organigramanuevo.getNivel().equals("0"))
                    && codSuper != null) {
                return guardarOrganigrama(organigramanuevo, objeto.departamento, codSuper, false, false,
                        objeto.accessToken.getToken());
            } else {
                return RespuestasOrganigrama.FALLOGUARDAR;
            }
        } else {
            return RespuestasOrganigrama.USUARIOEXISTE;
        }
        // TODO: si existe un usuario 0 desactivado ya no dejarlo reactivar o no se
        // puede desactivar
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
                objeto.accessToken.getToken(), objeto.departamento.getDepartamentoID());

        if (existeorganigrama != null) {
            if (!existeorganigrama.getDepartamento().getAccessToken().isActive()) {
                return RespuestasAccessToken.TOKENDESACTIVADO;
            }
            // dos verificaciones el de antiguo que no sea 0 si ya existe un 0 que ya
            // deberia existir por que si o si
            // y que el nuevo no vaya a ser 0 por que a menos de que el viejo y nuevo tengan
            // el mismo nivel

            // si solo existe ese organigrama en ese nivel todos los subs se deben mover con
            // el si se cambia

            // if (objeto.organigramanuevo.getNivel() != null) {//SI PUEDE VENIR NULO SOLO
            // ES PARA CAMBIAR X COSA
            // ver que el nivel 0 no se repita Y que no pueda ser modificado

            if (objeto.organigramanuevo.getCodigoPersona() != null
                    && !objeto.organigramanuevo.getCodigoPersona().isBlank()) {
                existeorganigrama.setCodigoPersona(objeto.organigramanuevo.getCodigoPersona());

            }
            if (objeto.organigramanuevo.getNombrePersona() != null
                    && !objeto.organigramanuevo.getNombrePersona().isBlank()) {
                existeorganigrama.setNombrePersona(objeto.organigramanuevo.getNombrePersona());

            }
            // existeorganigrama.setActive(objeto.organigramanuevo.isActive());
            // TODO no puedo modificar el estado por que si
            // esto solo para setear nivel
            // aqui igual irian la funcion para cambiar y reasignar los de abajo
            boolean modificarsub = false;
            Boolean llevarorganigramas = objeto.llevarorganigramas;
            if (objeto.organigramanuevo.getNivel() != null
                    && existeorganigrama.getNivel() != objeto.organigramanuevo.getNivel()) {
                if (existeorganigrama.getNivel().equals("0")) {
                    return RespuestasOrganigrama.NIVEL0;
                } else if (objeto.organigramanuevo.getNivel().equals("0")) {
                    return RespuestasOrganigrama.FALLONIVEL0;
                } else {
                    // aqui los niveles son distintos si o si
                    // tengo que ver si hay mas con este nivel// si solo hay uno debe estar puesta
                    // la opcion de llevarorganigramas para poder cambiar de nivel caso contrario
                    // sigale nomas
                    if (!existeorganigrama.getDepartamento().getAccessToken().isActive()) {
                        return RespuestasAccessToken.TOKENDESACTIVADO;
                    }
                    int cantidadmismonivel = organigramaRepo.findByNivel(existeorganigrama.getNivel(),
                            existeorganigrama.getDepartamento().getAccessToken().getToken(),
                            objeto.departamento.getDepartamentoID()).size();
                    if (cantidadmismonivel == 1 && llevarorganigramas != null && !objeto.llevarorganigramas) {
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
                return guardarOrganigrama(existeorganigrama, existeorganigrama.getDepartamento(), codSuper,
                        false, modificarsub, existeorganigrama.getDepartamento().getAccessToken().getToken());
            } else if (existeorganigrama.getNivel().equals("0") && codSuper != null) {
                return RespuestasOrganigrama.FALLOPADRE0;
            } else if (!existeorganigrama.getNivel().equals("0") && codSuper == null) {
                return RespuestasOrganigrama.FALLOPADRE;
            } else if (!existeorganigrama.getNivel().equals("0") && codSuper != null) {

                // TODO: si organigrama mismo nivel no se puede modificar el nivel a menos de
                // que si selleve todos
                if (llevarorganigramas != null) {
                    return guardarOrganigrama(existeorganigrama, existeorganigrama.getDepartamento(), codSuper,
                            objeto.llevarorganigramas, modificarsub,
                            existeorganigrama.getDepartamento().getAccessToken().getToken());
                } else {
                    return guardarOrganigrama(existeorganigrama, existeorganigrama.getDepartamento(), codSuper,
                            false, modificarsub, existeorganigrama.getDepartamento().getAccessToken().getToken());
                }
                // aqui ya viene encriptado
            } else {
                return RespuestasOrganigrama.FALLOGUARDAR;
            }

        } else {
            return RespuestasOrganigrama.ORGANIGRAMANOEXISTE;
        }
    }

    public String stateOrganigrama(ObjetoStateOrganigrama objeto) {
        var verificaciones = verificacionesState(objeto);
        if (verificaciones != "") {
            return verificaciones;
        }
        Organigrama existeorganigrama = organigramaRepo.findByCodigoPersona(objeto.organigramanuevo.getCodigoPersona(),
                objeto.accessToken.getToken(), objeto.departamento.getDepartamentoID());
        if (existeorganigrama != null) {
            existeorganigrama.setActive(!existeorganigrama.isActive());
            try {
                organigramaRepo.save(existeorganigrama);
                modificarSub(existeorganigrama, false, false);// sumar digito = mantenersub
                return existeorganigrama.isActive() ? RespuestasOrganigrama.ORGANIGRAMAACTIVADO
                        : RespuestasOrganigrama.ORGANIGRAMADESACTIVADO;
            } catch (Exception e) {
                return RespuestasOrganigrama.FALLOACTUALIZAR;
            }
        } else {
            return RespuestasOrganigrama.ORGANIGRAMANOEXISTE;
        }
    }

    public String listOrganigrama(ObjetoStateOrganigrama objeto) {
        var verificaciones = verificacionesState(objeto);
        if (verificaciones != "") {
            return verificaciones;
        }
        List<Organigrama> listaOrganigramas = organigramaRepo.findByToken(objeto.accessToken.getToken(),
                objeto.departamento.getDepartamentoID());
        if (listaOrganigramas != null && listaOrganigramas.size() > 0 && !listaOrganigramas.isEmpty()) {

            List<Organigrama> filteredList = listaOrganigramas.stream()
                    .filter(x -> (objeto.organigramanuevo.getCodigoPersona() == null
                            || x.getCodigoPersona().contains(objeto.organigramanuevo.getCodigoPersona())) &&
                            (objeto.organigramanuevo.getNombrePersona() == null
                                    || x.getNombrePersona().contains(objeto.organigramanuevo.getNombrePersona()))
                            &&
                            (objeto.organigramanuevo.getNivel() == null
                                    || x.getNivel().contains(objeto.organigramanuevo.getNivel())))

                    .collect(Collectors.toList());
            return GeneralFunctions.ConverToString(filteredList);
        } else {
            return RespuestasOrganigrama.ORGANIGRAMANOEXISTE;
        }
    }

    public String guardarOrganigrama(Organigrama organigramanuevo, Departamento departamentoenvio, String codsuper,
            boolean llevarorganigramas, boolean modificarsub, String token) {

        String numerotmp = organigramanuevo.getNivel();
        Departamento departamento = departamentoRepo.findByIdAccesstoken(departamentoenvio.getDepartamentoID(), token);
        if (departamento != null && departamento.isActive()) {
            organigramanuevo.setDepartamento(departamento);
            if (codsuper != null) {

                Organigrama organigramasuper = organigramaRepo.findByCodigoPersona(codsuper,
                        departamento.getAccessToken().getToken(), departamento.getDepartamentoID());
                if (organigramasuper != null && organigramasuper.isActive()) {
                    int nivelactual = Integer.parseInt(organigramasuper.getNivel()) + 1;
                    organigramanuevo.setNivel(String.valueOf(nivelactual));
                    organigramanuevo.setPadre(organigramasuper);
                } else {
                    if (organigramasuper == null) {

                        return RespuestasOrganigrama.FALLOFALTAPADRE;
                    } else {
                        return RespuestasOrganigrama.ORGANIGRAMADESACTIVADO;
                    }
                }
            }
            try { // hasta aqui se guardo
                Organigrama res = new Organigrama();
                if (llevarorganigramas) {

                    if (modificarsub) {
                        modificarSub(organigramanuevo, llevarorganigramas, false);
                    }
                    res = organigramaRepo.save(organigramanuevo);

                } else {
                    res = organigramaRepo.save(organigramanuevo);
                    modificarSub(organigramanuevo, llevarorganigramas, !organigramanuevo.getNivel().equals(numerotmp));
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

    public void modificarSub(Organigrama organigrama, boolean llevarorganigramas, boolean sumardigito) {
        // dos casos //si llevarorganigramas tengo que hacer que cada uno de los subs
        // para abajo se empiecen a actualizar el nivel
        // else que solo se reorganicen los subs proximos a los sup restantes en el
        // nivel
        if (llevarorganigramas) {
            // ahora modificar todos los niveles de los anteriores buscar todos los que
            // tengan de padre a x y de ahi cambiar nivel y ya
            cambiarNivelSUb(organigrama, Integer.parseInt(organigrama.getNivel()));

        } else {

            int nivelactual = Integer.parseInt(organigrama.getNivel());

            // con + 1 funciona lo que quiero pero es el mismo nivel pero se reparte para
            // los otros
            // sin + 1 funciona lo otro es distinto nivel y se reparte para los del anterior
            // nivel
            if (sumardigito) {
                nivelactual = nivelactual + 1;
            }

            List<Organigrama> organigramasmismonivel = organigramaRepo.findByNivel(String.valueOf(nivelactual),
                    organigrama.getDepartamento().getAccessToken().getToken(),
                    organigrama.getDepartamento().getDepartamentoID()).stream().filter(x -> x.isActive()).toList();

            List<Organigrama> organigramassubs = organigramaRepo.findByIDSuper(organigrama.getOrganigramaID(),
                    organigrama.getDepartamento().getAccessToken().getToken());
            for (Organigrama organigramaitem : organigramassubs) {
                Organigrama mejorsuper = mejorSuper(organigramasmismonivel);
                if (mejorsuper != null) {
                    organigramaitem.setPadre(mejorsuper);
                }
                organigramaitem.setNivel(String.valueOf(nivelactual + 1));
                organigramaRepo.save(organigramaitem);
            }
            // organigramaRepo.saveAll(organigramassubs);
        }

    }

    public void cambiarNivelSUb(Organigrama organigramasuper, int nivelactual) {
        List<Organigrama> listaOrganigramasSub = organigramaRepo.findByIDSuper(organigramasuper.getOrganigramaID(),
                organigramasuper.getDepartamento().getAccessToken().getToken());

        if (listaOrganigramasSub != null) {
            nivelactual = nivelactual + 1;

            for (Organigrama organigrama : listaOrganigramasSub) {
                organigramaRepo.updateOrganigramaNivel(String.valueOf(nivelactual), organigrama.getOrganigramaID());
                organigrama.setNivel(String.valueOf(nivelactual));
                cambiarNivelSUb(organigrama, nivelactual);
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
                    organigrama.getDepartamento().getAccessToken().getToken(),
                    organigrama.getDepartamento().getDepartamentoID()));
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
        if (objeto.departamento.getDepartamentoID() == null) {

            return RespuestaDepartamentos.DEPARTAMENTOIDINVALIDO;
        }
        if (objeto.accessToken.getToken().isEmpty() || objeto.accessToken.getToken().isBlank()) {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }

        return "";
    }

    public String verificacionesState(ObjetoStateOrganigrama objeto) {
        if (objeto == null || objeto.organigramanuevo == null ||
                objeto.accessToken == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }

        if (objeto.accessToken.getToken().isEmpty() || objeto.accessToken.getToken().isBlank()) {
            return RespuestasAccessToken.TOKENNOENCONTRADO;
        }

        return "";
    }
}
