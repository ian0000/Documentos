package imena.uisrael.docsmanagement.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoAccessToken;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasAccessToken;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasGenerales;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.UserRepo;

@Service
public class AccessTokenService {
    @Autowired
    private AccessTokenRepo accessTokenRepository;

    @Autowired
    private UserRepo userRepository;

    public String saveAccessToken(ObjetoAccessToken objeto) {
          if (objeto == null || objeto.accessToken == null || objeto.user == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        String verificacion = GeneralFunctions.verificarCorreoPassword(objeto.user.getEmail(),
                objeto.user.getPassword());
        if (verificacion.length() != 0) {
            return verificacion;
        }
        if (objeto.accessToken.getKeyword() == null || objeto.accessToken.getKeyword().isBlank()
                || objeto.accessToken.getKeyword().isEmpty()) {
            return RespuestasAccessToken.PARAMETROKEYWORDNULO;
        }
        User user = userRepository.findByEmailAndPassword(objeto.user.getEmail(), objeto.user.getPassword());
        if (user != null) {
            if (user.getAccessTokens() == null) {
                user.setAccessTokens(new ArrayList<AccessToken>());
            }
            if(!user.isActive()){
                return RespuestasUsuarios.USUARIODESACTIVADO;
            }
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> objeto.accessToken.getKeyword().equals(x.getKeyword()) == true).findFirst();
            if (accessTokenOptional.isPresent()) {
                return RespuestasAccessToken.TOKENEXISTE;
            } else {
                AccessToken accessToken = new AccessToken();
                accessToken.setToken(generateToken());
                accessToken.setKeyword(objeto.accessToken.getKeyword());
                accessToken.setUser(user);
                accessToken.setActive(true);
                try {

                    var res = accessTokenRepository.save(accessToken);
                    return GeneralFunctions.ConverToString(res);
                } catch (Exception e) {
                    return RespuestasAccessToken.FALLOGENERARTOKEN;
                }
            }

        } else {
            return RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS;
        }
    }

    public String updateAccessToken(ObjetoAccessToken objeto) {
        if (objeto == null || objeto.accessToken == null || objeto.user == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }

        String verificacion = GeneralFunctions.verificarCorreoPassword(objeto.user.getEmail(),
                objeto.user.getPassword());
        if (verificacion.length() != 0) {
            return verificacion;
        }
        if (objeto.accessToken.getKeyword() == null || objeto.accessToken.getKeyword().isBlank()
                || objeto.accessToken.getKeyword().isEmpty()) {
            return RespuestasAccessToken.PARAMETROKEYWORDNULO;
        }
        User user = userRepository.findByEmailAndPassword(objeto.user.getEmail(), objeto.user.getPassword());
        if (user != null) {
            if (user.getAccessTokens() == null) {
                user.setAccessTokens(new ArrayList<AccessToken>());
            }
            if(!user.isActive()){
                return RespuestasUsuarios.USUARIODESACTIVADO;
            }
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> objeto.accessToken.getKeyword().equals(x.getKeyword()) == true).findFirst();
            if (!accessTokenOptional.isPresent()) {
                return RespuestasAccessToken.TOKENNOENCONTRADO;
            } else {

                AccessToken accessToken = accessTokenOptional.get();
                accessToken.setToken(generateToken());
                try {
                    var res = accessTokenRepository.save(accessToken);
                    return GeneralFunctions.ConverToString(res);
                } catch (Exception e) {
                    return RespuestasAccessToken.FALLOGENERARTOKEN;
                }
            }

        } else {
            return RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS;
        }
    }

    public String changeStateAccessToken(ObjetoAccessToken objeto) {
         if (objeto == null || objeto.accessToken == null || objeto.user == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        String verificacion = GeneralFunctions.verificarCorreoPassword(objeto.user.getEmail(),
                objeto.user.getPassword());
        if (verificacion.length() != 0) {
            return verificacion;
        }
        if (objeto.accessToken.getKeyword() == null || objeto.accessToken.getKeyword().isBlank()
                || objeto.accessToken.getKeyword().isEmpty()) {
            return RespuestasAccessToken.PARAMETROKEYWORDNULO;
        }
        User user = userRepository.findByEmailAndPassword(objeto.user.getEmail(), objeto.user.getPassword());
        if (user != null) {
            if (user.getAccessTokens() == null) {
                user.setAccessTokens(new ArrayList<AccessToken>());
            }
            if(!user.isActive()){
                return RespuestasUsuarios.USUARIODESACTIVADO;
            }
            Optional<AccessToken> accessTokenOptional = user.getAccessTokens().stream()
                    .filter(x -> objeto.accessToken.getKeyword().equals(x.getKeyword()) == true).findFirst();
            if (!accessTokenOptional.isPresent()) {
                return RespuestasAccessToken.TOKENNOENCONTRADO;
            } else {
                AccessToken accessToken = accessTokenOptional.get();
                accessToken.setActive(!accessToken.isActive());
                try {
                    accessTokenRepository.save(accessToken);

                    return (accessToken.isActive()) ? RespuestasAccessToken.TOKENACTIVADO
                            : RespuestasAccessToken.TOKENDESACTIVADO;
                } catch (Exception e) {
                    return RespuestasAccessToken.FALLOGENERARTOKEN;
                }
            }
        } else {
            return RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS;
        }
    }

    public String findByKeyword(ObjetoAccessToken objeto) {
         if (objeto == null || objeto.accessToken == null || objeto.user == null) {
            return RespuestasGenerales.JSONINCORRECTO;
        }
        String verificacion = GeneralFunctions.verificarCorreoPassword(objeto.user.getEmail(),
                objeto.user.getPassword());
        if (verificacion.length() != 0) {
            return verificacion;
        }
        User user = userRepository.findByEmailAndPassword(objeto.user.getEmail(), objeto.user.getPassword());
        if (user != null) {
            // si keyword null retornar todos
            String keyword = objeto.accessToken.getKeyword();
            
            if(!user.isActive()){
                return RespuestasUsuarios.USUARIODESACTIVADO;
            }
            if (user.getAccessTokens() != null && !user.getAccessTokens().isEmpty()) {
                List<AccessToken> accessTokenOptional = user.getAccessTokens().stream().collect(Collectors.toList());
                if (keyword != null && keyword == "" && keyword.isBlank()) {
                    accessTokenOptional = user.getAccessTokens().stream().filter(x -> x.getKeyword().contains(keyword))
                            .collect(Collectors.toList());
                }
                return GeneralFunctions.ConverToString(accessTokenOptional);
            } else {
                return RespuestasAccessToken.TOKENNOENCONTRADO;
            }
        } else {
            return RespuestasUsuarios.USUARIOCONTRASENIAINCORRECTOS;
        }
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[12];
        secureRandom.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }

}
