package imena.uisrael.docsmanagement.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import imena.uisrael.docsmanagement.model.AccessToken;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {


}
