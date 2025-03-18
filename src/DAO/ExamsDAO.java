
package DAO;

import Connec.Connec;
import DTO.ExamsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamsDAO {
    public List<ExamsDTO> getAllExams() {
        List<ExamsDTO> examsList = new ArrayList<>();
        String sql = "SELECT * FROM exams";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ExamsDTO exam = new ExamsDTO(
                        rs.getString("testCode"),
                        rs.getString("exOrder").charAt(0),
                        rs.getString("exCode"),
                        rs.getString("ex_quesIDs")
                );
                examsList.add(exam);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách bài thi: " + e.getMessage());
        }
        return examsList;
    }
    // Kiểm tra testCode có tồn tại không
    public boolean isTestCodeExists(String testCode) {
        String sql = "SELECT COUNT(*) FROM test WHERE testCode = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, testCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Kiểm tra exCode có bị trùng không
    public boolean isExCodeExists(String exCode) {
        String sql = "SELECT COUNT(*) FROM exams WHERE exCode = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra danh sách câu hỏi có tồn tại không
    public boolean areQuestionsValid(String exQuesIDs) {
        String[] ids = exQuesIDs.split(",");
        String sql = "SELECT COUNT(*) FROM questions WHERE qID = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String id : ids) {
                pstmt.setInt(1, Integer.parseInt(id.trim()));
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next() || rs.getInt(1) == 0) {
                    return false; // Một câu hỏi không tồn tại
                }
            }
            return true; // Tất cả câu hỏi hợp lệ
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Chèn bài thi vào database
    public boolean insertExam(ExamsDTO exam) {
        String sql = "INSERT INTO exams (testCode, exOrder, exCode, ex_quesIDs) VALUES (?, ?, ?, ?)";
        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exam.getTestCode());
            pstmt.setString(2, Character.toString(exam.getExOrder()));
            pstmt.setString(3, exam.getExCode());
            pstmt.setString(4, exam.getExQuesIDs());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ExamsDTO getExamByTestCodeAndOrder(String testCode, char exOrder) {
    String sql = "SELECT * FROM exams WHERE testCode = ? AND exOrder = ?";
    try (Connection conn = Connec.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, testCode);
        pstmt.setString(2, Character.toString(exOrder));

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return new ExamsDTO(
                    rs.getString("testCode"),
                    rs.getString("exOrder").charAt(0),
                    rs.getString("exCode"),
                    rs.getString("ex_quesIDs")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Không tìm thấy bài thi
}

    public boolean updateExam(ExamsDTO exam) {
    String sql = "UPDATE exams SET ex_quesIDs = ? WHERE testCode = ? AND exOrder = ?";
    try (Connection conn = Connec.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, exam.getExQuesIDs());
        pstmt.setString(2, exam.getTestCode());
        pstmt.setString(3, Character.toString(exam.getExOrder()));

        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    // Kiểm tra xem exCode có tồn tại trong bảng result không
public boolean hasResultsForExCode(String exCode) {
    String sql = "SELECT COUNT(*) FROM result WHERE exCode = ?";
    try (Connection conn = Connec.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, exCode);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Có kết quả tồn tại
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// Xóa bài thi nếu không có kết quả trong bảng result
public boolean deleteExam(String testCode, char exOrder) {
    String sql = "DELETE FROM exams WHERE testCode = ? AND exOrder = ?";
    try (Connection conn = Connec.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, testCode);
        pstmt.setString(2, Character.toString(exOrder));

        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public List<ExamsDTO> searchExams(String keyword) {
    List<ExamsDTO> examList = new ArrayList<>();
    String sql = "SELECT * FROM exams WHERE testCode LIKE ? OR exCode LIKE ?";
    
    try (Connection conn = Connec.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, "%" + keyword + "%");
        pstmt.setString(2, "%" + keyword + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ExamsDTO exam = new ExamsDTO(
                    rs.getString("testCode"),
                    rs.getString("exOrder").charAt(0),
                    rs.getString("exCode"),
                    rs.getString("ex_quesIDs")
                );
                examList.add(exam);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return examList;
}

}
