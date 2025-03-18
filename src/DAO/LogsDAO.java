package DAO;

import Connec.Connec;
import DTO.LogsDTO;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class LogsDAO {
    public List<LogsDTO> getAllLogs() {
        List<LogsDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs ORDER BY logDate DESC";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LogsDTO log = new LogsDTO();
                log.setLogID(rs.getInt("logID"));
                log.setLogContent(rs.getString("logContent"));
                log.setLogUserID(rs.getInt("logUserID"));
                log.setLogExCode(rs.getString("logExCode"));
                log.setLogDate(rs.getTimestamp("logDate"));

                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public List<String> getAllExCodes() {
        List<String> exCodes = new ArrayList<>();
        String sql = "SELECT DISTINCT logExCode FROM logs";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                exCodes.add(rs.getString("logExCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exCodes;
    }

    public List<LogsDTO> searchLogs(String keyword, String exCode, Timestamp startDate, Timestamp endDate) {
        List<LogsDTO> logs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM logs WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND logContent LIKE ?");
            params.add("%" + keyword + "%");
        }

        if (exCode != null) {
            sql.append(" AND logExCode = ?");
            params.add(exCode);
        }

        if (startDate != null) {
            sql.append(" AND logDate >= ?");
            params.add(startDate);
        }

        if (endDate != null) {
            sql.append(" AND logDate <= ?");
            params.add(endDate);
        }

        sql.append(" ORDER BY logDate DESC");

        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LogsDTO log = new LogsDTO();
                    log.setLogID(rs.getInt("logID"));
                    log.setLogContent(rs.getString("logContent"));
                    log.setLogUserID(rs.getInt("logUserID"));
                    log.setLogExCode(rs.getString("logExCode"));
                    log.setLogDate(rs.getTimestamp("logDate"));

                    logs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}

