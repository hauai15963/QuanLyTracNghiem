
package DAO;
import Connec.Connec;
import DTO.UsersDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersDAO {
    public UsersDTO getUser(String userName, String password) {
        String query = "SELECT userName, userPassword, isAdmin FROM users WHERE userName = ? AND userPassword = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UsersDTO(rs.getString("userName"), rs.getString("userPassword"), rs.getBoolean("isAdmin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
