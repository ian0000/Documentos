package imena.uisrael.docsmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Parametros;

public interface ParametrosRepo extends JpaRepository<Parametros, Long> {
     @Query("SELECT p FROM Parametros p WHERE p.nombreParametro = :nombreParametro and p.accessToken.token = :token")
    Parametros findByNombreParametro(String nombreParametro, String token);

}
