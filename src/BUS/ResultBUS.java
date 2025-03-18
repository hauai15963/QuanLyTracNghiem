package BUS;

import DAO.ResultDAO;
import DTO.ResultDTO;
import java.sql.Timestamp; // Sửa import cho đúng
import java.util.List;

/**
 *
 * @author haun4
 */
public class ResultBUS {
    private ResultDAO resultDAO;

    public ResultBUS() {
        resultDAO = new ResultDAO();
    }

    public List<ResultDTO> getAllResults() {
        return resultDAO.getAllResults();
    }

    public List<String> getAllExCodes() {
        return resultDAO.getAllExCodes();
    }

    public List<ResultDTO> searchResults(String keyword, String exCode, Timestamp startDate, Timestamp endDate) {
        return resultDAO.searchResults(keyword, exCode, startDate, endDate);
    }
}
