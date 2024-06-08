import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        //Scanners
        //Intro scanners
        Scanner plannerSelectionInput = new Scanner(System.in);

        //bill scanners
        Scanner makeNewBillInput = new Scanner(System.in);
        Scanner newBillNameInput = new Scanner(System.in);
        Scanner newBillAmountInput = new Scanner(System.in);
        //task scanners
        Scanner makeNewTaskInput = new Scanner(System.in);
        Scanner newTaskNameInput = new Scanner(System.in);
        Scanner billCompletedBooleanInput = new Scanner(System.in);
        //Program loop boolean
        Boolean running = true;

        //Lists
        List<Bill> billList = new ArrayList<Bill>();
        List<Task> taskList = new ArrayList<Task>();

        //Program start loop
        while(running) {
            //Feature Selection
            System.out.print("What would you like to do. Make new [B]ill [T]ask [E]vent or [Q]uit > ");
            String featureSelection = plannerSelectionInput.nextLine();
            //Check which feature is selected
            if (featureSelection.toLowerCase().equals("b")) {
                //Bill loop boolean
                Boolean anotherBill = true;
                //Bill creation loop start
                while (anotherBill) {
                    System.out.print("Would you like to make a new bill('Y' or 'N)? ");
                    String makeNewBill = makeNewBillInput.nextLine();
                    //Check if a new bill will be added
                    if (makeNewBill.toLowerCase().equals("y")) {
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
                    } else if (makeNewBill.toLowerCase().equals("n")) {
                        anotherBill = false;
                    }else {
                        System.out.println("Invalid Selection");
                    }
                }
            } else if (featureSelection.toLowerCase().equals("t")){
                //Task creation loop
                boolean anotherTask = true;
                while (anotherTask){
                    System.out.print("Would you like to add a new task. [Y]es or [No]? ");
                    String makeNewTask = makeNewTaskInput.nextLine();
                    if (makeNewTask.toLowerCase().equals("y")){
                        //Task creation input
                        System.out.print("Enter a name for the task you would like to add > ");
                        String newTaskName = newTaskNameInput.nextLine();
                        Task newTask = new Task(newTaskName, false);
                        taskList.add(newTask);
                        System.out.println(taskList.size());
                    } else if (makeNewTask.toLowerCase().equals("n")){
                        anotherTask = false;
                    }else {
                        System.out.println("Invalid Selection");
                    }
                }
            }else if (featureSelection.toLowerCase().equals("q")){
                running = false;
            }else {
                System.out.println("Invalid Selection");
            }
        }
    }
}



