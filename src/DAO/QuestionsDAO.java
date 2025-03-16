
package DAO;

import Connec.Connec;
import DTO.QuestionsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class QuestionsDAO {
    public List<QuestionsDTO> getAllQuestions() {
        List<QuestionsDTO> questions = new ArrayList<>();
        String sql = "SELECT qID, qContent, qPictures, qTopicID, qLevel, qStatus FROM questions";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                QuestionsDTO question = new QuestionsDTO(
                        rs.getInt("qID"),
                        rs.getString("qContent"),
                        rs.getString("qPictures"),
                        rs.getInt("qTopicID"),
                        rs.getString("qLevel"),
                        rs.getBoolean("qStatus")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
        return questions;
    }
}
