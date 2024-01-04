package imena.uisrael.docsmanagement.model;

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
public class Organigrama {
    // este es el modelo de datos que se va a tener para
    // poder crear el json que se enviara para guardar

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long organigramaID;
    private String codigoPersona;// cedula id etc un identificador
    private String nombrePersona;// para mostrar?
    private String nivel; // posicion
    private boolean active;

    // ?el access token viene de departamento y ya no directo
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_padre")
    private Organigrama padre;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamentoID")
    private Departamento departamento;

    
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "organigramaSender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documentos> sentDocuments;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "organigramaReceiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documentos> receivedDocuments;
}
