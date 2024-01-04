package imena.uisrael.docsmanagement.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionesDocumentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long versionesDocumentosID;
    private String nombreVersion;
    @Lob
    private String jsonCambios;
    private Date fechaCambio;
    
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "documentosID")
    private Documentos documentos;

}
