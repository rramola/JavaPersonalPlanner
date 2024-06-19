import com.sun.source.tree.BreakTree;

import java.sql.Timestamp;

public class Event {
    private Integer userId;
    private String title;
    private String eventDescription;
    private Timestamp eventDate;
    private String eventLocation;
    public Event(Integer userId, String title, String eventDescription, Timestamp eventDate, String eventLocation) {
        this.userId = userId;
        this.title = title;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }

    public Integer getUserId(){
        return userId;
    }
    public String getTitle(){
        return title;
    }
    public String getEventDescription(){
        return eventDescription;
    }
    public Timestamp getEventDate() {
        return eventDate;
    }
    public String getEventLocation(){
        return eventLocation;
    }
}
