import java.util.Date;

public class Bill {
    private String name;
    private Double amountDue;
    private Boolean isPaid;
    public Bill(String name, Double amountDue, boolean isPaid)  {
        this.name = name;
        this.amountDue = amountDue;
        this.isPaid = isPaid;
    }

    public String getName(){
        return name;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public Boolean getIsPaid(){
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid){
        this.isPaid = isPaid;
    }

}
