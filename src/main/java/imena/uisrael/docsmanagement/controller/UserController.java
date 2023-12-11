package imena.uisrael.docsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepo userRepository;

    
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.atInfo().log("Usuario nuevo");
            user = new User();
            user.setEmail(email);
            user.setPassword(password);//TODO encrypt password
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            log.atError().log("Usuario existente");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe");
        }

    }

}
