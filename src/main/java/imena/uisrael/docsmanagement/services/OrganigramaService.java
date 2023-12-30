package imena.uisrael.docsmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.ObjetoOrganigrama;
import imena.uisrael.docsmanagement.model.Organigrama;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasOrganigrama;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.OrganigramaRepo;

@Service
public class OrganigramaService {
    @Autowired
    private OrganigramaRepo organigramaRepo;
    @Autowired
    private AccessTokenRepo accessTokenRepo;

    public String saveOrganigrama(ObjetoOrganigrama objeto, String codPadre) {
        Organigrama existingOrganigrama = verificarExiste(objeto);

        int countOrganigramasNivel0 = 0;
        try {
            int nivel = Integer.parseInt(objeto.organigrama.getNivel());

            // si existe org
            if (nivel == 0) {
                // solo si el organigrama tiene nivel 0, busoc que no existe mas con ese nivel
                countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0, objeto.accessToken.getToken())
                        .size();
            }
            var accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
            if (accessToken != null) {
                objeto.organigrama.setAccessToken(accessToken);
                if (countOrganigramasNivel0 == 0 && existingOrganigrama == null) {
                    // si cumple con los dos primeros es que debe ser un nivel != 0
                    // o que se olvido
                    // countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0).size();

                    if (codPadre != null && !codPadre.isEmpty() && nivel != 0) {
                        // entramos para asignar padre
                        Organigrama padre = organigramaRepo.findByCodigoPersona(codPadre, objeto.accessToken.getToken(),
                                true);
                        if (padre != null) {
                            objeto.organigrama.setPadre(padre);
                            int padreNivel = Integer.parseInt(padre.getNivel());
                            objeto.organigrama.setNivel(String.valueOf(padreNivel + 1));
                        } else {
                            countOrganigramasNivel0 = organigramaRepo
                                    .findByNivelOrganigrama(0, objeto.accessToken.getToken()).size();
                            return countOrganigramasNivel0 == 0 ? RespuestasOrganigrama.FALLOSENECESITAREGISTRO0
                                    : RespuestasOrganigrama.FALLOFALTAPADRE;
                        }
                        // EL NIVEL DEBERIA OBTENER DEPENDIENDO DEL PADRE
                    } else if ((codPadre == null || codPadre.isEmpty()) && nivel != 0) {
                        // todo que pasa cuando no hay ningun registro? ie no hay nivel 0 fijarme en
                        // esto countorganigramasnivel
                        if (countOrganigramasNivel0 == 0) {
                            return RespuestasOrganigrama.FALLOSENECESITAREGISTRO0;
                        }
                        return RespuestasOrganigrama.FALLOPADRE;
                    }
                    try {
                        var res = organigramaRepo.save(objeto.organigrama);
                        return GeneralFunctions.ConverToString(res);
                    } catch (Exception e) {
                        return RespuestasOrganigrama.FALLOGUARDADO;
                    }
                } else {
                    return existingOrganigrama != null ? RespuestasOrganigrama.USUARIOEXISTE
                            : RespuestasOrganigrama.USUARIO0;
                }
            } else {
                return RespuestasAccessToken.TOKENNOENCONTRADO;
            }
        } catch (Exception e) {
            return RespuestasOrganigrama.FALLOCAMPONIVEL;
        }
    }

    public String updateOrganigrama(ObjetoOrganigrama objeto, String codPadre) {
        // si existe genial tenemos para actualizar

        return "";

    }

    public Organigrama verificarExiste(ObjetoOrganigrama objeto) {
        var existe = new Organigrama();
        // ESTO BUSCA SOLO CON EL CODIGO PERSONA PERO DEBERIA BUSCAR CON EL PARAMETRO DE
        // CODIGO DE ACCESO
        // TODO AGREGAR EL DE SOLICITAR EL PARAMETRO DE ACCESO Y/O SOLO EL TOKEN
        existe = organigramaRepo.findByCodigoPersona(objeto.organigrama.getCodigoPersona(),
                objeto.accessToken.getToken(), true);
        if (existe != null) {
            return existe;
        } else {
            return null;
        }
    }
}
