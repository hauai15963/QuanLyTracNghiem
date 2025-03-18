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

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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

    public List<String> getAllLevels() {
        List<String> levels = new ArrayList<>();
        String sql = "SELECT DISTINCT qLevel FROM questions";

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                levels.add(rs.getString("qLevel"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
        return levels;
    }

    public List<Integer> getAllTopics() {
        List<Integer> topics = new ArrayList<>();
        String sql = "SELECT DISTINCT qTopicID FROM questions";

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                topics.add(rs.getInt("qTopicID"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn chủ đề: " + e.getMessage());
        }
        return topics;
    }

    public boolean insertQuestion(QuestionsDTO question) {
        String sql = "INSERT INTO questions (qContent, qPictures, qTopicID, qLevel, qStatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question.getContent());
            stmt.setString(2, question.getPicture());
            stmt.setInt(3, question.getTopicId());
            stmt.setString(4, question.getLevel());
            stmt.setBoolean(5, question.isStatus());

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.err.println("Lỗi thêm câu hỏi: " + e.getMessage());
            return false;
        }
    }

    public boolean isQuestionExists(String content) {
        String sql = "SELECT COUNT(*) FROM questions WHERE qContent = ?";
        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, content);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Đã tồn tại
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra câu hỏi: " + e.getMessage());
        }
        return false; // Không tồn tại
    }

    public boolean updateQuestion(QuestionsDTO question) {
        String sql = "UPDATE questions SET qContent = ?, qPictures = ?, qTopicID = ?, qLevel = ?, qStatus = ? WHERE qID = ?";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, question.getContent());
            stmt.setString(2, question.getPicture());
            stmt.setInt(3, question.getTopicId());
            stmt.setString(4, question.getLevel());
            stmt.setBoolean(5, question.isStatus());
            stmt.setInt(6, question.getId());

            return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật câu hỏi: " + e.getMessage());
            return false;
        }
    }

    public boolean hasAnswers(int questionId) {
        String sql = "SELECT COUNT(*) FROM answers WHERE qID = ?";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu có câu trả lời
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra câu trả lời: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteQuestion(int questionId) {
        String sql = "DELETE FROM questions WHERE qID = ?";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            System.err.println("Lỗi xóa câu hỏi: " + e.getMessage());
            return false;
        }
    }

    public List<QuestionsDTO> searchQuestions(String keyword, String level, Integer topicId) {
        List<QuestionsDTO> questionsList = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE 1=1"; // Điều kiện luôn đúng để thêm các điều kiện sau

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND qContent LIKE ?";
        }
        if (level != null && !level.isEmpty()) {
            sql += " AND qLevel = ?";
        }
        if (topicId != null) {
            sql += " AND qTopicID = ?";
        }

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(paramIndex++, "%" + keyword + "%");
            }
            if (level != null && !level.isEmpty()) {
                stmt.setString(paramIndex++, level);
            }
            if (topicId != null) {
                stmt.setInt(paramIndex++, topicId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questionsList.add(new QuestionsDTO(
                            rs.getInt("qID"),
                            rs.getString("qContent"),
                            rs.getString("qPictures"),
                            rs.getInt("qTopicID"),
                            rs.getString("qLevel"),
                            rs.getBoolean("qStatus")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm câu hỏi: " + e.getMessage());
        }

        return questionsList;
    }

}
