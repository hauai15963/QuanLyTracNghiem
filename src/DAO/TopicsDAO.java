package DAO;

import Connec.Connec;
import DTO.TopicsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haun4
 */
public class TopicsDAO {

    public List<TopicsDTO> getAllTopics() {
        List<TopicsDTO> topics = new ArrayList<>();
        String sql = "SELECT tpID, tpTitle, tpParent, tpStatus FROM topics";

        try (Connection conn = Connec.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopicsDTO topic = new TopicsDTO(
                        rs.getInt("tpID"),
                        rs.getString("tpTitle"),
                        rs.getInt("tpParent"),
                        rs.getBoolean("tpStatus")
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
        return topics;
    }

    public boolean insertTopic(TopicsDTO topic) {
        String sql = "INSERT INTO topics (tpTitle, tpParent, tpStatus) VALUES (?, ?, ?)";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, topic.getTitle());
            stmt.setInt(2, topic.getParentId());
            stmt.setBoolean(3, topic.isStatus());

            return stmt.executeUpdate() > 0; // Trả về true nếu chèn thành công
        } catch (SQLException e) {
            System.err.println("Lỗi thêm chủ đề: " + e.getMessage());
            return false;
        }
    }

    public boolean isTopicExists(String title) {
        String sql = "SELECT COUNT(*) FROM topics WHERE tpTitle = ?";

        try (Connection conn = Connec.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất 1 kết quả
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra trùng chủ đề: " + e.getMessage());
        }
        return false;
    }
    
    public boolean isParentTopicExists(int parentId) {
    String sql = "SELECT COUNT(*) FROM topics WHERE tpID = ?";
    
    try (Connection conn = Connec.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, parentId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1) > 0; // Trả về true nếu tìm thấy ID cha
        }
    } catch (SQLException e) {
        System.err.println("Lỗi kiểm tra ID chủ đề cha: " + e.getMessage());
    }
    return false;
}
  
    
    public boolean updateTopic(TopicsDTO topic) {
    String sql = "UPDATE topics SET tpTitle = ?, tpParent = ?, tpStatus = ? WHERE tpID = ?";
    
    try (Connection conn = Connec.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, topic.getTitle());
        stmt.setInt(2, topic.getParentId());
        stmt.setBoolean(3, topic.isStatus());
        stmt.setInt(4, topic.getId());

        return stmt.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
        System.err.println("Lỗi cập nhật chủ đề: " + e.getMessage());
    }
    return false;
}

    

}
