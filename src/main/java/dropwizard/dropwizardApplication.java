package dropwizard;

//import dropwizard.persistence.ClientDAO;
import dropwizard.persistence.UserDAO;
//import dropwizard.resource.ClientResource;
import dropwizard.resource.UserResource;
import dropwizard.service.*;
import dropwizard.model.User;
import dropwizard.persistence.ProductDAO;
import dropwizard.resource.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.HEAD;
import java.io.IOException;
import java.sql.SQLException;

public class dropwizardApplication extends Application<dropwizardConfiguration> {

    public static void main(final String[] args) throws Exception {
        new dropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard";
    }

    @Override
    public void initialize(final Bootstrap<dropwizardConfiguration> bootstrap) {
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(final dropwizardConfiguration configuration,
                    final Environment environment) throws IOException, SQLException {

        UserService userService = new  UserService(new UserDAO());
        UserResource userResource = new UserResource(userService);
//        ClientResource clientResource = new ClientResource(new ClientService(new ClientDAO(new UserDAO())));
        ProductResource productResource = new ProductResource(new ProductService(new ProductDAO(new UserDAO())));


        environment.jersey().register(userResource);
//        environment.jersey().register(clientResource);
        environment.jersey().register(productResource);

        //register the userAuthenticator and the UserAuthorizer.
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
        .setAuthenticator(new UserAuthenticator(userService))
        .setAuthorizer(new UserAuthorizer())
        .setRealm("SECRET REALM")
        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

    }

}
