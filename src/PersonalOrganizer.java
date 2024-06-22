import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class PersonalOrganizer {
    public static void main(String[] args) {
        checkAccount();
    }

    public static void run(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        db.createUsersTable(conn, "users");
        db.createBillsTable(conn, "bills");
        db.createTasksTable(conn, "tasks");
        db.createEventsTable(conn, "events");

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
                        createBill(currentUserId);
                    } else if (billMenuSelection.equals("2")) {
                        viewBills(currentUserId);
                    } else if (billMenuSelection.equals("3")) {
                        System.out.print("Which bill would you like to pay? ");
                        String payBillName = input.nextLine();
                        payBills(payBillName, currentUserId);
                    } else if (billMenuSelection.equals("4")) {
                        System.out.print("Which bill would you like to delete? ");
                        String deleteBillName = input.nextLine();
                        deleteBills(deleteBillName, currentUserId);
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
                        createTask(currentUserId);
                    } else if (taskMenuSelection.equals("2")) {
                        viewTasks(currentUserId);
                    } else if (taskMenuSelection.equals("3")) {
                        System.out.print("Please enter the task you would like to mark complete > ");
                        String taskName = input.nextLine();
                        completeTask(currentUserId, taskName);
                    } else if (taskMenuSelection.equals("4")) {
                        System.out.print("Please enter the name of the task you would like to delete > ");
                        String taskName = input.nextLine();
                        deleteTask(currentUserId, taskName);
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
                    System.out.println("Would you like to [1]Add, [2]View,[3]Delete, [4]Quit > ");
                    String eventMenuSelection = input.nextLine();
                    if (eventMenuSelection.equals("1")) {
                        createEvent(currentUserId);
                    } else if (eventMenuSelection.equals("2")) {
                        viewEvents(currentUserId);
                    } else if (eventMenuSelection.equals("3")) {
                        System.out.print("Please enter the name of the event you would like to delete > ");
                        String eventName = input.nextLine();
                        deleteEvent(currentUserId, eventName);
                    } else if (eventMenuSelection.equals("4")) {
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


    //////////////////////////USER METHODS////////////////////////////
    public static void checkAccount() {
        Boolean loggedIn = false;
        Scanner input = new Scanner(System.in);
        System.out.print("Do you already have an account? [Y]es or [N]o > ");
        String userAccountCreation = input.nextLine();
        if (userAccountCreation.toLowerCase().equals("y")) {
            while (!loggedIn) {
                DbFunctions db = new DbFunctions();
                Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
                System.out.print("Please enter your username > ");
                String user = input.nextLine();
                System.out.print("Please enter your password > ");
                String password = input.nextLine();
                Integer currentUserId = DbFunctions.loginUser(conn, user, password);
                if (currentUserId != null){
                    loggedIn = true;
                    System.out.println(currentUserId);
                    run(currentUserId);
                }else{
                    System.out.print("Incorrect login information.");
                }
            }
        } else if (userAccountCreation.toLowerCase().equals("n")) {
            DbFunctions db = new DbFunctions();
            Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
            System.out.print("Please enter your email > ");
            String email = input.nextLine();
            Boolean isNewUser = DbFunctions.checkUsers(conn, email);
            if (!isNewUser) {
                System.out.print("Please enter your first name > ");
                String firstName = input.nextLine();
                System.out.print("Please enter your last name > ");
                String lastName = input.nextLine();
                System.out.print("Please enter a username > ");
                String username = input.nextLine();
                while (!loggedIn) {
                    System.out.print("Please enter a password > ");
                    String passwordOne = input.nextLine();
                    System.out.print("Please confirm password > ");
                    String passwordTwo = input.nextLine();
                    if (passwordOne.equals(passwordTwo)) {
                        loggedIn = true;
                        DbFunctions.createUser(conn, email, firstName, lastName, username, passwordOne);
                        Integer currentUserId = DbFunctions.getUserId(conn, username);
                        run(currentUserId);
                    }else {
                    System.out.println("Passwords do not match!");
                    }
                }
            }else{
                System.out.println("Account already exists for this email.");
                checkAccount();
            }
        }else{
            System.out.println("Invalid Selection.");
            checkAccount();
        }
    }

    ////////////////////BILLS  METHODS////////////////////////////
    public static void createBill(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        Scanner input = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Enter a name for the bill > ");
        String newBillName = input.nextLine();
        System.out.print("Enter the amount due for this bill > ");
        Double newBillAmount = input.nextDouble();
        Date confirmDate = dateCheck("Enter the new bill due date (yyyy-MM-dd) >");
        DbFunctions.createBill(conn, currentUserId, newBillName, newBillAmount, confirmDate, false);
    }

    public static void viewBills(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.viewBills(conn, currentUserId);
    }

    public static void payBills(String payBillName, Integer currentUserID) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.payBill(conn, payBillName, currentUserID);
    }

    public static void deleteBills(String deleteBillName, Integer currentUserID) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.deleteBill(conn, deleteBillName, currentUserID);
    }


    /////////////////////TASKS METHODS/////////////////////
    public static void createTask(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a name for the task you would like to add > ");
        String newTaskName = input.nextLine();
        System.out.print("Enter a description for the task you would like to add > ");
        String newTaskDescription = input.nextLine();
        Date confirmDate = dateCheck("Enter the date the task is due (yyyy-MM-dd) >");
        DbFunctions.createTask(conn, currentUserId, newTaskName, newTaskDescription, confirmDate, false);
    }

    public static void viewTasks(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.viewTasks(conn, currentUserId);
    }

    public static void completeTask(Integer currentUserId, String taskName) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.completeTask(conn, currentUserId, taskName);
    }

    public static void deleteTask(Integer currentUserId, String taskName) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.deleteTask(conn, taskName, currentUserId);
    }


    /////////////////////EVENT METHODS///////////////////////
    public static void createEvent(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        Scanner input = new Scanner(System.in);
        System.out.print("What is the name of the event you want to add? ");
        String newEventName = input.nextLine();
        System.out.print("Please enter a description for this event.> ");
        String newEventDescription = input.nextLine();
        Date confirmDate = dateCheck("Enter the new bill due date (yyyy-MM-dd) >");
        System.out.print("Please enter a location for this event.> ");
        String newEventLocation = input.nextLine();
        DbFunctions.createEvent(conn, currentUserId,newEventName, newEventDescription, confirmDate, newEventLocation);
    }

    public static void viewEvents(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.viewEvents(conn, currentUserId);
    }

    public static void deleteEvent(Integer currentUserId, String eventName) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.deleteEvent(conn, eventName, currentUserId);
    }

    public static Date dateCheck(String prompt) {
        Scanner input = new Scanner(System.in);
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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



