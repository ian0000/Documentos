package imena.uisrael.docsmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.Departamento;

public interface DepartamentoRepo extends JpaRepository<Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.nombreDepartamento = :nombre and d.accessToken.token = :token")
    Departamento findByNombre(String nombre, String token);

    @Query("SELECT d FROM Departamento d WHERE d.departamentoID = :departamentoID and d.accessToken.token = :token")
    Departamento findByIdAccesstoken(Long departamentoID, String token);
}
