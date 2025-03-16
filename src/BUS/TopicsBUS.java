
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

        public boolean updateTopic(TopicsDTO oldTopic, TopicsDTO newTopic) {
    // Kiểm tra nếu không có thay đổi
    if (oldTopic.equals(newTopic)) {
        return false; // Không có gì thay đổi => Không cần cập nhật
    }

    // Kiểm tra nếu tên chủ đề mới đã tồn tại và không phải của chính nó
    if (!oldTopic.getTitle().equals(newTopic.getTitle()) && topicsDAO.isTopicExists(newTopic.getTitle())) {
        return false; // Tên chủ đề đã tồn tại
    }

    // Kiểm tra nếu ID chủ đề cha không tồn tại
    if (newTopic.getParentId() > 0 && !topicsDAO.isParentTopicExists(newTopic.getParentId())) {
        return false; // ID cha không hợp lệ
    }

    return topicsDAO.updateTopic(newTopic);
}

}
