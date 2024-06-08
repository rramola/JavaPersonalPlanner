import java.util.Date;

public class Bill {
    private String name;
    private Double amountDue;
    private Date date;
    public Bill(String name, Double amountDue, Date date)  {
        this.name = name;
        this.amountDue = amountDue;
        this.date = date;
    }
}
