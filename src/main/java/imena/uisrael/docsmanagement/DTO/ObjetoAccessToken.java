package imena.uisrael.docsmanagement.DTO;

import imena.uisrael.docsmanagement.model.AccessToken;
import imena.uisrael.docsmanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ObjetoAccessToken {
    // de aqui solo necesito email password keyword
    public AccessToken accessToken;
    public User user;
}
