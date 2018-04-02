package dropwizard.persistence;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection dbConnection;
    private static Database database;
    private String line;
    private String[] config = getDatabaseConfig();
    private String url ="jdbc:mysql://"+ config[0] + "/" + config[3];

    /**
     * This is the constructor for the Database. It
     * loads the database driver.
     */
    private Database() {

        // Load dbms driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            System.out.println(e);
        }

        try {
//            String url = "jdbc:mysql://localhost:5432/iprwc";
            dbConnection = DriverManager.getConnection(url, config[1],config[2]);
//            dbConnection = DriverManager.getConnection(url, "root","root");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    /**
     * @return the Database
     */
    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Connection checkConnection() {
        try {
            if(dbConnection.isClosed() || dbConnection == null) {
                config = getDatabaseConfig();
                dbConnection = DriverManager.getConnection(url, config[1],config[2]);
                return dbConnection;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return a String array with the information from the webapp.conf file
     */
    private String[] getDatabaseConfig() {
        String[] config = new String[4];
        //System.out.println("config: "+config[0].toString()+" "+config[1]+" "+config[2]+" "+config[3]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("webapp.conf")));
                while((line = reader.readLine()) != null){
                if (line.contains("host")){
                    config[0] = line.substring(line.indexOf(":") + 1);
                }
                if (line.contains("username")){
                    config[1] = line.substring(line.indexOf(":") + 1);
                }
                if (line.contains("password")){
                    config[2] = line.substring(line.indexOf(":") + 1);
                }
                if (line.contains("database")){
                    config[3] = line.substring(line.indexOf(":") + 1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    /**
     * @return the database connection
     */
    public Connection getDbConnection() {
        return dbConnection;
    }

}

