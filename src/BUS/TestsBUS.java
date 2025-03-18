package BUS;

import DAO.TestsDAO;
import DTO.TestsDTO;
import java.util.List;

/**
 *
 * @author haun4
 */
public class TestsBUS {

    private TestsDAO testsDAO;

    public TestsBUS() {
        testsDAO = new TestsDAO();
    }

    public List<TestsDTO> getAllTests() {
        return testsDAO.getAllTests();
    }

    public List<Integer> getAllTestTimes() {
        return testsDAO.getAllTestTimes(); // Gọi từ DAO
    }

    public List<String> getAllTestTopics() {
        return testsDAO.getAllTestTopics(); // Gọi từ DAO
    }

    public boolean addTest(TestsDTO test) {
        // Kiểm tra nếu DAO đã được khởi tạo
        if (testsDAO == null) {
            System.out.println("Lỗi: DAO chưa được khởi tạo!");
            return false;
        }

        // Kiểm tra nếu mã bài kiểm tra đã tồn tại
        boolean testCodeExists = testsDAO.isTestCodeExists(test.getCode());
        if (testCodeExists) {
            System.out.println("Mã bài kiểm tra đã tồn tại!");
            return false;
        }

        // Kiểm tra nếu chủ đề có tồn tại
        boolean topicExists = testsDAO.isTopicExists(test.getTopicId());
        if (!topicExists) {
            System.out.println("Chủ đề không tồn tại!");
            return false;
        }

        // Thêm bài kiểm tra vào cơ sở dữ liệu
        return testsDAO.insertTest(test);
    }

    public boolean isTestCodeExists(String testCode) {
        return testsDAO.isTestCodeExists(testCode);
    }

    public boolean isTopicExists(int topicId) {
        return testsDAO.isTopicExists(topicId);
    }

    public boolean updateTest(TestsDTO test) {
        // Kiểm tra nếu DAO đã được khởi tạo
        if (testsDAO == null) {
            System.out.println("Lỗi: DAO chưa được khởi tạo!");
            return false;
        }
        boolean testCodeExists = testsDAO.isTestCodeExists(test.getCode());
        if (testCodeExists) {
            System.out.println("Mã bài kiểm tra đã tồn tại!");
            return false;
        }
        // Kiểm tra nếu chủ đề có tồn tại
        boolean topicExists = testsDAO.isTopicExists(test.getTopicId());
        if (!topicExists) {
            System.out.println("Chủ đề không tồn tại!");
            return false;
        }

        // Cập nhật bài kiểm tra trong cơ sở dữ liệu
        return testsDAO.updateTest(test);
    }
    // Kiểm tra bài kiểm tra có bài thi liên quan không
    public boolean hasExams(int testId) {
        return testsDAO.countExamsByTestId(testId) > 0;
    }

    // Xóa bài kiểm tra nếu không có bài thi liên quan
    public boolean deleteTest(int testId) {
        if (hasExams(testId)) {
            System.out.println("Không thể xóa! Bài kiểm tra có bài thi liên quan.");
            return false;
        }
        return testsDAO.deleteTest(testId);
    }
    // Hàm tìm kiếm bài kiểm tra
    public List<TestsDTO> searchTests(String keyword, Integer topicId, Integer time) {
        return testsDAO.searchTests(keyword, topicId, time);
    }
}
