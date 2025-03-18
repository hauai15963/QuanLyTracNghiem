package BUS;

import DAO.QuestionsDAO;
import DTO.QuestionsDTO;
import java.util.List;

public class QuestionsBUS {

    private QuestionsDAO questionsDAO;

    public QuestionsBUS() {
        questionsDAO = new QuestionsDAO();
    }

    public List<QuestionsDTO> getAllQuestions() {
        return questionsDAO.getAllQuestions();
    }

    public List<String> getAllLevels() {
        return questionsDAO.getAllLevels(); // Gọi DAO để lấy danh sách độ khó
    }

    public List<Integer> getAllTopics() {
        return questionsDAO.getAllTopics(); // Gọi DAO để lấy danh sách chủ đề
    }

    public boolean addQuestion(QuestionsDTO question) {
        // Kiểm tra nếu nội dung câu hỏi đã tồn tại
        if (questionsDAO.isQuestionExists(question.getContent())) {
            return false; // Câu hỏi đã tồn tại, không thêm nữa
        }

        return questionsDAO.insertQuestion(question);
    }

    public boolean updateQuestion(QuestionsDTO newQuestion, QuestionsDTO oldQuestion) {
        // 1️⃣ Kiểm tra nếu không có thay đổi
        if (newQuestion.equals(oldQuestion)) {
            return false; // Không có thay đổi, không cập nhật
        }

        // 2️⃣ Kiểm tra nếu nội dung mới bị trùng với một câu hỏi khác
        if (!newQuestion.getContent().equals(oldQuestion.getContent()) && questionsDAO.isQuestionExists(newQuestion.getContent())) {
            return false; // Nội dung đã tồn tại
        }

        return questionsDAO.updateQuestion(newQuestion);
    }

    public boolean deleteQuestion(int questionId) {
        // 1️⃣ Kiểm tra nếu câu hỏi có câu trả lời
        if (questionsDAO.hasAnswers(questionId)) {
            return false; // Không thể xóa nếu vẫn còn câu trả lời
        }

        // 2️⃣ Xóa câu hỏi
        return questionsDAO.deleteQuestion(questionId);
    }

    public List<QuestionsDTO> searchQuestions(String keyword, String level, Integer topicId) {
        return questionsDAO.searchQuestions(keyword, level, topicId);
    }

}
