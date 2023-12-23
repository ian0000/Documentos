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
        Organigrama org = new Organigrama();
        org = verificarExiste(organigrama);

        int countorganigramasnivel = 0;
        countorganigramasnivel = organigramaRepo.findByNivelOrganigrama(0).size();
        if (countorganigramasnivel != 0) {

            if (codPadre != null && !codPadre.equals("")) {
                Organigrama padre = organigramaRepo.findByCodigoPersona(codPadre, true);
                if (padre != null) {
                    org.setPadre(padre);
                }
            }
            try {
                return GeneralFunctions.ConverToString(org);

            } catch (Exception e) {
                return RespuestasOrganigrama.FALLOGUARDADO;
            }
        } else {
            return RespuestasOrganigrama.USUARIO0;
        }
    }

    public Organigrama verificarExiste(Organigrama organigrama) {
        // todo solo puede existir uno sin padre
        var existe = new Organigrama();
        existe = organigramaRepo.findByCodigoPersona(organigrama.getCodigoPersona(), true);
        if (existe != null) {
            return existe;
        } else {
            return null;
        }
    }
}
