package imena.uisrael.docsmanagement.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
public class VersionesParametros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long versionesParametrosID;
    private String nombreVersion;
    @Column(columnDefinition = "BLOB")
    @Lob
    private byte[] jsonCambios;
    private Date fechaCambio;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "parametrosID")
    private Parametros parametros;

}
