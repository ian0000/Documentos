package imena.uisrael.docsmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoOrganigrama;
import imena.uisrael.docsmanagement.repo.OrganigramaRepo;

@Service
public class OrganigramaService {
    @Autowired
    private OrganigramaRepo organigramaRepo;
    public String saveOrganigrama(ObjetoOrganigrama objeto){
        return "";
    }
    public String updateOrganigrama(ObjetoOrganigrama objeto){
        return "";
    }
    public String stateOrganigrama(ObjetoOrganigrama objeto){
        return "";
    }
    public String listOrganigrama(ObjetoOrganigrama objeto){
        return "";
    }
}
//     public String saveOrganigrama(ObjetoOrganigrama objeto, String codPadre) {
//         Organigrama existe = verificarExiste(objeto);

//         int countOrganigramasNivel0 = 0;
//         try {
//             int nivel = Integer.parseInt(objeto.organigrama.getNivel());

//             // si existe org
//             if (nivel == 0) {
//                 // solo si el organigrama tiene nivel 0, busoc que no existe mas con ese nivel
//                 countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0, objeto.accessToken.getToken())
//                         .size();
//             }
//             var accessToken = accessTokenRepo.findByToken(objeto.accessToken.getToken());
//             if (accessToken != null) {
//                 objeto.organigrama.setAccessToken(accessToken);
//                 if (countOrganigramasNivel0 == 0 && existe == null) {
//                     // si cumple con los dos primeros es que debe ser un nivel != 0
//                     // o que se olvido
//                     // countOrganigramasNivel0 = organigramaRepo.findByNivelOrganigrama(0).size();

//                     if (codPadre != null && !codPadre.isEmpty() && nivel != 0) {
//                         // entramos para asignar padre
//                         Organigrama padre = organigramaRepo.findByCodigoPersona(codPadre, objeto.accessToken.getToken(),
//                                 true);
//                         if (padre != null) {
//                             objeto.organigrama.setPadre(padre);
//                             int padreNivel = Integer.parseInt(padre.getNivel());
//                             objeto.organigrama.setNivel(String.valueOf(padreNivel + 1));
//                         } else {
//                             countOrganigramasNivel0 = organigramaRepo
//                                     .findByNivelOrganigrama(0, objeto.accessToken.getToken()).size();
//                             return countOrganigramasNivel0 == 0 ? RespuestasOrganigrama.FALLOSENECESITAREGISTRO0
//                                     : RespuestasOrganigrama.FALLOFALTAPADRE;
//                         }
//                         // EL NIVEL DEBERIA OBTENER DEPENDIENDO DEL PADRE
//                     } else if ((codPadre == null || codPadre.isEmpty()) && nivel != 0) {
//                         // todo que pasa cuando no hay ningun registro? ie no hay nivel 0 fijarme en
//                         // esto countorganigramasnivel
//                         if (countOrganigramasNivel0 == 0) {
//                             return RespuestasOrganigrama.FALLOSENECESITAREGISTRO0;
//                         }
//                         return RespuestasOrganigrama.FALLOPADRE;
//                     }
//                     try {
//                         var res = organigramaRepo.save(objeto.organigrama);
//                         return GeneralFunctions.ConverToString(res);
//                     } catch (Exception e) {
//                         return RespuestasOrganigrama.FALLOGUARDADO;
//                     }
//                 } else {
//                     return existe != null ? RespuestasOrganigrama.USUARIOEXISTE
//                             : RespuestasOrganigrama.USUARIO0;
//                 }
//             } else {
//                 return RespuestasAccessToken.TOKENNOENCONTRADO;
//             }
//         } catch (Exception e) {
//             return RespuestasOrganigrama.FALLOCAMPONIVEL;
//         }
//     }

//     public String updateOrganigrama(ObjetoOrganigrama objeto) {
//         // si existe genial tenemos para actualizar//el objeto no puede traer cod
//         // padre//bueno en teoria ya puede

//         Organigrama organigrama = new Organigrama();
//         organigrama = objeto.organigrama;
//         Organigrama existe = organigramaRepo.findByIDOrganigrama(organigrama.getOrganigramaID(),
//                 objeto.accessToken.getToken());
//         try {
//             int nivelexiste = Integer.parseInt(existe.getNivel());
//             int nivelOrganigrama = Integer.parseInt(organigrama.getNivel());

//             if (nivelexiste == 0 && nivelOrganigrama == nivelexiste) {
//                 // es caso unico
//                 existe.setCodigoPersona(organigrama.getCodigoPersona());
//                 existe.setNombrePersona(organigrama.getNombrePersona());

//             } else if (nivelexiste == 0 && nivelOrganigrama != nivelexiste) {
//                 return RespuestasOrganigrama.FALLOPADRE0;
//             } else if (nivelexiste != 0 && nivelOrganigrama != 0) {
//                 // de aqui solo me importan si son != 0
//                 // si ocurre eso trabajamos buscando el padre del nivel que queremos
//                 // tengo que verificar que si voy a subir debe existir al menos otro con ese
//                 // nivel
//                 int countOrganigramasNivelX = organigramaRepo
//                         .findByNivelOrganigrama(nivelOrganigrama, objeto.accessToken.getToken())
//                         .size();
//                 // QUE PASA CON LOS USUARIOS QUE ESTABAN ASIGANDOS A ESE NIVEL DE USUARIO?SE
//                 // MUEVEN O SE CAMBIAN AL OTRO?
//                 // si hay mas? como los mando?
//                 // TODO: PARA EVITAR PROBLEMAS SE QUEDAN CON EL OTRO Y ESO ANTES DE GUARDAR ESTE
//                 if (countOrganigramasNivelX > 1) {
//                     existe.setCodigoPersona(organigrama.getCodigoPersona());
//                     existe.setNombrePersona(organigrama.getNombrePersona());
//                     return "    ";
//                 } else {
//                     return RespuestasOrganigrama.EXISTEUSUARIOSNIVELX;
//                 }

//             } else {
//                 return RespuestasOrganigrama.FALLONIVEL0;
//             }
//             try {

//                 var res = organigramaRepo.save(existe);
//                 return GeneralFunctions.ConverToString(res);
//             } catch (Exception e) {
//                 // TODO: handle exception
//                 return RespuestasOrganigrama.FALLOACTUALIZAR;
//             }
//         } catch (Exception e) {
//             // TODO: handle exception
//             return RespuestasOrganigrama.FALLOACTUALIZAR;
//         }

//     }

//     public Organigrama verificarExiste(ObjetoOrganigrama objeto) {
//         var existe = new Organigrama();
//         existe = organigramaRepo.findByCodigoPersona(objeto.organigrama.getCodigoPersona(),
//                 objeto.accessToken.getToken(), true);
//         if (existe != null) {
//             return existe;
//         } else {
//             return null;
//         }
//     }
// }
