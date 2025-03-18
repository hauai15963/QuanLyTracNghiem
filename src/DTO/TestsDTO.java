
package DTO;

import java.util.Date;

/**
 *
 * @author haun4
 */
public class TestsDTO {
    private int id;
    private String code;
    private String title;
    private int time;
    private int topicId;
    private int numEasy;
    private int numMedium;
    private int numDiff;
    private int limit;
    private Date date;
    private int status;

    public TestsDTO(int id, String code, String title, int time, int topicId, int numEasy, int numMedium, int numDiff, int limit, Date date, int status) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.time = time;
        this.topicId = topicId;
        this.numEasy = numEasy;
        this.numMedium = numMedium;
        this.numDiff = numDiff;
        this.limit = limit;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }

    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public int getNumEasy() { return numEasy; }
    public void setNumEasy(int numEasy) { this.numEasy = numEasy; }

    public int getNumMedium() { return numMedium; }
    public void setNumMedium(int numMedium) { this.numMedium = numMedium; }

    public int getNumDiff() { return numDiff; }
    public void setNumDiff(int numDiff) { this.numDiff = numDiff; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
