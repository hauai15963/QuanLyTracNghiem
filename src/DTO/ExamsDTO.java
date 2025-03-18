
package DTO;

/**
 *
 * @author haun4
 */
public class ExamsDTO {
    private String testCode;
    private char exOrder;
    private String exCode;
    private String exQuesIDs;

    public ExamsDTO() {
    }

    public ExamsDTO(String testCode, char exOrder, String exCode, String exQuesIDs) {
        this.testCode = testCode;
        this.exOrder = exOrder;
        this.exCode = exCode;
        this.exQuesIDs = exQuesIDs;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public char getExOrder() {
        return exOrder;
    }

    public void setExOrder(char exOrder) {
        this.exOrder = exOrder;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getExQuesIDs() {
        return exQuesIDs;
    }

    public void setExQuesIDs(String exQuesIDs) {
        this.exQuesIDs = exQuesIDs;
    }
}
