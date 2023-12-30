package imena.uisrael.docsmanagement.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imena.uisrael.docsmanagement.model.AccessToken;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

    @Query("SELECT a FROM AccessToken a WHERE a.token = :token AND a.active = true")
    AccessToken findByToken(String token);

}
