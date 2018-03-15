package dropwizard.resource;

import com.fasterxml.jackson.annotation.JsonView;
import dropwizard.View;
import dropwizard.model.Product;
import dropwizard.service.ProductService;
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
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;
    @Inject
    public ProductResource(ProductService service){
        this.service = service;
    }

    @GET
    @JsonView(View.Public.class)
    public Collection<Product> retrieveAll(){

        return service.getAll();
    }
}
