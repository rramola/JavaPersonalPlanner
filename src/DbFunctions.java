import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Date;

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
                    "    eventDate TIMESTAMP NOT NULL,\n" +
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
}
