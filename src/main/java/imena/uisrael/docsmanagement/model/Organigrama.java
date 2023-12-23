package imena.uisrael.docsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // tiene que hacer enlaFce conalgo? consigo mismo?
    
    @JsonIgnore
    @ToString.Exclude   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_padre") // Foreign key column name
    private Organigrama padre; // Reference to the parent Organigrama

}
