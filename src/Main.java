import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        //Continue making bill loop boolean
        Boolean anotherBill = true;

        //List of bills
        List<Bill> billList = new ArrayList<Bill>();

        //Scanners
        Scanner makeNewBillInput = new Scanner((System.in));
        Scanner newBillNameInput = new Scanner(System.in);
        Scanner newBillAmountInput = new Scanner(System.in);

        //Bill creation loop start
        while(anotherBill) {
            System.out.print("Would you like to make a new bill('Y' or 'N)? ");
            String makeNewBill = makeNewBillInput.nextLine();

            //Check if a new bill will be added
            if(makeNewBill.toLowerCase().equals("y")){

                //Bill creation input
                System.out.print("Enter a name for the bill that is due > ");
                String newBillName = newBillNameInput.nextLine();
                System.out.print("Enter the amount due for this bill > ");
                Double newBillAmount = newBillAmountInput.nextDouble();

                //Bill object creation
                Bill newBill = new Bill(newBillName, newBillAmount);

                //Add bill to list
                billList.add(newBill);
                System.out.println(billList.size());
            }else if(makeNewBill.toLowerCase().equals("n")){
                anotherBill = false;
            }
        }
    }
}



