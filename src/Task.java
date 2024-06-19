import com.sun.source.tree.BreakTree;

import java.util.Date;

public class Task {
    private Integer userId;
    private String title;
    private String description;
    private Date date;
    private Boolean isCompleted;

    public Task(Integer userId, String title, String description, Date date, Boolean isCompleted){
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public Integer getUserId(){
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Date getDate(){
        return date;
    }

    public Boolean getIsCompleted(){
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
