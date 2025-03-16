
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
}
