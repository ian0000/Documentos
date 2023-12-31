package imena.uisrael.docsmanagement.DTO;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Departamento;
import imena.uisrael.docsmanagement.model.Organigrama;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ObjetoOrganigrama {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjetoCrearOrganigrama {
        public Organigrama organigramanuevo;
        public Departamento departamento;
        public AccessToken accessToken;
        public String codSuper; // este puede ser nulo
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjetoUpdateOrganigrama {
        public Organigrama organigramanuevo;
        public Departamento departamento;
        public AccessToken accessToken;
        public long codSuper; // este puede ser nulo
        public Organigrama organigramaviejo; 
    }
}
