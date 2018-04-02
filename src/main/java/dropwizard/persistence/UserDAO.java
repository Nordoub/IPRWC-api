package dropwizard.persistence;

//import dropwizard.model.Product;
import dropwizard.model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private PreparedStatement getUsers;
    private PreparedStatement addUser;
    private PreparedStatement getLoginUser;
    private PreparedStatement updateUser;
    private PreparedStatement getPassword;
    private PreparedStatement deleteUser;
    private PreparedStatement countUsers;

    private List<User> allUsers;
    private Connection dbConnection;
    private Database database = Database.getDatabase();

    public UserDAO() throws IOException, SQLException {
        allUsers = new ArrayList<>();
        dbConnection = database.getDbConnection();
        preparedStatements();
    }

    public User getByUsername(String username) {
        database.checkConnection();
        allUsers = getUsers();
        Optional<User> result = allUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        return result.isPresent() ? result.get() : null;
    }

    public List<User> getUsers(){
        database.checkConnection();
        try {
            allUsers.clear();
            ResultSet users = getUsers.executeQuery();
            while(users.next()){
                int id = users.getInt("gebruiker_id");
                String gebruikersnaam = users.getString("gebruiker_gebruikersnaam");
                String password = users.getString("gebruiker_wachtwoord");
                String voornaam = users.getString("gebruiker_voornaam");
                String tussenvoegsel = users.getString("gebruiker_tussenvoegsel");
                String achternaam = users.getString("gebruiker_achternaam");
                String email = users.getString("gebruiker_email");
                String rol = users.getString("gebruiker_rol");
                String created_at = users.getString("gebruiker_aangemaakt_op");
                String updated_at = users.getString("gebruiker_laast_gewijzigd");
                Boolean actief = users.getBoolean("gebruiker_actief");

                //create and add user
                User user = new User(id,gebruikersnaam,password,voornaam,tussenvoegsel,achternaam,email,rol,created_at,updated_at,actief);
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(User user){
        database.checkConnection();
        try {
            addUser.setString(1,user.getUsername());
            addUser.setString(2,user.getPassword());
            addUser.setString(3,user.getFirstname());
            addUser.setString(4,user.getPreposition());
            addUser.setString(5,user.getLastname());
            addUser.setString(6,user.getEmail());
            addUser.setString(7,user.getRole());
            addUser.setString(8, "1");
            addUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void register(User user){
        database.checkConnection();
        try {
            addUser.setString(1,user.getUsername());
            addUser.setString(2,user.getPassword());
            addUser.setString(3,user.getFirstname());
            addUser.setString(4,user.getPreposition());
            addUser.setString(5,user.getLastname());
            addUser.setString(6,user.getEmail());
            addUser.setString(7, "guest");
            addUser.setString(8, "1");
            addUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user){
        database.checkConnection();
        try {
            updateUser.setString(1,user.getUsername());
            updateUser.setString(2, user.getPassword());
            updateUser.setString(3 ,user.getFirstname());
            updateUser.setString(4 ,user.getPreposition());
            updateUser.setString(5 ,user.getLastname());
            updateUser.setString(6 ,user.getEmail());
            updateUser.setString(7 ,user.getRole());
            updateUser.setString(8,user.getUsername());
            updateUser.executeLargeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String gebruikersnaam){
        database.checkConnection();
        try {
            deleteUser.setString(1, gebruikersnaam);
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized User loginUser(String userName) {
        database.checkConnection();
        User loginUser = new User();

        try {
            getLoginUser.setString(1, userName);
            ResultSet resultSet = getLoginUser.executeQuery();
            if(resultSet.next()){
                loginUser.setUsername( resultSet.getString("gebruiker_gebruikersnaam"));
                loginUser.setPassword( resultSet.getString("gebruiker_wachtwoord"));
                loginUser.setRole(resultSet.getString("gebruiker_rol"));
                loginUser.setFirstname(resultSet.getString("gebruiker_voornaam"));
                loginUser.setPreposition(resultSet.getString("gebruiker_tussenvoegsel"));
                loginUser.setLastname(resultSet.getString("gebruiker_achternaam"));
                loginUser.setEmail(resultSet.getString("gebruiker_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginUser;
    }

    public String getPassword(String username){
        database.checkConnection();
        try {
            getPassword.setString(1, username);
            ResultSet resultSet = getPassword.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("gebruiker_wachtwoord");
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getUserCount() {
        database.checkConnection();
        try {
            ResultSet resultSet = countUsers.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("aantal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void preparedStatements(){
        try {
            deleteUser = dbConnection.prepareStatement("UPDATE gebruiker SET gebruiker_actief = FALSE, gebruiker_laast_gewijzigd = CURDATE() WHERE gebruiker_gebruikersnaam = ?");
            getPassword = dbConnection.prepareStatement("SELECT gebruiker_wachtwoord FROM gebruiker WHERE gebruiker_gebruikersnaam = ?");
            getUsers = dbConnection.prepareStatement("SELECT * FROM gebruiker where gebruiker_actief = TRUE ;");
            updateUser = dbConnection.prepareStatement("UPDATE gebruiker SET gebruiker_gebruikersnaam = ?, gebruiker_wachtwoord = ?, gebruiker_voornaam = ?, gebruiker_tussenvoegsel = ?, gebruiker_achternaam = ?, gebruiker_email = ?, gebruiker_rol = ?, gebruiker_laast_gewijzigd = CURDATE() WHERE gebruiker_gebruikersnaam =?;");
            addUser = dbConnection.prepareStatement("INSERT INTO gebruiker(gebruiker_gebruikersnaam, gebruiker_wachtwoord, gebruiker_voornaam, gebruiker_tussenvoegsel, gebruiker_achternaam, gebruiker_email, gebruiker_rol, gebruiker_aangemaakt_op, gebruiker_laast_gewijzigd, gebruiker_actief) VALUES (?,?,?,?,?,?,?,CURDATE(),CURDATE(),?)");
            getLoginUser = dbConnection.prepareStatement("SELECT gebruiker_gebruikersnaam, gebruiker_wachtwoord, gebruiker_rol, gebruiker_voornaam, gebruiker_tussenvoegsel, gebruiker_achternaam, gebruiker_email  FROM gebruiker WHERE gebruiker_gebruikersnaam = ? AND gebruiker_actief = TRUE");
            countUsers = dbConnection.prepareStatement("SELECT COUNT(*) AS aantal FROM gebruiker");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
