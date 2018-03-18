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

    private PreparedStatement getAllProducts;
//    private PreparedStatement addUser;
//    private PreparedStatement getLoginUser;
//    private PreparedStatement updateUser;
//    private PreparedStatement getPassword;
//    private PreparedStatement deleteUser;
//    private PreparedStatement countUsers;

    private List<Product> allProducts;
    private Connection dbConnection;
    private Database database = Database.getDatabase();

    public ProductDAO() throws IOException, SQLException {
        allProducts = new ArrayList<>();
        dbConnection = database.getDbConnection();
        preparedStatements();
    }

    public Collection<Product> getAllProducts() {
        ResultSet resultSet;
        try {
            resultSet = getAllProducts.executeQuery();
            return createProduct(resultSet, allProducts);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Product> createProduct(ResultSet resultSet, List<Product> products) {
        products.clear();
//        this.omschrijving = omschrijving;
//        this.fabrikant = fabrikant;
//        this.gecheckt = gecheckt;
//        this.prijs = prijs;
//        this.product_gebruiker_id = product_gebruiker_id;
//        this.imgURL = imgURL;
        try {
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getString("omschrijving"),
                        resultSet.getString("fabrikant"),
                        resultSet.getInt("gecheckt"),
                        resultSet.getDouble("prijs"),
                        resultSet.getInt("product_gebruiker_id"),
                        resultSet.getString("imgURL"),
                        resultSet.getString("categorie")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }

    private void preparedStatements(){
        try {
//            deleteUser = dbConnection.prepareStatement("UPDATE gebruiker SET gebruiker_actief = FALSE, gebruiker_laast_gewijzigd = CURDATE() WHERE gebruiker_gebruikersnaam = ?");
//            getPassword = dbConnection.prepareStatement("SELECT gebruiker_wachtwoord FROM gebruiker WHERE gebruiker_gebruikersnaam = ?");
            getAllProducts = dbConnection.prepareStatement("SELECT * FROM product;");
//            updateUser = dbConnection.prepareStatement("UPDATE gebruiker SET gebruiker_wachtwoord = ?, gebruiker_voornaam = ?, gebruiker_tussenvoegsel = ?, gebruiker_achternaam = ?, gebruiker_email = ?, gebruiker_rol = ?, gebruiker_laast_gewijzigd = CURDATE() WHERE gebruiker_gebruikersnaam =?;");
//            addUser = dbConnection.prepareStatement("INSERT INTO gebruiker(gebruiker_voornaam, gebruiker_tussenvoegsel, gebruiker_achternaam, gebruiker_gebruikersnaam, gebruiker_wachtwoord, gebruiker_email, gebruiker_rol, gebruiker_aangemaakt_op, gebruiker_laast_gewijzigd)VALUES (?,?,?,?,?,?,?,CURDATE(),CURDATE())");
//            getLoginUser = dbConnection.prepareStatement("SELECT gebruiker_gebruikersnaam, gebruiker_wachtwoord, gebruiker_rol, gebruiker_voornaam, gebruiker_tussenvoegsel, gebruiker_achternaam, gebruiker_email  FROM gebruiker WHERE gebruiker_gebruikersnaam = ? AND gebruiker_actief = TRUE");
//            countUsers = dbConnection.prepareStatement("SELECT COUNT(*) AS aantal FROM gebruiker");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
