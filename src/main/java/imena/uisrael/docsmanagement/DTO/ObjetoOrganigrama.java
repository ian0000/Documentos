package imena.uisrael.docsmanagement.DTO;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Departamento;
import imena.uisrael.docsmanagement.model.Organigrama;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ObjetoOrganigrama {
    
    public Organigrama organigramanuevo;
    public Departamento departamento;
    public AccessToken accessToken;
    public Organigrama organigramaviejo;
    public long codPadre;
    public boolean llevarorganigramas;//en caso de cambiar de nivel se true se lleva sus organigramaas o false los reparte a su nivel
}

