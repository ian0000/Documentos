package imena.uisrael.docsmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import imena.uisrael.docsmanagement.model.User;


public interface UserRepo extends JpaRepository<User, Long>{

        User findByEmailAndPassword(String email, String password);
        User findByEmail(String email );
    
}
