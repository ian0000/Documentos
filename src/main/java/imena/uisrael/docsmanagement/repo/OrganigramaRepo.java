package imena.uisrael.docsmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Organigrama;

public interface OrganigramaRepo extends JpaRepository<Organigrama, Long> {

    @Query("SELECT o FROM Organigrama o WHERE o.codigoPersona = :codigoPersona AND o.accessToken.token = :accessTokenID AND o.active = :activeStatus")
    Organigrama findByCodigoPersona(String codigoPersona,String accessTokenID, boolean activeStatus);

    @Query("SELECT o FROM Organigrama o WHERE o.nivel = :nivel AND o.accessToken.token = :accessTokenID")
    List<Organigrama> findByNivelOrganigrama(int nivel,String accessTokenID);
}