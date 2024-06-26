import com.sun.source.tree.BreakTree;

import java.util.Date;

public class Task {
    private Integer userId;
    private String title;
    private String todoDescription;
    private Date dueDate;
    private Boolean isCompleted;

    public Task(Integer userId, String title, String todoDescription, Date dueDate, Boolean isCompleted){
        this.userId = userId;
        this.title = title;
        this.todoDescription = todoDescription;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public Integer getUserId(){
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String todoDescription(){
        return todoDescription;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public Boolean getIsCompleted(){
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        String status = "Incomplete";
        if (isCompleted) {
            status = "Completed";
        } ;
        return "NAME: " + title + ", DESCRIPTION: " + todoDescription +
                ", DATE: " + dueDate + ", STATUS: " + status;
    }
}
