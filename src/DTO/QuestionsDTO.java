
package DTO;


public class QuestionsDTO {
    private int id;
    private String content;
    private String picture;
    private int topicId;
    private String level;
    private boolean status;

    public QuestionsDTO(int id, String content, String picture, int topicId, String level, boolean status) {
        this.id = id;
        this.content = content;
        this.picture = picture;
        this.topicId = topicId;
        this.level = level;
        this.status = status;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
