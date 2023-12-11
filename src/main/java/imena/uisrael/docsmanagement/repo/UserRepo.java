package imena.uisrael.docsmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import imena.uisrael.docsmanagement.model.User;


public interface UserRepo extends JpaRepository<User, Long>{

        User findByEmailAndPassword(String email, String password); //TODO encrypt password
        User findByEmail(String email );
    
}
