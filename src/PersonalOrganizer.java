import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PersonalOrganizer {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        try {
            login(input);
        } finally {
            input.close();
        }
    }

    public static void run(Scanner input, Integer currentUserId) throws SQLException {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
//        db.createUsersTable(conn, "users");
//        db.createBillsTable(conn, "bills");
//        db.createTasksTable(conn, "tasks");
//        db.createEventsTable(conn, "events");

        Boolean running = true;

        while (running) {
            System.out.print("Please select a menu option. [1]Bills, [2]Tasks, [3]Events, [4]Logout > ");
            String mainMenuSelection = input.nextLine();

            //Bills menu
            if (mainMenuSelection.equals("1")) {
                Boolean billsMenuRunning = true;
                while (billsMenuRunning) {
                    System.out.print("Would you like to [1]Create, [2]View, [3]Pay, [4]Delete, or [5]Quit > ");
                    String billMenuSelection = input.nextLine();
                    if (billMenuSelection.equals("1")) {
                        createBill(input, currentUserId);
                    } else if (billMenuSelection.equals("2")) {
                        System.out.print("Would you like to view [1]All, [2]Paid, or [3]Unpaid > ");
                        String viewBillSelection = input.nextLine();
                        if (viewBillSelection.equals("1")) {
                            viewBills(currentUserId);
                        }else if (viewBillSelection.equals("2")){
                            viewPaidBills(currentUserId);
                        } else if (viewBillSelection.equals("3")) {
                            viewUnpaidBills(currentUserId);
                        }else{
                            System.out.println("Invalid Selection");
                        }
                    } else if (billMenuSelection.equals("3")) {
                        System.out.print("Which bill would you like to pay? ");
                        String payBillName = input.nextLine();
                        Boolean checkBill = checkBillName(payBillName);
                        if (checkBill) {
                            payBills(payBillName, currentUserId);
                        }else{
                            System.out.println("Bill not found.");
                        }
                    } else if (billMenuSelection.equals("4")) {
                        System.out.print("Which bill would you like to delete? ");
                        String deleteBillName = input.nextLine();
                        Boolean checkBill = checkBillName(deleteBillName);
                        if (checkBill) {
                            deleteBills(deleteBillName, currentUserId);
                        }else{
                            System.out.println("Bill not found.");
                        }
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
                        createTask(input, currentUserId);
                    } else if (taskMenuSelection.equals("2")) {
                        System.out.print("Would you like to view [1]All, [2]Complete, or [3]Incomplete > ");
                        String viewTasksSelection = input.nextLine();
                        if (viewTasksSelection.equals("1")) {
                            viewTasks(currentUserId);
                        }else if (viewTasksSelection.equals("2")){
                            viewCompleteTasks(currentUserId);
                        }else if (viewTasksSelection.equals("3")){
                            viewIncompleteTasks(currentUserId);
                        }else{
                            System.out.println("Invalid Selection.");
                        }
                    } else if (taskMenuSelection.equals("3")) {
                        System.out.print("Please enter the task you would like to mark complete > ");
                        String taskName = input.nextLine();
                        Boolean checkTask = checkTaskName(taskName);
                        if (checkTask) {
                            completeTask(currentUserId, taskName);
                        }else{
                            System.out.println("Task not found.");
                        }
                    } else if (taskMenuSelection.equals("4")) {
                        System.out.print("Please enter the name of the task you would like to delete > ");
                        String taskName = input.nextLine();
                        Boolean checkTask = checkTaskName(taskName);
                        if (checkTask) {
                            deleteTask(currentUserId, taskName);
                        }else{
                            System.out.println("Task not found");
                        }
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
                        createEvent(input, currentUserId);
                    } else if (eventMenuSelection.equals("2")) {
                        viewEvents(currentUserId);
                    } else if (eventMenuSelection.equals("3")) {
                        System.out.print("Please enter the name of the event you would like to delete > ");
                        String eventName = input.nextLine();
                        Boolean checkEvent = checkEventName(eventName);
                        if (checkEvent) {
                            deleteEvent(currentUserId, eventName);
                        }else{
                            System.out.println("Event not found.");
                        }
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
    public static void login(Scanner input) throws SQLException {
        Boolean running = true;
        Boolean loggedIn = false;
        while (running) {
            System.out.print("Do you already have an account? [Y]es or [N]o [Q]uit > ");
            String userAccountCreation = input.nextLine();
            if (userAccountCreation.toLowerCase().equals("y")) {
                while (!loggedIn) {
                    DbFunctions db = new DbFunctions();
                    Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
                    System.out.print("Please enter your username > ");
                    String username = input.nextLine();
                    System.out.print("Please enter your password > ");
                    String password = input.nextLine();
                    Integer currentUserId = DbFunctions.loginUser(conn, username, password);
                    if (currentUserId != null) {
                        loggedIn = true;
                        System.out.println(currentUserId);
                        run(input, currentUserId);
                    } else {
                        System.out.println("Incorrect login information.");
                    }
                }
            } else if (userAccountCreation.toLowerCase().equals("n")) {
                Boolean accountCreated = false;
                DbFunctions db = new DbFunctions();
                Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
                while (!accountCreated) {
                    Boolean addEmail = false;
                    String email = "";
                    while (!addEmail) {
                        System.out.print("Please enter your email > ");
                        email = input.nextLine();
                        if (email.isEmpty()) {
                            System.out.println("Invalid entry.");
                        } else {
                            Boolean isNewUser = DbFunctions.checkNewUserEmail(conn, email);
                            if (isNewUser) {
                                System.out.println("Account already exists for this email.");
                            } else {
                                addEmail = true;
                            }
                        }
                    }

                    Boolean addFirstName = false;
                    String firstName = "";
                    while (!addFirstName) {
                        System.out.print("Please enter your first name > ");
                        firstName = input.nextLine();
                        if (firstName.isEmpty()) {
                            System.out.println("Invalid entry.");
                        } else {
                            addFirstName = true;
                        }
                    }

                    Boolean addLastName = false;
                    String lastName = "";
                    while (!addLastName) {
                        System.out.print("Please enter your last name > ");
                        lastName = input.nextLine();
                        if (lastName.isEmpty()) {
                            System.out.println("Invalid entry.");
                        } else {
                            addLastName = true;
                        }
                    }

                    Boolean createUsername = false;
                    String username = "";
                    while (!createUsername) {
                        System.out.print("Please enter a username > ");
                        username = input.nextLine();
                        if (username.isEmpty()) {
                            System.out.println("Invalid entry");
                        } else {

                            Boolean checkUsername = DbFunctions.checkNewUsername(conn, username);
                            if (checkUsername) {
                                System.out.println("Username already taken.");
                            } else {
                                createUsername = true;
                            }
                        }
                    }

                    while (!loggedIn) {
                        System.out.print("Please enter a password > ");
                        String passwordOne = input.nextLine();
                        System.out.print("Please confirm password > ");
                        String passwordTwo = input.nextLine();
                        if (passwordOne.equals(passwordTwo)) {
                            loggedIn = true;
                            DbFunctions.createUser(conn, email, firstName, lastName, username, passwordOne);
                            Integer currentUserId = DbFunctions.getUserId(conn, username);
                            accountCreated = true;
                            run(input, currentUserId);
                        } else {
                            System.out.println("Passwords do not match!");
                        }
                    }
                }
            } else if (userAccountCreation.toLowerCase().equals("q")) {
                running = false;
            } else {
                System.out.println("Invalid Selection.");
            }
        }
    }

    ////////////////////BILLS  METHODS////////////////////////////
    public static void createBill(Scanner input, Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        Boolean validNumber = false;
        Double newBillAmount = 0.0;
        System.out.print("Enter a name for the bill > ");
        String newBillName = input.nextLine();
        while (!validNumber) {
            System.out.print("Enter the amount due for this bill > ");
            if (input.hasNextDouble()) {
                newBillAmount = input.nextDouble();
                validNumber = true;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
        Date confirmDate = dateCheck(input,"Enter the new bill due date (yyyy-MM-dd) >");
        DbFunctions.createBill(conn, currentUserId, newBillName, newBillAmount, confirmDate, false);
    }

    public static void viewBills(Integer currentUserId){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var bills = DbFunctions.viewBills(conn, currentUserId);
        for (var i = 0; i < bills.size(); i++) {
           var bill = bills.get(i);
           System.out.println(bill.toString());
        }
    }

    public static void viewPaidBills(Integer currentUserId){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var bills = DbFunctions.viewPaidBills(conn, currentUserId);
        for (var i = 0; i < bills.size(); i++) {
            var bill = bills.get(i);
            System.out.println(bill.toString());
        }
    }

    public static void viewUnpaidBills(Integer currentUserId){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var bills = DbFunctions.viewUnpaidBills(conn, currentUserId);
        for (var i = 0; i < bills.size(); i++) {
            var bill = bills.get(i);
            System.out.println(bill.toString());
        }
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

    public static Boolean checkBillName(String billName){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        return DbFunctions.checkBill(conn, billName);
    }

    /////////////////////TASKS METHODS/////////////////////
    public static void createTask(Scanner input, Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        System.out.print("Enter a name for the task you would like to add > ");
        String newTaskName = input.nextLine();
        System.out.print("Enter a description for the task you would like to add > ");
        String newTaskDescription = input.nextLine();
        Date confirmDate = dateCheck(input,"Enter the date the task is due (yyyy-MM-dd) >");
        DbFunctions.createTask(conn, currentUserId, newTaskName, newTaskDescription, confirmDate, false);
    }

    public static void viewTasks(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var tasks = DbFunctions.viewTasks(conn, currentUserId);
        for (var i = 0; i < tasks.size(); i++) {
            var task = tasks.get(i);
            System.out.println(task.toString());
        }
    }

    public static void viewCompleteTasks(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var tasks = DbFunctions.viewCompleteTasks(conn, currentUserId);
        for (var i = 0; i < tasks.size(); i++) {
            var task = tasks.get(i);
            System.out.println(task.toString());
        }
    }

    public static void viewIncompleteTasks(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var tasks = DbFunctions.viewIncompleteTasks(conn, currentUserId);
        for (var i = 0; i < tasks.size(); i++) {
            var task = tasks.get(i);
            System.out.println(task.toString());
        }
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

    public static Boolean checkTaskName(String taskName){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        return DbFunctions.checkTask(conn, taskName);
    }

    /////////////////////EVENT METHODS///////////////////////
    public static void createEvent(Scanner input, Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        System.out.print("What is the name of the event you want to add? ");
        String newEventName = input.nextLine();
        System.out.print("Please enter a description for this event.> ");
        String newEventDescription = input.nextLine();
        Date confirmDate = dateCheck(input, "Enter the new bill due date (yyyy-MM-dd) >");
        System.out.print("Please enter a location for this event.> ");
        String newEventLocation = input.nextLine();
        DbFunctions.createEvent(conn, currentUserId,newEventName, newEventDescription, confirmDate, newEventLocation);
    }

    public static void viewEvents(Integer currentUserId) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        var events = DbFunctions.viewEvents(conn, currentUserId);
        for (var i = 0; i < events.size(); i++) {
            var event = events.get(i);
            System.out.println(event.toString());
        }
    }

    public static void deleteEvent(Integer currentUserId, String eventName) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        DbFunctions.deleteEvent(conn, eventName, currentUserId);
    }

    public static Boolean checkEventName(String eventName){
        DbFunctions db = new DbFunctions();
        Connection conn = db.connectToDb("personalorganizer", "postgres", "admin");
        return DbFunctions.checkEvent(conn, eventName);
    }

    public static Date dateCheck(Scanner input, String prompt) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Strict parsing
        while (date == null) {
            System.out.print(prompt);
            String newDate = input.next();
            try {
                date = dateFormat.parse(newDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
        }
        return date;
    }
}



