package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class UpdateEmployeeExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/mydatabase"; // Change to your database name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statement to update Alice's name to John
        String sql = "UPDATE employee SET empname = 'John' WHERE empname = 'Alice'";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL update statement
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println("Updated " + rowsAffected + " row(s) successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}