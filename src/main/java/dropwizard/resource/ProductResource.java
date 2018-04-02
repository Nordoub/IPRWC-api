package dropwizard.resource;

import com.fasterxml.jackson.annotation.JsonView;
import dropwizard.View;
import dropwizard.model.Product;
import dropwizard.model.User;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Public.class)
    public Collection<Product> retrieveAll(){

        return service.getAll();
    }
    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void addProduct(Product product, @Auth User authenticator) {
        service.add(product, authenticator);
    }

    @RolesAllowed("admin")
    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void editProduct(Product product, @Auth User authenticator) {
        service.edit(product, authenticator);
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/delete")
    @JsonView(View.Protected.class)
    public void delete(@QueryParam("omschrijving") String omschrijving , @QueryParam("fabrikant") String fabrikant) {
        service.delete(omschrijving, fabrikant);
    }

}
