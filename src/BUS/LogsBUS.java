package BUS;

import DAO.LogsDAO;
import DTO.LogsDTO;
import java.sql.Timestamp;
import java.util.List;

public class LogsBUS {
    private LogsDAO logsDAO;

    public LogsBUS() {
        logsDAO = new LogsDAO();
    }

    public List<LogsDTO> getAllLogs() {
        return logsDAO.getAllLogs();
    }

    public List<String> getAllExCodes() {
        return logsDAO.getAllExCodes();
    }

    public List<LogsDTO> searchLogs(String keyword, String exCode, Timestamp startDate, Timestamp endDate) {
        return logsDAO.searchLogs(keyword, exCode, startDate, endDate);
    }
}


