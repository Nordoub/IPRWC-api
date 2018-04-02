package dropwizard.service;

import dropwizard.model.User;
import dropwizard.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class UserService extends BaseService {

    private final UserDAO dao;
    private final PasswordService passwordService;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
        this.passwordService = new PasswordService();
    }

    public Collection<User> getAll(){
        return dao.getUsers();
    }

    public void add(User user) {
        try {
            user.setPassword(passwordService.createHash(user.getPassword()));
            dao.add(user);
        } catch (PasswordService.CannotPerformOperationException e) {
            e.printStackTrace();
        }
    }
    public void register(User user) {
        try {
            user.setPassword(passwordService.createHash(user.getPassword()));
            dao.register(user);
        } catch (PasswordService.CannotPerformOperationException e) {
            e.printStackTrace();
        }
    }

    public User getLoggedUser(String userName){
        return dao.loginUser(userName);
    }

    public User loginUser(String userName, String password) {
        User user  = dao.loginUser(userName);
        Boolean correctPass = false;
        if (user.getPassword() != null) {
            try {
                correctPass = passwordService.verifyPassword(password, user.getPassword());
            } catch (PasswordService.CannotPerformOperationException e) {
                e.printStackTrace();
            } catch (PasswordService.InvalidHashException e) {
                e.printStackTrace();
            }
        }
        if (correctPass) {
            return user;
        } else{
            return new User();
        }
    }

    public void update(User user){
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            try {
                user.setPassword(passwordService.createHash(user.getPassword()));
            } catch (PasswordService.CannotPerformOperationException e) {
                e.printStackTrace();
            }
        } else {
            user.setPassword(dao.getPassword(user.getUsername()));
        }
        dao.update(user);
    }

//    public String getEmailByUsername(String username){
//        return dao.getByUsername(username).getEmail();
//    }
//
//    public int getUserCount() {
//        return dao.getUserCount();
//    }

    public void delete(String gebruikersnaam){
        dao.delete(gebruikersnaam);
    }

}