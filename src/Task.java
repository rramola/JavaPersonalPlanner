public class Task {
    private String name;
    private Boolean taskCompleted;

    public Task(String name, Boolean taskCompleted){
        this.name = name;
        this.taskCompleted = taskCompleted;
    }

    public String getName() {
        return name;
    }

    public Boolean getTaskCompleted(){
        return taskCompleted;
    }

    public void setTaskCompleted(Boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }
}
