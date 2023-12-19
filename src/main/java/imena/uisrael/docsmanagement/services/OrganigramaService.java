package imena.uisrael.docsmanagement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.Organigrama;
import imena.uisrael.docsmanagement.repo.OrganigramaRepo;

@Service
public class OrganigramaService {
    @Autowired
    private OrganigramaRepo organigramaRepo;

    public Organigrama saveOrganigrama(String CodigoPersona, String Nombre, String nivel, boolean active,
            String codPadre) {
        Organigrama organigrama = new Organigrama();
        organigrama.setCodigoPersona(CodigoPersona);
        organigrama.setNombrePersona(Nombre);
        organigrama.setNivel(nivel);
        organigrama.setActive(active);
        if (codPadre != null && !codPadre.equals("")) {
            Organigrama padre = organigramaRepo.findByCodigoPersona(codPadre, true);
            if (padre != null) {
                organigrama.setPadre(padre);
            }
        }
        try {
           return organigramaRepo.save(organigrama);
            
        } catch (Exception e) {
          return null;
        }
    }
}

