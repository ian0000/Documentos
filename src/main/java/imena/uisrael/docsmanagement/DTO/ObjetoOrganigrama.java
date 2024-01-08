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
        public String codSuper; // este  no debe ser nulo
        public Organigrama organigramaviejo;
        public boolean llevarorganigramas;// en caso de cambiar de nivel se true se lleva sus organigramaas o false los
                                          // reparte a su nivel
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjetoStateOrganigrama {
        public Organigrama organigramanuevo;
        public AccessToken accessToken;
        public Departamento departamento;
    }
}
