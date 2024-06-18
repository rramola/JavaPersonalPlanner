import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbFunctions {
    public Connection connectToDb(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pass);
            if(conn!=null){
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
            String query="create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    email TEXT NOT NULL UNIQUE,\n" +
                    "    firstName TEXT,\n" +
                    "    lastName TEXT\n" +
                    ");";
            statement  = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created.");
        }catch (Exception e){
            System.out.print(e);
        }
    }
    public void createBillsTable(Connection conn, String tableName){
        Statement statement;
        try{
            String query="create table " + tableName + "(\n" +
                    "    id BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "    userId INTEGER NOT NULL,\n" +
                    "    title TEXT NOT NULL,\n" +
                    "    amount DECIMAL(10, 2) NOT NULL,\n" +
                    "    dueDate DATE NOT NULL,\n" +
                    "    isPaid BOOLEAN DEFAULT FALSE,\n" +
                    "    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE\n" +
                    ");";
            statement  = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created.");
        }catch (Exception e){
            System.out.print(e);
        }
    }
}
