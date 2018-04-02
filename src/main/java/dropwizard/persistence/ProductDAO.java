package dropwizard.persistence;
import dropwizard.model.Product;
import dropwizard.model.User;
import dropwizard.persistence.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    UserDAO userDAO;
    private PreparedStatement getAllProducts;
    private PreparedStatement addProduct;
    private PreparedStatement updateProduct;
    private PreparedStatement deleteProduct;
    private PreparedStatement deleteNullProduct;

    private List<Product> allProducts;
    private Connection dbConnection;
    private Database database = Database.getDatabase();

    public ProductDAO(UserDAO userDAO) throws IOException, SQLException {
        allProducts = new ArrayList<>();
        dbConnection = database.getDbConnection();
        preparedStatements();
        this.userDAO = userDAO;
    }
//        resultSet.getString("product_omschrijving"),
//        resultSet.getString("product_fabrikant"),
//        resultSet.getInt("product_gecheckt"),
//        resultSet.getDouble("product_prijs"),
//        resultSet.getInt("product_gebruiker_id"),
//        resultSet.getString("imgURL"),
//        resultSet.getString("categorie")
    public List<Product> getAllProducts(){
        database.checkConnection();
        try {
            allProducts.clear();
            ResultSet products = getAllProducts.executeQuery();
            while(products.next()){
                int id = products.getInt("product_id");
                String omschrijving = products.getString("product_omschrijving");
                String fabrikant = products.getString("product_fabrikant");
                int gecheckt = products.getInt("product_gecheckt");
                Double prijs = products.getDouble("product_prijs");
                int gebruiker_id = products.getInt("product_gebruiker_id");
                String imgURL = products.getString("imgURL");
                String categorie = products.getString("categorie");


                //create and add product
                Product product = new Product(id,omschrijving,fabrikant,gecheckt, prijs,gebruiker_id, imgURL, categorie);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void addProduct(Product product, User authenticator) {
        try {

            setPreparedStatement(product, addProduct, authenticator);
            addProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateProduct(Product product, User authenticator) {
        try {
            setPreparedStatement(product, updateProduct, authenticator);
            updateProduct.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String omschrijving, String fabrikant){
        try {
            PreparedStatement statement = deleteProduct;

            if(fabrikant == null){
                statement = deleteNullProduct;
            }
            statement.setObject(1, omschrijving);
            statement.setObject(2, fabrikant);
            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private PreparedStatement setPreparedStatement(Product product, PreparedStatement statement, User authenticator) {

        User user = userDAO.getByUsername(authenticator.getUsername());
        int userId = user.getId();
//        this.omschrijving = omschrijving;
//        this.fabrikant = fabrikant;
//        this.gecheckt = gecheckt;
//        this.prijs = prijs;
//        this.product_gebruiker_id = product_gebruiker_id;
//        this.imgURL = imgURL;
//        this.categorie = categorie;
        try {
            System.out.println(product.getOmschrijving());
            System.out.println(product.getFabrikant());
            System.out.println(product.getPrijs());
            System.out.println(userId);
            System.out.println(product.getImgURL());
            System.out.println(product.getCategorie());

            statement.setString( 1, product.getOmschrijving());
            statement.setString( 2, product.getFabrikant());
            statement.setInt( 3, 1 );
            statement.setDouble( 4, product.getPrijs());
            statement.setInt( 5, userId);
            statement.setString( 6, product.getImgURL());
            statement.setString( 7, product.getCategorie());


//            if (product.getId() != 0) {
//                statement.setInt(70, product.getId());
//            }
            System.out.println("statement = " + statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
//    public Collection<Product> getAllProducts() {
//        ResultSet resultSet;
//        try {
//            resultSet = getAllProducts.executeQuery();
//            return createProduct(resultSet, allProducts);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    private List<Product> createProduct(ResultSet resultSet, List<Product> products) {
//        products.clear();
////        this.omschrijving = omschrijving;
////        this.fabrikant = fabrikant;
////        this.gecheckt = gecheckt;
////        this.prijs = prijs;
////        this.product_gebruiker_id = product_gebruiker_id;
////        this.imgURL = imgURL;
//        try {
//            while (resultSet.next()) {
//                products.add(new Product(
//                        resultSet.getInt("product_id"),
//                        resultSet.getString("product_omschrijving"),
//                        resultSet.getString("product_fabrikant"),
//                        resultSet.getInt("product_gecheckt"),
//                        resultSet.getDouble("product_prijs"),
//                        resultSet.getInt("product_gebruiker_id"),
//                        resultSet.getString("imgURL"),
//                        resultSet.getString("categorie")
//                ));
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return products;
//    }

    private void preparedStatements(){
        try {
            getAllProducts = dbConnection.prepareStatement("SELECT * FROM product;");
            addProduct = dbConnection.prepareStatement("INSERT INTO product(product_omschrijving, product_fabrikant, product_gecheckt, product_prijs, product_gebruiker_id, imgURL, categorie) VALUES (?,?,?,?,?,?,?);");
            updateProduct = dbConnection.prepareStatement("UPDATE product SET product_omschrijving = ?, product_fabrikant = ?, product_gecheckt = ?, product_gebruiker_id = ?, imgURL = ?, categorie = ? WHERE product_id = ?;");
            deleteProduct = dbConnection.prepareStatement("DELETE FROM product WHERE product_omschrijving = ? AND product_fabrikant = ?;");
            deleteNullProduct = dbConnection.prepareStatement("DELETE FROM product WHERE product_omschrijving = ? AND product_fabrikant IS ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
