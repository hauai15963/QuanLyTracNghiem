package DTO;

import java.math.BigDecimal;
import java.sql.Timestamp; 

public class ResultDTO {
    private int rsNum;
    private int userID;
    private String exCode;
    private String rsAnswers;
    private BigDecimal rsMark;
    private Timestamp rsDate; 

    // Getter và Setter
    public int getRsNum() {
        return rsNum;
    }

    public void setRsNum(int rsNum) {
        this.rsNum = rsNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getRsAnswers() {
        return rsAnswers;
    }

    public void setRsAnswers(String rsAnswers) {
        this.rsAnswers = rsAnswers;
    }

    public BigDecimal getRsMark() {
        return rsMark;
    }

    public void setRsMark(BigDecimal rsMark) {
        this.rsMark = rsMark;
    }

    public Timestamp getRsDate() {
        return rsDate;
    }

    public void setRsDate(Timestamp rsDate) {
        this.rsDate = rsDate;
    }
}
