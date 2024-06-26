import com.sun.source.tree.BreakTree;

import java.sql.Timestamp;
import java.util.Date;

public class Event {
    private Integer userId;
    private String title;
    private String eventDescription;
    private Date eventDate;
    private String eventLocation;
    public Event(Integer userId, String title, String eventDescription, Date eventDate, String eventLocation) {
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
    public Date getEventDate() {
        return eventDate;
    }
    public String getEventLocation(){
        return eventLocation;
    }

    @Override
    public String toString() {
        return "NAME: " + title + ", DESCRIPTION: " + eventDescription +
                ", DATE: " + eventDate + ", LOCATION: " + eventLocation;
    }
}
