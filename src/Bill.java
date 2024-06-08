import java.util.Date;

public class Bill {
    private String name;
    private Double amountDue;
    public Bill(String name, Double amountDue)  {
        this.name = name;
        this.amountDue = amountDue;
    }

    public String getName(){
        return name;
    }

    public Double getAmountDue(){
        return amountDue;
    }
}
