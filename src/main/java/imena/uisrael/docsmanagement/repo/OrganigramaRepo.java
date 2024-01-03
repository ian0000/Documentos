package imena.uisrael.docsmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Organigrama;
import jakarta.transaction.Transactional;

public interface OrganigramaRepo extends JpaRepository<Organigrama, Long> {

    @Query("SELECT o FROM Organigrama o WHERE o.codigoPersona = :codigoPersona and o.departamento.accessToken.token = :token")
    Organigrama findByCodigoPersona(String codigoPersona, String token);

    @Query("SELECT o FROM Organigrama o WHERE o.padre.organigramaID = :organigramaID and o.departamento.accessToken.token = :token and o.active = true")
    List<Organigrama> findByIDSuper(long organigramaID, String token);

    @Query("SELECT COUNT(o.organigramaID)  FROM Organigrama o WHERE o.padre.organigramaID = :organigramaID and o.departamento.accessToken.token = :token and o.active = true")
    int findContByIDSuper(long organigramaID, String token);

    @Query("SELECT o FROM Organigrama o WHERE o.nivel = :nivel and o.departamento.accessToken.token = :token and o.active = true")
    List<Organigrama> findByNivel(String nivel, String token);

    @Query("SELECT o FROM Organigrama o WHERE o.departamento.accessToken.token = :token and o.departamento.accessToken.active = true")
    List<Organigrama> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Organigrama o SET o.nivel = :nivel WHERE o.padre.organigramaID = :organigramaID")
    void updateOrganigramaNivel(String nivel, long organigramaID);

}