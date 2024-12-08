package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DeleteEmployeeExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/mydatabase"; // Change to your database name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statement to delete the row with empname 'John'
        String sql = "DELETE FROM employee WHERE empname = 'John'";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL delete statement
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println("Deleted " + rowsAffected + " row(s) successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}