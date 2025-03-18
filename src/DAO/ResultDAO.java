package DAO;

import Connec.Connec;
import DTO.ResultDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    public List<ResultDTO> getAllResults() {
        List<ResultDTO> results = new ArrayList<>();
        String sql = "SELECT * FROM result ORDER BY rs_date DESC";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ResultDTO result = new ResultDTO();
                result.setRsNum(rs.getInt("rs_num"));
                result.setUserID(rs.getInt("userID"));
                result.setExCode(rs.getString("exCode"));
                result.setRsAnswers(rs.getString("rs_anwsers"));
                result.setRsMark(rs.getBigDecimal("rs_mark"));
                result.setRsDate(rs.getTimestamp("rs_date")); // ✅ Sửa lỗi import Timestamp

                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<String> getAllExCodes() {
        List<String> exCodes = new ArrayList<>();
        String sql = "SELECT DISTINCT exCode FROM result";

        try (Connection conn = Connec.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                exCodes.add(rs.getString("exCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exCodes;
    }

    public List<ResultDTO> searchResults(String keyword, String exCode, Timestamp startDate, Timestamp endDate) {
        List<ResultDTO> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM result WHERE 1=1");

        // Thêm điều kiện tìm kiếm nếu có
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (CAST(rs_num AS VARCHAR) LIKE ? OR CAST(userID AS VARCHAR) LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        if (exCode != null && !exCode.isEmpty()) {
            sql.append(" AND exCode = ?");
            params.add(exCode);
        }

        if (startDate != null) {
            sql.append(" AND rs_date >= ?");
            params.add(startDate);
        }

        if (endDate != null) {
            sql.append(" AND rs_date <= ?");
            params.add(endDate);
        }

        sql.append(" ORDER BY rs_date DESC"); // Sắp xếp theo ngày giảm dần

        try (Connection conn = Connec.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Timestamp) {
                    pstmt.setTimestamp(i + 1, (Timestamp) params.get(i));
                } else {
                    pstmt.setString(i + 1, params.get(i).toString());
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ResultDTO result = new ResultDTO();
                    result.setRsNum(rs.getInt("rs_num"));
                    result.setUserID(rs.getInt("userID"));
                    result.setExCode(rs.getString("exCode"));
                    result.setRsAnswers(rs.getString("rs_anwsers"));
                    result.setRsMark(rs.getBigDecimal("rs_mark"));
                    result.setRsDate(rs.getTimestamp("rs_date")); // ✅ Sửa lỗi import Timestamp

                    results.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
