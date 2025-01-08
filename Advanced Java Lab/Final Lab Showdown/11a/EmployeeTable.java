package my_pack;

import java.sql.*;
import java.util.Scanner;

public class EmployeeDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/my_db"; // Update with your DB details
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            // Step 1: Create 'employee' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS employee (" +
                                      "Employee_ID INT PRIMARY KEY AUTO_INCREMENT, " +
                                      "Employee_Name VARCHAR(50), " +
                                      "Designation VARCHAR(50), " +
                                      "Dept VARCHAR(50), " +
                                      "Salary DECIMAL(10, 2))";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'employee' created successfully.");

            // Step 2: Insert employee data (can insert multiple employees)
            System.out.println("Enter employee details:");

            System.out.print("Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Department: ");
            String dept = scanner.nextLine();

            System.out.print("Salary: ");
            double salary = scanner.nextDouble();

            // Insert employee data into the table
            String insertQuery = "INSERT INTO employee (Employee_Name, Designation, Dept, Salary) " +
                                 "VALUES ('" + name + "', '" + designation + "', '" + dept + "', " + salary + ")";
            statement.executeUpdate(insertQuery);
            System.out.println("Employee data inserted successfully.");

            // Step 3: Retrieve and display employees with salary greater than 50,000
            String selectQuery = "SELECT Employee_ID, Employee_Name, Designation, Dept, Salary FROM employee " +
                                 "WHERE Salary > 50000";
            resultSet = statement.executeQuery(selectQuery);

            // Step 4: Display the extracted results in a proper format
            System.out.println("\nEmployees with salary greater than 50,000:");
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-15s %-30s %-20s %-15s %-10s\n", "Employee ID", "Employee Name", "Designation", "Department", "Salary");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-15d %-30s %-20s %-15s %-10.2f\n",
                        resultSet.getInt("Employee_ID"),
                        resultSet.getString("Employee_Name"),
                        resultSet.getString("Designation"),
                        resultSet.getString("Dept"),
                        resultSet.getDouble("Salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
