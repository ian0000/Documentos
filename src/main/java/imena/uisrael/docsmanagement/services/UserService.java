package imena.uisrael.docsmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.model.User;
import imena.uisrael.docsmanagement.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepository;
    public User saveUser(String email, String password) {   
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setPassword(password);//TODO encrypt password
            return userRepository.save(user);
        }else{
            return null;
        }
    }
    public User findByEmail(String email) {   
        return userRepository.findByEmail(email);
    }
    public User findByEmailAndPassword(String email,String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
