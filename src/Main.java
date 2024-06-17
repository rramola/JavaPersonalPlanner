import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        List<Bill> billList = new ArrayList<Bill>();
        List<Task> taskList = new ArrayList<Task>();
        List<Event> eventList = new ArrayList<Event>();

        Boolean running = true;
        while (running) {
            System.out.print("Please select a menu option. [1]Bills, [2]Tasks, [3]Events, [4]Quit > ");
            String mainMenuSelection = input.nextLine();

            //Bills menu
            if (mainMenuSelection.equals("1")) {
                Boolean billsMenuRunning = true;
                while (billsMenuRunning) {
                    System.out.print("Would you like to [1]Create, [2]View, [3]Pay, [4]Delete, or [5]Quit > ");
                    String billMenuSelection = input.nextLine();
                    if (billMenuSelection.equals("1")) {
                        Bill newBill = createBill();
                        billList.add(newBill);
                    } else if (billMenuSelection.equals("2")) {
                        viewBills(billList);
                    } else if (billMenuSelection.equals("3")) {
                        System.out.print("Which bill would you like to pay? ");
                        String payBillName = input.nextLine();
                        payBills(billList, payBillName);
                    } else if (billMenuSelection.equals("4")) {
                        System.out.print("Which bill would you like to delete? ");
                        String deleteBillName = input.nextLine();
                        deleteBills(billList, deleteBillName);
                    } else if (billMenuSelection.equals("5")) {
                        billsMenuRunning = false;
                    } else {
                        System.out.println(("Invalid Selection"));
                    }
                }

                //Task menu
            } else if (mainMenuSelection.equals("2")) {
                boolean taskMenu = true;
                while (taskMenu) {
                    System.out.print("Would you like to [1]Create, [2]View, [3]Complete, [4]Delete, [5]Quit > ");
                    String taskMenuSelection = input.nextLine();
                    if (taskMenuSelection.equals("1")) {
                        Task newTask = createTask();
                        taskList.add(newTask);
                    } else if (taskMenuSelection.equals("2")) {
                        viewTasks(taskList);

                    } else if (taskMenuSelection.equals("3")) {
                        System.out.print("Please enter the task you would like to mark complete > ");
                        String taskName = input.nextLine();
                        completeTask(taskList, taskName);
                    } else if (taskMenuSelection.equals("4")) {
                        System.out.print("Please enter the name of the task you would like to delete > ");
                        String taskName = input.nextLine();
                        deleteTask(taskList, taskName);
                    } else if (taskMenuSelection.equals("5")) {
                        taskMenu = false;
                    } else {
                        System.out.println("Invalid Selection");
                    }
                }

                //Events menu
            } else if (mainMenuSelection.equals("3")) {
                boolean eventMenu = true;
                while (eventMenu) {
                    System.out.println("Would you like to [1]Add, [2]View, [3]Modify, [4]Delete, [5]Quit > ");
                    String eventMenuSelection = input.nextLine();
                    if (eventMenuSelection.equals("1")) {
                        Event newEvent = createEvent();
                        eventList.add(newEvent);
                    } else if (eventMenuSelection.equals("2")) {
                        viewEvents(eventList);
                    } else if (eventMenuSelection.equals("4")) {
                        System.out.print("Please enter the name of the event you would like to delete");
                        String eventName = input.nextLine();
                        deleteEvent(eventList, eventName);
                    } else if (eventMenuSelection.equals("5")) {
                        eventMenu = false;
                    } else {
                        System.out.println("Invalid Selection");
                    }
                }
            } else if (mainMenuSelection.equals("4")) {
                running = false;
            } else {
                System.out.println("Invalid Selection");
            }
        }
    }

    public static Bill createBill() {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Enter a name for the bill > ");
        String newBillName = input.nextLine();
        System.out.print("Enter the amount due for this bill > ");
        Double newBillAmount = input.nextDouble();
        Date confirmDate = dateCheck("Enter the new bill due date (yyyy-MM-dd) >");
        return new Bill(1, newBillName, newBillAmount, confirmDate, false);
    }

    public static void viewBills(List<Bill> billList) {
        for (var i = 0; i < billList.size(); i++) {
            var bill = billList.get(i);
            var paidStatus = "Paid";
            if (!bill.getIsPaid()) {
                paidStatus = "Unpaid";
            }
            System.out.println("Bill Name:" + " " + bill.getTitle() + "," + " " + "Amount Due:" + " " + bill.getAmount() + "," + " " + paidStatus);
        }
    }

    public static void payBills(List<Bill> billList, String payBillName) {
        for (var i = 0; i < billList.size(); i++) {
            var bill = billList.get(i);
            if (bill.getTitle().toLowerCase().equals(payBillName)) {
                System.out.println(bill.getTitle() + " " + "Successfully Paid!");
                bill.setIsPaid(true);
            }
        }
    }

    public static void deleteBills(List<Bill> billList, String deleteBillName) {
        for (var i = 0; i < billList.size(); i++) {
            var bill = billList.get(i);
            if (bill.getTitle().toLowerCase().equals(deleteBillName)) {
                System.out.println(bill.getTitle() + " " + "Successfully Deleted!");
                billList.remove(bill);
            }
        }
    }

    public static Task createTask() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a name for the task you would like to add > ");
        String newTaskName = input.nextLine();
        Task newTask = new Task(newTaskName, false);
        return newTask;
    }

    public static void viewTasks(List<Task> taskList) {
        for (var i = 0; i < taskList.size(); i++) {
            var task = taskList.get(i);
            var status = "Incomplete";
            if (task.getTaskCompleted()) {
                status = "Completed";
            }
            System.out.println("Task:" + " " + task.getName() + " - " + "Status:" + " " + status);
        }
    }

    public static void completeTask(List<Task> taskList, String taskName) {
        for (var i = 0; i < taskList.size(); i++) {
            var task = taskList.get(i);
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())) {
                task.setTaskCompleted(true);
                System.out.println("Task marked complete!");
            }
        }
    }

    public static void deleteTask(List<Task> taskList, String taskName) {
        for (var i = 0; i < taskList.size(); i++) {
            var task = taskList.get(i);
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())) {
                taskList.remove(task);
                System.out.print("Task successfully deleted!");
            }
        }
    }

    public static Event createEvent() {
        Scanner input = new Scanner(System.in);
        System.out.print("What is the name of the event you want to add? ");
        String newEventName = input.nextLine();
        Event newEvent = new Event(newEventName);
        return newEvent;
    }

    public static void viewEvents(List<Event> eventList) {
        for (var i = 0; i < eventList.size(); i++) {
            var event = eventList.get(i);
            System.out.println("Task:" + event.getName());
        }
    }

    public static void deleteEvent(List<Event> eventList, String eventName) {
        for (var i = 0; i < eventList.size(); i++) {
            var event = eventList.get(i);
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())) {
                eventList.remove(event);
            }
        }
    }

    public static Date dateCheck(String prompt) {
        Scanner input = new Scanner(System.in);
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format as needed
        while ( date == null) {
            System.out.print(prompt);
            String newBillDueDate = input.nextLine();
            try {
                date = dateFormat.parse(newBillDueDate);

            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
        }
        return date;

    }

}



