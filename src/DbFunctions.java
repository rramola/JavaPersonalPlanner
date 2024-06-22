import org.postgresql.replication.fluent.physical.PhysicalReplicationOptions;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class DbFunctions {
    public Connection connectToDb(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if(conn != null){
                System.out.println("Connection Established");
            }else {
                System.out.println("Connection Failed");
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    ///////////////////TABLE METHODS/////////////////////
    public void createUsersTable(Connection conn, String tableName){
        Statement statement;
        try{
            String sql = "create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    email TEXT NOT NULL UNIQUE,\n" +
                    "    firstName TEXT,\n" +
                    "    lastName TEXT,\n" +
                    "    username TEXT,\n" +
                    "    password TEXT\n" +
                    ");";
            statement  = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Table Created.");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void createBillsTable(Connection conn, String tableName){
        Statement statement;
        try{
            String sql = "create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    userId INTEGER NOT NULL,\n" +
                    "    title TEXT NOT NULL,\n" +
                    "    amount DECIMAL(10, 2) NOT NULL,\n" +
                    "    dueDate DATE NOT NULL,\n" +
                    "    isPaid BOOLEAN DEFAULT FALSE,\n" +
                    "    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE\n" +
                    ");";
            statement  = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Table Created.");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTasksTable(Connection conn, String tableName){
        Statement statement;
        try{
            String sql = "create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    userId INTEGER NOT NULL,\n" +
                    "    title TEXT NOT NULL,\n" +
                    "    todoDescription TEXT,\n" +
                    "    dueDate DATE NOT NULL,\n" +
                    "    isCompleted BOOLEAN DEFAULT FALSE,\n" +
                    "    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE\n" +
                    ");";
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createEventsTable(Connection conn, String tableName){
        Statement statement;
        try{
            String sql = "create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    userId INTEGER NOT NULL,\n" +
                    "    title TEXT NOT NULL,\n" +
                    "    eventDescription TEXT,\n" +
                    "    eventDate DATE NOT NULL,\n" +
                    "    eventLocation TEXT,\n" +
                    "    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE\n" +
                    ");";
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void createUser(Connection conn, String email, String firstName, String lastName, String username, String password){
        try{
            String sql = "insert into users(email,firstName,lastName,username,password) "
                    + "Values(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            System.out.println("User created!");
        }catch (Exception e){
            System.out.print(e);
        }
    }

    //////////////////////USER METHODS////////////////////
    public static Boolean checkNewUserEmail(Connection conn, String email){
        String sql = "SELECT id FROM users WHERE email = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return  true;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public static Boolean checkNewUsername(Connection conn, String username){
        String sql = "SELECT id FROM users WHERE username = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch (Exception e){
            System.out.print(e);
        }
        return false;
    }

    public static Integer loginUser(Connection conn, String username, String password){
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static Integer getUserId(Connection conn, String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    ////////////////BILLS METHODS///////////////////
    public static void createBill(Connection conn, Integer userId, String title, Double amount, Date dueDate, Boolean isPaid ){
        String sql = "insert into bills(userId,title,amount,dueDate,isPaid) "
                + "Values(?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setDouble(3,amount);
            pstmt.setObject(4, dueDate, java.sql.Types.DATE);
            pstmt.setBoolean(5, isPaid);
            pstmt.executeUpdate();
            System.out.println("Bill Created Successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static ArrayList<Bill> viewBills(Connection conn, Integer userId){
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.amount, b.dueDate, b.isPaid\n" +
                "FROM bills b\n" +
                "JOIN users u ON b.userId = u.id\n" +
                "WHERE u.id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                Double amount = resultSet.getDouble("amount");
                Date dueDate = resultSet.getDate("dueDate");
                Boolean isPaid = resultSet.getBoolean("isPaid");
                bills.add( new Bill(id, title, amount, dueDate, isPaid));
            }
        }catch (Exception e){
            System.out.print(e);
        }
        return bills;
    }
    public static void payBill(Connection conn, String payBillName, Integer userId){
        String sql = "UPDATE bills b " +
                "SET isPaid = ? " +
                "WHERE b.title = ? " +
                "AND b.userId = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1,true);
            pstmt.setString(2,payBillName);
            pstmt.setInt(3,userId);
            pstmt.executeUpdate();
            System.out.println("Bill Paid Successfully");
        }catch (Exception e){
            System.out.print(e);
        }
    }

    public static void deleteBill(Connection conn, String payBillName, Integer userId){
        String sql = "DELETE FROM bills b " +
                "WHERE b.title = ? " +
                "AND b.userId = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,payBillName);
            pstmt.setInt(2,userId);
            pstmt.executeUpdate();
            System.out.println("Bill Deleted Successfully");
        }catch (Exception e){
            System.out.print(e);
        }
    }
    public static void createTask(Connection conn, Integer userId, String title, String description, Date date, Boolean isCompleted) {
        String sql = "insert into tasks(userId,title,todoDescription, dueDate,isCompleted) "
                + "VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setObject(4, date, java.sql.Types.DATE);
            pstmt.setBoolean(5, isCompleted);
            pstmt.executeUpdate();
            System.out.println("Task Created Successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void viewTasks(Connection conn, Integer userId) {
        String sql = "SELECT t.id, t.title, t.todoDescription, t.dueDate, t.isCompleted\n" +
                "FROM tasks t\n" +
                "JOIN users u ON t.userId = u.id\n" +
                "WHERE u.id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String status = "Completed";
                if (!resultSet.getBoolean("isCompleted")) {
                    status = "Not Completed";
                }
                String taskInfo = "TASK ID: " + resultSet.getInt("id") +
                        " NAME: " + resultSet.getString("title") +
                        " DESCRIPTION: " + resultSet.getString("todoDescription") +
                        " DUE DATE: " + resultSet.getDate("dueDate") +
                        " STATUS: " + status;
                System.out.println(taskInfo);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public static void completeTask(Connection conn, Integer userId, String taskNAme) {
        String sql = "UPDATE tasks t " +
                "SET isCompleted = ? " +
                "WHERE t.title = ? " +
                "AND t.userId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, taskNAme);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
            System.out.println("Task Completed");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public static void deleteTask(Connection conn, String taskName, Integer userId){
        String sql = "DELETE FROM tasks t " +
                "WHERE t.title = ? " +
                "AND t.userId = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,taskName);
            pstmt.setInt(2,userId);
            pstmt.executeUpdate();
            System.out.println("Task Deleted Successfully");
        }catch (Exception e){
            System.out.print(e);
        }
    }

    public static void createEvent(Connection conn, Integer userId, String title, String eventDescription, Date eventDate, String eventLocation) {
        String sql = "insert into events(userId,title,eventDescription, eventDate, eventLocation) "
                + "VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, eventDescription);
            pstmt.setObject(4, eventDate, java.sql.Types.DATE);
            pstmt.setString(5, eventLocation);
            pstmt.executeUpdate();
            System.out.println("Event Created Successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void viewEvents(Connection conn, Integer userId) {
        String sql = "SELECT e.id, e.title, e.eventDescription, e.eventDate, e.eventLocation\n" +
                "FROM events e\n" +
                "JOIN users u ON e.userId = u.id\n" +
                "WHERE u.id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String taskInfo = "EVENT ID: " + resultSet.getInt("id") +
                        " NAME: " + resultSet.getString("title") +
                        " DESCRIPTION: " + resultSet.getString("eventDescription") +
                        " EVENT DATE: " + resultSet.getDate("eventDate") +
                        " EVENT LOCATION: " + resultSet.getString("eventLocation") ;
                System.out.println(taskInfo);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public static void deleteEvent(Connection conn, String eventName, Integer userId){
        String sql = "DELETE FROM events e " +
                "WHERE e.title = ? " +
                "AND e.userId = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,eventName);
            pstmt.setInt(2,userId);
            pstmt.executeUpdate();
            System.out.println("Event Deleted Successfully");
        }catch (Exception e){
            System.out.print(e);
        }
    }
}
