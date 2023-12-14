package imena.uisrael.docsmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.services.AccessTokenService;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/tokens")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/generate")
    public ResponseEntity<Object> generarAccessToken(@RequestParam String email, @RequestParam String password,
            @RequestParam String keyword) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            if(user.getAccessTokens() == null){
                user.setAccessTokens(new ArrayList<AccessToken>());
            }
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> keyword.equals(x.getKeyword()) == true).findFirst();
            if (accessTokenOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un token con esa palabra clave");
            }
            AccessToken accessToken = accessTokenService.saveAccessToken(email, password, keyword);
            if (accessToken != null) {
                // return ResponseEntity.ok(accessToken.getToken());
                return GeneralFunctions.convertJSOn(accessToken);

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallo al generar el token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario y/o contraseña incorrectos");
        }
    }

    @PostMapping("/changestate")
    public ResponseEntity<Object> changeState(@RequestParam String email, @RequestParam String password,
            @RequestParam String keyword) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> keyword.equals(x.getKeyword()) == true).findFirst();
            if (accessTokenOptional.isPresent()) {
                AccessToken accessToken = accessTokenOptional.get();
                String texto;
                if(accessToken.isActive() == true){
                    accessToken.setActive(false);
                    texto = "Token activado";
                    
                }else{
                    accessToken.setActive(true);
                    texto = "Token desactivado";
                }
                accessTokenService.updateAccessToken(accessToken);
                return ResponseEntity.ok(texto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token no encontrado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario y/o contraseña incorrectos");
        }
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateToken(@RequestParam String email, @RequestParam String password,
            @RequestParam String keyword) {
        
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> keyword.equals(x.getKeyword()) == true).findFirst();
            if (accessTokenOptional.isPresent()) {
                AccessToken accessToken = accessTokenOptional.get();
                accessToken.setToken(accessTokenService.generateToken());
                accessTokenService.updateAccessToken(accessToken);
                return ResponseEntity.ok( GeneralFunctions.convertJSOn(accessToken.getToken()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token no encontrado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario y/o contraseña incorrectos");
        }
    }
    @GetMapping("/list")
    public ResponseEntity<Object> getAccessTokenList(@RequestParam String email,
            @RequestParam String password) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            if (!user.getAccessTokens().isEmpty()) {
                // return ResponseEntity.ok(user.getAccessTokens().toString());

                return  GeneralFunctions.convertJSOn(user.getAccessTokens());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No token de acceso encontrado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario y/o contraseña incorrectos");
        }
    }
    @GetMapping("/findbykeyword")
    public ResponseEntity<Object> getAccessTokenListByLikeKeyword(@RequestParam String email,
            @RequestParam String password, String keyword) {
        User user = userService.findByEmailAndPassword(email, password);
        if(keyword == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parámetro 'keyword' no puede ser nulo");
        }
        if (user != null) {

            if (user.getAccessTokens() != null && !user.getAccessTokens().isEmpty()) {
                List<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                        .filter(x -> x.getKeyword().contains(keyword)).collect(Collectors.toList());
                if (accessTokenOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No token de acceso encontrado");
                }else{
                    return  GeneralFunctions.convertJSOn(accessTokenOptional);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No token de acceso encontrado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario y/o contraseña incorrectos");
        }
    }

}
