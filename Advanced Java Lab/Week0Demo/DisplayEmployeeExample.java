package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DisplayEmployeeExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/mydatabase"; // Change to your database name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statement to select all records from the employee table
        String sql = "SELECT * FROM employee";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Display the content of the table
            System.out.println("empid\tempname\t\tsalary");
            System.out.println("-----------------------------------");
            while (rs.next()) {
                int empid = rs.getInt("empid");
                String empname = rs.getString("empname");
                double salary = rs.getDouble("salary");
                System.out.printf("%d\t%s\t%.2f%n", empid, empname, salary);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
