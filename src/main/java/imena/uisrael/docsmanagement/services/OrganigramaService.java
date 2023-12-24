package imena.uisrael.docsmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.Organigrama;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasOrganigrama;
import imena.uisrael.docsmanagement.repo.OrganigramaRepo;

@Service
public class OrganigramaService {
    @Autowired
    private OrganigramaRepo organigramaRepo;

    public String saveOrganigrama(Organigrama organigrama, String codPadre) {
        Organigrama existingOrganigrama = verificarExiste(organigrama);

        int countOrganigramasNivel0 = 0;
        try {
            int nivel = Integer.parseInt(organigrama.getNivel());
        
            // si existe org
            if (nivel == 0) {
                // solo si el organigrama tiene nivel 0, busoc que no existe mas con ese nivel
                countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0).size();
            }
            if (countOrganigramasNivel0 == 0 && existingOrganigrama == null) {
                // si cumple con los dos primeros es que debe ser un nivel != 0
                // o que se olvido
                // countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0).size();

                if (codPadre != null && !codPadre.isEmpty() && nivel != 0) {
                    // entramos para asignar padre
                    Organigrama padre = organigramaRepo.findByCodigoPersona(codPadre, true);
                    if (padre != null) {
                        organigrama.setPadre(padre);
                        int padreNivel = Integer.parseInt(padre.getNivel());
                        organigrama.setNivel(String.valueOf(padreNivel + 1));
                    } else {
                        countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0).size();
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
                    var res = organigramaRepo.save(organigrama);
                    return GeneralFunctions.ConverToString(res);
                } catch (Exception e) {
                    return RespuestasOrganigrama.FALLOGUARDADO;
                }
            } else {
                return existingOrganigrama != null ? RespuestasOrganigrama.USUARIOEXISTE
                        : RespuestasOrganigrama.USUARIO0;
            }
        } catch (Exception e) {
            return RespuestasOrganigrama.FALLOCAMPONIVEL;
        }
    }

    public Organigrama verificarExiste(Organigrama organigrama) {
        var existe = new Organigrama();
        existe = organigramaRepo.findByCodigoPersona(organigrama.getCodigoPersona(), true);
        if (existe != null) {
            return existe;
        } else {
            return null;
        }
    }
}
