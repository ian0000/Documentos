package imena.uisrael.docsmanagement.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentosID;
    private String nombreDocumento;
    private byte[] base64;
    private Date fechaUltimaModificaicon;
    private String jsonDatosDocumento;

    //aqui deberia tener dos de para quien es y de quien es
    //con el token
    //el parametro y su version
    
}
