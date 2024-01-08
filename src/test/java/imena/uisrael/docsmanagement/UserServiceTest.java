package imena.uisrael.docsmanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasUsuarios;
import imena.uisrael.docsmanagement.repo.UserRepo;
import imena.uisrael.docsmanagement.services.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser_NewUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "testPassword";
        when(userRepository.findByEmail(email)).thenReturn(null);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        String result = userService.saveUser(email, password);

        // Assert
        assertNotNull(result);
        // Add more specific assertions if you have any logic to convert User to String
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSaveUser_ExistingUser_ReturnsConflict() {
        // Arrange
        String email = "existing@example.com";
        String password = "existingPassword";
        when(userRepository.findByEmail(email)).thenReturn(new User(/* initialize necessary fields */));

        // Act
        String result = userService.saveUser(email, password);

        // Assert
        assertEquals(RespuestasUsuarios.USUARIOEXISTE, result);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser_InvalidOldUserData_ReturnsError() {
        // Arrange
        User newUser = new User(/* Set necessary fields for new user */);
        User oldUser = new User(/* Set necessary fields for old user */);
        oldUser.setEmail("existing@example.com"); // Set a non-null email
        oldUser.setPassword("password123"); // Set a non-null password
        when(userRepository.findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword())).thenReturn(null);

        // Act
        String result = userService.updateUser(newUser, oldUser);

        // Assert
        assertEquals(RespuestasUsuarios.USUARIONOENCONTRADO, result);
        verify(userRepository, times(1)).findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser_InvalidNewUserData_ReturnsError() {
        // Arrange
        User newUser = new User(/* Set necessary fields for new user */);
        User oldUser = new User(/* Set necessary fields for old user */);
        oldUser.setEmail("existing@example.com"); // Set a non-null email
        oldUser.setPassword("password123"); // Set a non-null password
        newUser.setEmail("existing@example.com"); // Set a non-null email
        newUser.setPassword("password123"); // Set a non-null password
        when(userRepository.findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword())).thenReturn(oldUser);

        // Act
        String result = userService.updateUser(newUser, oldUser);

        // Assert
        // Add assertions for the specific error response returned for invalid new user
        // data
        verify(userRepository, times(1)).findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword());
    }

    @Test
    public void testUpdateUser_NonExistentUser_ReturnsError() {
        // Arrange
        User newUser = new User(/* Set necessary fields for new user */);
        User oldUser = new User(/* Set necessary fields for old user */);
        oldUser.setEmail("existing@example.com"); // Set a non-null email
        oldUser.setPassword("password123"); // Set a non-null password

        when(userRepository.findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword())).thenReturn(null);

        // Act
        String result = userService.updateUser(newUser, oldUser);

        // Assert
        assertEquals(RespuestasUsuarios.USUARIONOENCONTRADO, result);
        verify(userRepository, times(1)).findByEmailAndPassword(oldUser.getEmail(), oldUser.getPassword());
        verify(userRepository, never()).save(any(User.class));
    }
}
