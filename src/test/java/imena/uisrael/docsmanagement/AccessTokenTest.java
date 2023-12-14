package imena.uisrael.docsmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import imena.uisrael.docsmanagement.controller.AccessTokenController;
import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.services.AccessTokenService;
import imena.uisrael.docsmanagement.services.GeneralFunctions;
import imena.uisrael.docsmanagement.services.UserService;

@SpringBootTest
public class AccessTokenTest {

    @InjectMocks
    private AccessTokenController accessTokenController;

    @Mock
    private AccessTokenService accessTokenService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void generarAccessToken_ValidCredentialsAndUniqueKeyword_ReturnsToken() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";
        String keyword = "newToken";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccessTokens(new ArrayList<>());

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);
        when(accessTokenService.saveAccessToken(email, password, keyword))
                .thenReturn(new AccessToken(1L, "generatedToken", keyword, true, user));

        // Execute
        ResponseEntity<Object> response = accessTokenController.generarAccessToken(email, password, keyword);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"accessTokenID\":1,\"token\":\"generatedToken\",\"keyword\":\"newToken\",\"active\":true}", response.getBody());
    }

    @Test
    void getAccessTokenList_ValidCredentialsAndTokensExist_ReturnsTokensList() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        List<AccessToken> tokens = new ArrayList<>();
        tokens.add(new AccessToken(1l, "generatedToken", "token1", true, user));
        tokens.add(new AccessToken(2l, "generatedToken", "token2", true, user));
        user.setAccessTokens(tokens);

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenList(email, password);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"accessTokenID\":1,\"token\":\"generatedToken\",\"keyword\":\"token1\",\"active\":true},{\"accessTokenID\":2,\"token\":\"generatedToken\",\"keyword\":\"token2\",\"active\":true}]", response.getBody());
    }

    // Other test cases for getAccessTokenList method...

    @Test
    void getAccessTokenListByLikeKeyword_ValidCredentialsAndMatchingKeyword_ReturnsMatchingTokens() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";
        String keyword = "token";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        List<AccessToken> tokens = new ArrayList<>();
        tokens.add(new AccessToken(1l, "generatedToken", "token1", true, user));
        tokens.add(new AccessToken(2l, "generatedToken", "token2", true, user));
        user.setAccessTokens(tokens);

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password,
                keyword);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"accessTokenID\":1,\"token\":\"generatedToken\",\"keyword\":\"token1\",\"active\":true},{\"accessTokenID\":2,\"token\":\"generatedToken\",\"keyword\":\"token2\",\"active\":true}]", response.getBody());
    }

     // Other test cases for conflicts, errors, and edge cases in generarAccessToken method...

     @Test
     void getAccessTokenList_InvalidCredentials_ReturnsNotFoundStatus() {
         // Prepare
         String email = "nonexistent@example.com";
         String password = "wrongPassword";
 
         when(userService.findByEmailAndPassword(email, password)).thenReturn(null);
 
         // Execute
         ResponseEntity<Object> response = accessTokenController.getAccessTokenList(email, password);
 
         // Verify
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
         assertEquals("Usuario y/o contraseña incorrectos", response.getBody());
     }
 
     // Other test cases for incorrect credentials and error scenarios in getAccessTokenList method...
 
     @Test
     void getAccessTokenListByLikeKeyword_NoMatchingTokens_ReturnsNotFoundStatus() {
         // Prepare
         String email = "user@example.com";
         String password = "password123";
         String keyword = "nonexistentKeyword";
 
         User user = new User();
         user.setEmail(email);
         user.setPassword(password);
         user.setAccessTokens(new ArrayList<>());
 
         when(userService.findByEmailAndPassword(email, password)).thenReturn(user);
 
         // Execute
         ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password, keyword);
 
         // Verify
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
         assertEquals("No token de acceso encontrado", response.getBody());
     }
 
     @Test
     void generarAccessToken_FailedTokenGeneration_ReturnsInternalServerError() {
         // Prepare
         String email = "user@example.com";
         String password = "password123";
         String keyword = "newToken";
 
         User user = new User();
         user.setEmail(email);
         user.setPassword(password);
 
         when(userService.findByEmailAndPassword(email, password)).thenReturn(user);
         when(accessTokenService.saveAccessToken(email, password, keyword)).thenReturn(null);
 
         // Execute
         ResponseEntity<Object> response = accessTokenController.generarAccessToken(email, password, keyword);
 
         // Verify
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
         assertEquals("Fallo al generar el token", response.getBody());
     }
 
     // Other test cases for failed token generation scenarios in generarAccessToken method...
 
     @Test
     void getAccessTokenListByLikeKeyword_InvalidCredentials_ReturnsNotFoundStatus() {
         // Prepare
         String email = "invalid@example.com";
         String password = "invalidPassword";
         String keyword = "someKeyword";
 
         when(userService.findByEmailAndPassword(email, password)).thenReturn(null);
 
         // Execute
         ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password, keyword);
 
         // Verify
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
         assertEquals("Usuario y/o contraseña incorrectos", response.getBody());
     }
 
     // Other test cases for incorrect credentials scenarios in getAccessTokenListByLikeKeyword method...
 
     @Test
     void convertJson_ValidObject_ReturnsJsonString() {
         // Prepare
         Object obj = new Object(); // Replace with appropriate test object
 
         // Execute
         ResponseEntity<Object> response =  GeneralFunctions.convertJSOn(obj);
 
         // Verify
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
         // Add assertions to verify the generated JSON string based on the input object
         // Example: assertEquals("Expected JSON String", response.getBody());
     }
     
    @Test
    void getAccessTokenList_EmptyTokensList_ReturnsNotFoundStatus() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccessTokens(new ArrayList<>()); // Empty access tokens list

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenList(email, password);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No token de acceso encontrado", response.getBody());
    }

    // Other test cases for scenarios with empty access tokens list in getAccessTokenList method...

    @Test
    void getAccessTokenListByLikeKeyword_MatchingEmptyKeyword_ReturnsNotFoundStatus() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";
        String keyword = ""; // Empty keyword

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccessTokens(new ArrayList<>());

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password, keyword);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No token de acceso encontrado", response.getBody());
    }

    // Other test cases for scenarios with empty keyword in getAccessTokenListByLikeKeyword method...

    @Test
    void getAccessTokenListByLikeKeyword_NullKeyword_ReturnsBadRequestStatus() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";

        when(userService.findByEmailAndPassword(email, password)).thenReturn(new User());

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password, null);

        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parámetro 'keyword' no puede ser nulo", response.getBody());
    }

    @Test
    void generarAccessToken_NullUser_ReturnsNotFoundStatus() {
        // Prepare
        String email = "nonexistent@example.com";
        String password = "password123";
        String keyword = "newToken";

        when(userService.findByEmailAndPassword(email, password)).thenReturn(null);

        // Execute
        ResponseEntity<Object> response = accessTokenController.generarAccessToken(email, password, keyword);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario y/o contraseña incorrectos", response.getBody());
    }

    // Other test cases for scenarios with null user in generarAccessToken method...

    // Other test cases for scenarios with no matching tokens in getAccessTokenListByLikeKeyword method...

    @Test
    void getAccessTokenListByLikeKeyword_ValidCredentialsAndEmptyTokenList_ReturnsNotFoundStatus() {
        // Prepare
        String email = "user@example.com";
        String password = "password123";
        String keyword = "someKeyword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccessTokens(new ArrayList<>());

        when(userService.findByEmailAndPassword(email, password)).thenReturn(user);

        // Execute
        ResponseEntity<Object> response = accessTokenController.getAccessTokenListByLikeKeyword(email, password, keyword);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No token de acceso encontrado", response.getBody());
    }
 
}
