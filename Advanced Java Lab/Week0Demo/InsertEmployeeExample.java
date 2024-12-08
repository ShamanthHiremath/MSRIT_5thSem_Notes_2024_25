package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class InsertEmployeeExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/shoo"; // Change to your database name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statements to insert data into the employee table
        String sql1 = "INSERT INTO employee (empid, empname, salary) VALUES (1, 'Alice', 50000.00)";
        String sql2 = "INSERT INTO employee (empid, empname, salary) VALUES (2, 'Bob', 55000.00)";
        String sql3 = "INSERT INTO employee (empid, empname, salary) VALUES (3, 'Charlie', 60000.00)";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL statements
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);

            System.out.println("Inserted 3 rows into the employee table successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}