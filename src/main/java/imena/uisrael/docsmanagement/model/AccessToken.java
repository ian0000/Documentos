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
public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessTokenID;

    private String token;
    private String keyword;// puede tener varias claves pero con esto encuentra la especifca?
    private boolean active;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    // los departamento tendran la misma llave es para mas por organizacion? sehhh
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "accessToken", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Departamento> departamentos;
    
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "accessToken", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Parametros> parametros;

}
