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
import jakarta.persistence.Lob;
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
public class Parametros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parametrosID;
    private String codigoParametro;
    @Lob
    private String jsonParametros;
    private Date ultimaModificacion;
    private boolean isActive;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accessTokenID")
    private AccessToken accessToken;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "parametros", cascade = CascadeType.ALL)
    private List<VersionesParametros> versiones = new ArrayList<>();
}
