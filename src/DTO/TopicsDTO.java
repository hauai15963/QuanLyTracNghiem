package DTO;

/**
 *
 * @author haun4
 */
public class TopicsDTO {

    private int id;
    private String title;
    private int parentId;
    private boolean status;

    public TopicsDTO(int id, String title, int parentId, boolean status) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
        this.status = status;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
