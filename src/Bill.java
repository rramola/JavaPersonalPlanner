import java.util.Date;

public class Bill {
    private Integer userId;
    private String title;
    private Double amount;
    private Date dueDate;
    private Boolean isPaid;
    public Bill(Integer userId, String title, Double amount, Date dueDate, boolean isPaid)  {
        this.userId = userId;
        this.title = title;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
    }

    public Integer getUserId(){
        return userId;
    }
    public String getTitle(){
        return title;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getIsPaid(){
        return isPaid;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public void setIsPaid(Boolean isPaid){
        this.isPaid = isPaid;
    }

    public String toString() {
        String status = "Unpaid";
        if (isPaid) {
            status = "Paid";
        } ;
        return "NAME: " + title + ", AMOUNT DUE: " + amount +
                ", DUE DATE: " + dueDate + ", STATUS: " + status;
    }
}




