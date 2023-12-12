package imena.uisrael.docsmanagement.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.repo.AccessTokenRepo;
import imena.uisrael.docsmanagement.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccessTokenService {
    @Autowired
    private AccessTokenRepo accessTokenRepository;

    @Autowired
    private UserRepo userRepository;

    public AccessToken saveAccessToken(String email, String password, String keyword) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            AccessToken accessToken = new AccessToken();
            accessToken.setToken(generateToken());
            accessToken.setKeyword(keyword);
            accessToken.setUser(user);
            accessToken.setActive(true);
            return accessTokenRepository.save(accessToken);
        } else {
            return null;
        }
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[12];
        secureRandom.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }
    public AccessToken updateAccessToken(AccessToken accessToken) {
        return accessTokenRepository.save(accessToken);
    }

}
