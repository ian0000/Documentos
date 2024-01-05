package imena.uisrael.docsmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import imena.uisrael.docsmanagement.DTO.ObjetoAccessToken;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.Parciales;
import imena.uisrael.docsmanagement.services.AccessTokenService;
import imena.uisrael.docsmanagement.services.GeneralFunctions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tokens")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @PostMapping("/generate")
    public ResponseEntity<Object> createAccessToken(@RequestBody ObjetoAccessToken objeto) {
        String respuestmp = accessTokenService.saveAccessToken(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, AccessToken.class, Parciales.RespuestasAccessTokenHash);
    }

    @PostMapping("/changestate")
    public ResponseEntity<Object> changeState(@RequestBody ObjetoAccessToken objeto) {
        String respuestmp = accessTokenService.saveAccessToken(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, AccessToken.class, Parciales.RespuestasAccessTokenHash);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateToken(@RequestBody ObjetoAccessToken objeto) {
        String respuestmp = accessTokenService.updateAccessToken(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, AccessToken.class, Parciales.RespuestasAccessTokenHash);
    }

    @GetMapping("/findbykeyword")
    public ResponseEntity<Object> getAccessTokenListByLikeKeyword(@RequestBody ObjetoAccessToken objeto) {

        String respuestmp = accessTokenService.findByKeyword(objeto);
        return GeneralFunctions.DevolverRespuesta(respuestmp, List.class, Parciales.RespuestasAccessTokenHash);

    }

}
