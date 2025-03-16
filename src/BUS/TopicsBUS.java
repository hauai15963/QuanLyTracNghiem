
package BUS;
import java.util.List;
import DAO.TopicsDAO;
import DTO.TopicsDTO;

/**
 *
 * @author haun4
 */
public class TopicsBUS {
    private TopicsDAO topicsDAO;

    public TopicsBUS() {
        topicsDAO = new TopicsDAO();
    }

    public List<TopicsDTO> getAllTopics() {
        return topicsDAO.getAllTopics();
    }
    
    public boolean isParentTopicExists(int parentId) {
    return topicsDAO.isParentTopicExists(parentId);
}

    public boolean addTopic(TopicsDTO topic) {
    // Kiểm tra nếu chủ đề đã tồn tại
    if (topicsDAO.isTopicExists(topic.getTitle())) {
        return false; // Chủ đề đã tồn tại, không thêm nữa
    }

    // Kiểm tra nếu ID chủ đề cha không tồn tại trong DB
    if (topic.getParentId() > 0 && !topicsDAO.isParentTopicExists(topic.getParentId())) {
        return false; // ID cha không hợp lệ
    }

    return topicsDAO.insertTopic(topic);
}

       public boolean updateTopic(TopicsDTO newTopic, TopicsDTO oldTopic) {
    // 1️⃣ Kiểm tra nếu không có thay đổi
    if (newTopic.equals(oldTopic)) {
        return false; // Không có thay đổi, không cập nhật
    }

    // 2️⃣ Kiểm tra nếu tên mới bị trùng với một chủ đề khác
    if (!newTopic.getTitle().equals(oldTopic.getTitle()) && topicsDAO.isTopicExists(newTopic.getTitle())) {
        return false; // Tên đã tồn tại
    }

    // 3️⃣ Kiểm tra nếu ID chủ đề cha không tồn tại
    if (newTopic.getParentId() > 0 && !topicsDAO.isParentTopicExists(newTopic.getParentId())) {
        return false; // ID cha không hợp lệ
    }

    return topicsDAO.updateTopic(newTopic);
}

       public boolean hasChildTopics(int parentId) {
    return topicsDAO.countChildTopics(parentId) > 0;
}
public boolean deleteTopics(int idTopic) {
    if (hasChildTopics(idTopic)) {
        return false; // Không thể xóa nếu còn topics con
    }
    return topicsDAO.deleteTopic(idTopic);
}
public List<TopicsDTO> searchTopics(String keyword) {
    return topicsDAO.searchTopics(keyword);
}


}
