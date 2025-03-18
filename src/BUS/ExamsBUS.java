
package BUS;

import DAO.ExamsDAO;
import DTO.ExamsDTO;
import java.util.List;

/**
 *
 * @author haun4
 */
public class ExamsBUS {
    private ExamsDAO examsDAO = new ExamsDAO();

    public List<ExamsDTO> getExamsList() {
        return examsDAO.getAllExams();
    }
    
    // Kiểm tra testCode có tồn tại không
    public boolean isTestCodeExists(String testCode) {
        return examsDAO.isTestCodeExists(testCode);
    }

    // Kiểm tra exCode có bị trùng không
    public boolean isExCodeExists(String exCode) {
        return examsDAO.isExCodeExists(exCode);
    }

    // Kiểm tra danh sách câu hỏi có hợp lệ không
    public boolean areQuestionsValid(String exQuesIDs) {
        return examsDAO.areQuestionsValid(exQuesIDs);
    }

    // Thêm bài thi mới
    public boolean addExam(ExamsDTO exam) {
        return examsDAO.insertExam(exam);
    }
    public boolean updateExam(ExamsDTO exam) {
    return examsDAO.updateExam(exam);
}
public ExamsDTO getExamByTestCodeAndOrder(String testCode, char exOrder) {
    return examsDAO.getExamByTestCodeAndOrder(testCode, exOrder);
}
public boolean hasResultsForExCode(String exCode) {
    return examsDAO.hasResultsForExCode(exCode);
}

public boolean deleteExam(String testCode, char exOrder) {
    return examsDAO.deleteExam(testCode, exOrder);
}
public List<ExamsDTO> searchExams(String keyword) {
    return examsDAO.searchExams(keyword);
}

}
