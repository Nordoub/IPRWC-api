package dropwizard.resource;

import com.fasterxml.jackson.annotation.JsonView;
import dropwizard.View;
import dropwizard.model.User;
import dropwizard.service.UserService;
import io.dropwizard.auth.Auth;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private  final UserService service;

    @Inject
    public UserResource(UserService service){
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<User> retrieveAll(){

        return service.getAll();
    }

    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void createUser(User user){
        service.add(user);
    }

    @RolesAllowed("admin")
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void updateUser(User user){
        service.update(user);


    }

    @GET
    @Path("/login")
    @JsonView(View.Public.class)
    public User authenticate(@Auth User authenticator)
    {
        return authenticator;
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public int countUsers(){
        return service.getUserCount();
    }

    @RolesAllowed("admin")
    @GET
    @Path("/test")
    @JsonView(View.Public.class)
    public String test(User user)
    {
        return "acces";
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/delete/{username}")
    @JsonView(View.Protected.class)
    public void deleteUser(@PathParam("username") String gebruikersnaam){
        service.delete(gebruikersnaam);
    }

    @GET
    @Path("/email/{username}")
    @JsonView(View.Public.class)
    @Produces(MediaType.TEXT_PLAIN)
    public String getEmail(@PathParam("username") String username){
        System.out.println(service.getEmailByUsername(username));
        return service.getEmailByUsername(username);
    }

    @GET
    @Path("/{username}")
    @JsonView(View.Public.class)
    public User getLoggedUser(@PathParam("username") String username){
        return service.getLoggedUser(username);
    }
}
