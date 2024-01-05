package imena.uisrael.docsmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Parametros;

public interface ParametrosRepo extends JpaRepository<Parametros, Long> {
    @Query("SELECT p FROM Parametros p WHERE p.nombreParametro = :nombreParametro and p.accessToken.token = :token")
    Parametros findByNombreParametro(String nombreParametro, String token);

    @Query("SELECT p FROM Parametros p WHERE p.accessToken.token = :token and p.accessToken.active = true")
    List<Parametros> findByToken(String token);

}
