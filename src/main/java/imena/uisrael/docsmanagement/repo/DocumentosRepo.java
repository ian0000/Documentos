package imena.uisrael.docsmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import imena.uisrael.docsmanagement.model.Documentos;

public interface DocumentosRepo extends JpaRepository<Documentos, Long> {
// TODO: LA DEBERIA TENER DOS TABLAS DISTINTAS PARA LOS DOCUMENTOS
//LA DE GENERADAS POR PANTALLA Y LA DE SUBIDA COMO DOCUMENTOS

}
