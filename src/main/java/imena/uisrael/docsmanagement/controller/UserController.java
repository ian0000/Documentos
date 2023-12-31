package imena.uisrael.docsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.DTO.ObjetoUserUpdate;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestParam String email, @RequestParam String password) {
        String respuestmp = userService.saveUser(email, password);
        return GeneralFunctions.DevolverRespuesta(respuestmp, User.class, Parciales.RespuestasUsuariosHash);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody ObjetoUserUpdate objeto) {
        String respuestmp = userService.updateUser(objeto.usernuevo, objeto.userantiguo);
        return GeneralFunctions.DevolverRespuesta(respuestmp, User.class, Parciales.RespuestasUsuariosHash);

    }

    @PostMapping("/state")
    public ResponseEntity<Object> updateUserState(@RequestParam String email) {
        String respuestmp = userService.stateUser(email);
        return GeneralFunctions.DevolverRespuesta(respuestmp, User.class, Parciales.RespuestasUsuariosHash);
    }
}
