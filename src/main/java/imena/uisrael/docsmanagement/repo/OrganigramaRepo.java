package imena.uisrael.docsmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Organigrama;

public interface OrganigramaRepo extends JpaRepository<Organigrama, Long> {
    
    @Query("SELECT o FROM Organigrama o WHERE o.codigoPersona = :codigoPersona and o.departamento.accessToken.token = :token")
    Organigrama findByCodigoPersona(String codigoPersona, String token);
    @Query("SELECT o FROM Organigrama o WHERE o.nivel = :nivel and o.departamento.accessToken.token = :token")
    List<Organigrama> findByNivel(String nivel, String token);
}