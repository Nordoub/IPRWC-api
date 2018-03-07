package dropwizard.service;


import dropwizard.model.User;
import dropwizard.persistence.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;


import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;


public class UserAuthenticator implements Authenticator<BasicCredentials, User>{

    private final UserService service;

    @Inject
    public UserAuthenticator(UserService userService)
    {
        this.service = userService;
    }


    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        User user = service.loginUser(credentials.getUsername(), credentials.getPassword());
        if(user.getUsername() != null && user.getUsername().equals(credentials.getUsername())){
            return Optional.of(user);
        }else
        {
            throw new WebApplicationException(403);
        }

    }

}