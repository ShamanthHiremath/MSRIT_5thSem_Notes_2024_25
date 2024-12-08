package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTableExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/shoo"; // Change to your database name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statement to create a new table
        String sql = "CREATE TABLE employee (" +
                     "empid INT PRIMARY KEY," +
                     "empname VARCHAR(100)," +
                     "salary DECIMAL(10, 2)" +
                     ")";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL statement
            stmt.executeUpdate(sql);
            System.out.println("Table 'employee' created successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}