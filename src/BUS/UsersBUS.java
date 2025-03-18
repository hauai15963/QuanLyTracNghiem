
package BUS;

import DAO.UsersDAO;
import DTO.UsersDTO;


public class UsersBUS {
    private UsersDAO usersDAO = new UsersDAO();
    private static UsersDTO currentUser = null; // Lưu thông tin user sau khi đăng nhập

    public UsersDTO login(String userName, String password) {
        currentUser = usersDAO.getUser(userName, password);
        return currentUser;
    }

    public static UsersDTO getCurrentUser() {
        return currentUser;
    }
}

