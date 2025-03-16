
package BUS;

import DAO.UsersDAO;
import DTO.UsersDTO;


public class UsersBUS {
    private UsersDAO userDAO = new UsersDAO();
    
    public UsersDTO login(String userName, String password) {
        return userDAO.getUser(userName, password);
    }
}

