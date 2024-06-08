import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        //Scanners
        //intro scanners
        Scanner mainMenuSelectionInput = new Scanner(System.in);
        Scanner plannerSelectionInput = new Scanner(System.in);
        //bill scanners
        Scanner billMenuSelectionInput = new Scanner(System.in);
        Scanner makeNewBillInput = new Scanner(System.in);
        Scanner newBillNameInput = new Scanner(System.in);
        Scanner newBillAmountInput = new Scanner(System.in);
        Scanner payBillInput = new Scanner(System.in);
        Scanner deleteBillInput = new Scanner(System.in);

        //task scanners
        Scanner makeNewTaskInput = new Scanner(System.in);
        Scanner newTaskNameInput = new Scanner(System.in);
        Scanner billCompletedBooleanInput = new Scanner(System.in);
        //event scanners
        Scanner makeNewEventInput = new Scanner(System.in);
        Scanner newEventNameInput = new Scanner(System.in);

        //Lists
        List<Bill> billList = new ArrayList<Bill>();
        List<Task> taskList = new ArrayList<Task>();
        List<Event> eventList = new ArrayList<Event>();

        //Program loop boolean
        Boolean running = true;


        //Program start loop
        while(running) {
            //Feature Selection
            System.out.print("Please select a menu option. [1]Bills, [2]Tasks, [3]Events, [4]Quit > ");
            String mainMenuSelection = mainMenuSelectionInput.nextLine();
            //Bills menu
            if (mainMenuSelection.equals("1")){
                Boolean billsMenuRunning = true;
                while (billsMenuRunning) {
                    System.out.print("Would you like to [1]Create, [2]View, [3]Pay, [4]Delete, or [5]Quit > ");
                    String billMenuSelection = billMenuSelectionInput.nextLine();
                    if (billMenuSelection.equals("1")) {
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
                                Bill newBill = new Bill(newBillName, newBillAmount, false);
                                //Add bill to list
                                billList.add(newBill);
                                System.out.println(billList.size());
                            } else if (makeNewBill.toLowerCase().equals("n")) {
                                anotherBill = false;
                            } else {
                                System.out.println("Invalid Selection");
                            }
                        }
                    }else if(billMenuSelection.equals("2")){
                        for (var i = 0; i < billList.size(); i++){
                            var bill = billList.get(i);
                            var paidStatus = "Paid";
                            if (!bill.getIsPaid()) {
                                paidStatus = "Unpaid";
                            }
                            System.out.println("Bill Name:" + " " + bill.getName() + "," + " " + "Amount Due:" + " " + bill.getAmountDue() + "," + " " + paidStatus);
                        }
                    }else if(billMenuSelection.equals("3")){
                        System.out.print("Which bill would you like to pay? ");
                        String payBillName = payBillInput.nextLine();
                        for (var i = 0; i < billList.size(); i++){
                            var bill = billList.get(i);
                            if (bill.getName().toLowerCase().equals(payBillName)){
                                System.out.println(bill.getName() + " " +  "Successfully Paid!");
                                bill.setIsPaid(true);
                            }

                        }
                    }else if(billMenuSelection.equals("4")){
                        System.out.print("Which bill would you like to delete? ");
                        String deleteBillName = deleteBillInput.nextLine();
                        for (var i = 0; i < billList.size(); i++) {
                            var bill = billList.get(i);
                            if (bill.getName().toLowerCase().equals(deleteBillName)){
                                System.out.println(bill.getName() + " " + "Successfully Deleted!");
                                billList.remove(bill);
                            }
                        }
                    } else if (billMenuSelection.equals("5")) {
                        billsMenuRunning = false;
                    }
                }

                //Task menu
            }else if (mainMenuSelection.equals("2")){
                //Task creation loop
                    boolean anotherTask = true;
                    while (anotherTask){
                        System.out.print("Would you like to add a new task. [Y]es or [No]? ");
                        String makeNewTask = makeNewTaskInput.nextLine();
                        if (makeNewTask.toLowerCase().equals("y")){
                            //Task creation input
                            System.out.print("Enter a name for the task you would like to add > ");
                            String newTaskName = newTaskNameInput.nextLine();
                            //Task object creation
                            Task newTask = new Task(newTaskName, false);
                            //Add task to task list
                            taskList.add(newTask);
                            System.out.println(taskList.size());
                        } else if (makeNewTask.toLowerCase().equals("n")){
                            anotherTask = false;
                        }else {
                            System.out.println("Invalid Selection");
                        }
                    }

                    //Events menu
                }else if(mainMenuSelection.equals("3")){
                //Events creation loop
                boolean anotherEvent = true;
                while (anotherEvent) {
                    //Events creation input
                    System.out.println("Would you like to add a new event. [Y]es or [N]o? ");
                    String makeNewEvent = makeNewEventInput.nextLine();
                    if (makeNewEvent.toLowerCase().equals("y")) {
                        System.out.print("What is the name of the event you want to add? ");
                        String newEventName = newEventNameInput.nextLine();
                        //Event object creation
                        Event newEvent = new Event(newEventName);
                        //Add event to event list
                        eventList.add(newEvent);
                        System.out.println(eventList.size());
                    } else if (makeNewEvent.toLowerCase().equals("n")) {
                        anotherEvent = false;
                    }else {
                        System.out.println("Invalid Selection");
                    }
                }

            }else if (mainMenuSelection.equals("4")){
                running = false;
            }else{
                System.out.println("Invalid Selection");
            }
        }
    }
}



