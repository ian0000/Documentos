package imena.uisrael.docsmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;
import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public String saveUser(String email, String password) {
        String verificacion = GeneralFunctions.verificarCorreoPassword(email, password);
        if (verificacion.length() != 0) {
            return verificacion;
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            try {
                user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setActive(true);
                var res = userRepository.save(user);
                return GeneralFunctions.ConverToString(res);
            } catch (Exception e) {
                return RespuestasGenerales.ERRORINTERNO;
            }
        } else {
            return RespuestasUsuarios.USUARIOEXISTE;
        }
    }

    public String updateUser(User usernuevo, User userantiguo) {
        String verificacionantiguo = GeneralFunctions.verificarCorreoPassword(userantiguo.getEmail(),
                userantiguo.getPassword());
        String verificacionnuevo = GeneralFunctions.verificarCorreoPassword(usernuevo.getEmail(),
                usernuevo.getPassword());

        if (verificacionantiguo.length() != 0 && verificacionnuevo.length() != 0) {
            return verificacionantiguo + verificacionnuevo;
        }
        User user = userRepository.findByEmailAndPassword(userantiguo.getEmail(), userantiguo.getPassword());

        if (user != null) {
            try {
                user.setEmail(usernuevo.getEmail());
                user.setPassword(usernuevo.getPassword());
                user.setActive(usernuevo.isActive());
                var res = userRepository.save(user);
                return GeneralFunctions.ConverToString(res);
            } catch (Exception e) {
                return RespuestasGenerales.ERRORINTERNO;
            }
        } else {
            return RespuestasUsuarios.USUARIONOENCONTRADO;
        }
    }

    public String stateUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.save(user);
            return (user.isActive()) ? RespuestasUsuarios.USUARIOACTIVADO : RespuestasUsuarios.USUARIODESACTIVADO;
        } else {
            return RespuestasUsuarios.USUARIONOENCONTRADO;
        }
    }

}
