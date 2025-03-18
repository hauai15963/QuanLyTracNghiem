package DAO;

import Connec.Connec;
import DTO.QuestionsDTO;
import DTO.TestsDTO;
import DTO.TopicsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haun4
 */
public class TestsDAO {

    public List<TestsDTO> getAllTests() {
        List<TestsDTO> tests = new ArrayList<>();
        String sql = "SELECT testID, testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus FROM test";

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Lấy testDate dưới dạng java.sql.Date
                Date testDate = rs.getDate("testDate");

                TestsDTO test = new TestsDTO(
                        rs.getInt("testID"),
                        rs.getString("testCode"),
                        rs.getString("testTilte"),
                        rs.getInt("testTime"),
                        rs.getInt("tpID"),
                        rs.getInt("num_easy"),
                        rs.getInt("num_medium"),
                        rs.getInt("num_diff"),
                        rs.getInt("testLimit"),
                        testDate, // Dữ liệu ngày tháng đã đúng
                        rs.getInt("testStatus")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn bài kiểm tra: " + e.getMessage());
        }
        return tests;
    }

    public List<Integer> getAllTestTimes() {
        List<Integer> times = new ArrayList<>();
        String sql = "SELECT DISTINCT testTime FROM test ORDER BY testTime ASC"; // Lấy thời gian duy nhất

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                times.add(rs.getInt("testTime")); // Thêm thời gian vào danh sách
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn thời gian làm bài: " + e.getMessage());
        }
        return times;
    }

    public List<String> getAllTestTopics() {
        List<String> topics = new ArrayList<>();
        String sql = "SELECT DISTINCT tpID FROM test ORDER BY tpID ASC"; // Lấy chủ đề duy nhất

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                topics.add(rs.getString("tpID")); // Thêm chủ đề vào danh sách
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn chủ đề: " + e.getMessage());
        }
        return topics;
    }

    public boolean isTestCodeExists(String testCode) {
        String sql = "SELECT COUNT(*) FROM test WHERE testCode = ?";
        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, testCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0 thì đã tồn tại
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra mã bài kiểm tra: " + e.getMessage());
        }
        return false;
    }

    public boolean isTopicExists(int topicId) {
        String sql = "SELECT COUNT(*) FROM topics WHERE tpID = ?";
        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0 thì chủ đề tồn tại
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra chủ đề: " + e.getMessage());
        }
        return false;
    }

    public boolean insertTest(TestsDTO test) {
        String sql = "INSERT INTO test (testCode, testTilte, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, test.getCode());
            stmt.setString(2, test.getTitle());
            stmt.setInt(3, test.getTime());
            stmt.setInt(4, test.getTopicId());
            stmt.setInt(5, test.getNumEasy());
            stmt.setInt(6, test.getNumMedium());
            stmt.setInt(7, test.getNumDiff());
            stmt.setInt(8, test.getLimit());
            stmt.setDate(9, new java.sql.Date(test.getDate().getTime())); // Chuyển từ java.util.Date sang java.sql.Date
            stmt.setInt(10, test.getStatus());

            return stmt.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.err.println("Lỗi thêm bài kiểm tra: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTest(TestsDTO test) {
        String sql = "UPDATE test SET testTilte = ?, testTime = ?, tpID = ?, num_easy = ?, num_medium = ?, num_diff = ?, testLimit = ?, testDate = ?, testStatus = ? WHERE testCode = ?";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, test.getTitle());
            stmt.setInt(2, test.getTime());
            stmt.setInt(3, test.getTopicId());
            stmt.setInt(4, test.getNumEasy());
            stmt.setInt(5, test.getNumMedium());
            stmt.setInt(6, test.getNumDiff());
            stmt.setInt(7, test.getLimit());
            stmt.setDate(8, new java.sql.Date(test.getDate().getTime())); // Chuyển từ java.util.Date sang java.sql.Date
            stmt.setInt(9, test.getStatus());
            stmt.setString(10, test.getCode());

            return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật bài kiểm tra: " + e.getMessage());
        }
        return false;
    }
// Kiểm tra số lượng bài thi liên quan đến bài kiểm tra
    public int countExamsByTestId(int testId) {
        String sql = "SELECT COUNT(*) FROM exams WHERE testID = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy số lượng bài thi
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra số lượng bài thi: " + e.getMessage());
        }
        return -1; // Lỗi xảy ra
    }
    // Xóa bài kiểm tra
    public boolean deleteTest(int testId) {
        String sql = "DELETE FROM test WHERE testID = ?";
        try (Connection conn = Connec.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testId);
            return stmt.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa bài kiểm tra: " + e.getMessage());
        }
        return false;
    }
    // Tìm kiếm bài kiểm tra
    public List<TestsDTO> searchTests(String keyword, Integer tpID, Integer testTime) {
        List<TestsDTO> tests = new ArrayList<>();
        String sql = "SELECT * FROM test WHERE 1=1";

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (testCode LIKE ? OR testTilte LIKE ?)";
        }
        if (tpID != null) {
            sql += " AND tpID = ?";
        }
        if (testTime != null) {
            sql += " AND testTime = ?";
        }

        try (Connection conn = Connec.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(index++, "%" + keyword + "%");
                stmt.setString(index++, "%" + keyword + "%");
            }
            if (tpID != null) {
                stmt.setInt(index++, tpID);
            }
            if (testTime != null) {
                stmt.setInt(index++, testTime);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tests.add(new TestsDTO(
                        rs.getInt("testID"),
                        rs.getString("testCode"),
                        rs.getString("testTilte"),
                        rs.getInt("testTime"),
                        rs.getInt("tpID"),
                        rs.getInt("num_easy"),
                        rs.getInt("num_medium"),
                        rs.getInt("num_diff"),
                        rs.getInt("testLimit"),
                        rs.getDate("testDate"),
                        rs.getInt("testStatus")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm bài kiểm tra: " + e.getMessage());
        }
        return tests;
    }
}
