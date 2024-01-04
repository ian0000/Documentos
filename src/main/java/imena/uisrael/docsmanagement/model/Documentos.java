package imena.uisrael.docsmanagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    // aqui deberia tener dos de para quien es y de quien es
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_organigrama_id") // Change column name as needed
    private Organigrama organigramaSender;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_organigrama_id") // Change column name as needed
    private Organigrama organigramaReceiver;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parametrosID")
    private Parametros parametros;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "documentos", cascade = CascadeType.ALL)
    private List<VersionesDocumentos> versiones = new ArrayList<>();

}
