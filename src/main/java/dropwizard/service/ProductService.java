package dropwizard.service;

import dropwizard.model.Product;
import dropwizard.persistence.ProductDAO;

import javax.inject.Inject;
import java.util.Collection;

public class ProductService {
    private final ProductDAO dao;
    private final PasswordService passwordService;

    @Inject
    public ProductService(ProductDAO dao) {
        this.dao = dao;
        this.passwordService = new PasswordService();
    }

    public Collection<Product> getAll(){
        return dao.getAllProducts();
    }
}
