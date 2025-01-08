package my_pack;

import java.sql.*;

public class EmployeeDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"; // URL to connect to MySQL server
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Establish the connection to MySQL server
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            // Step 2: Create the 'EmployeeDB' database if it doesn't exist
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS EmployeeDB";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database 'EmployeeDB' created successfully.");

            // Step 3: Use the 'EmployeeDB' database
            String useDatabaseQuery = "USE EmployeeDB";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'EmployeeDB'.");

            // Step 4: Create the 'Employee' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Employee (" +
                                      "name VARCHAR(50), " +
                                      "designation VARCHAR(50), " +
                                      "dept VARCHAR(50), " +
                                      "salary DECIMAL(10, 2))";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'Employee' created successfully.");

            // Step 5: Insert sample employee data into the 'Employee' table
            String insertQuery = "INSERT INTO Employee (name, designation, dept, salary) VALUES" +
                                 "('John Doe', 'Assistant Professor', 'Computer Science', 55000.00), " +
                                 "('Alice Smith', 'Associate Professor', 'Mathematics', 65000.00), " +
                                 "('Bob Brown', 'Assistant Professor', 'Physics', 58000.00)";
            statement.executeUpdate(insertQuery);
            System.out.println("Sample employee data inserted successfully.");

            // Step 6: Update designation from 'Assistant Professor' to 'Associate Professor' for the employee "John Doe"
            String updateQuery = "SELECT * FROM Employee WHERE name = 'John Doe' AND designation = 'Assistant Professor'";
            resultSet = statement.executeQuery(updateQuery);
            if (resultSet.next()) {
                resultSet.updateString("designation", "Associate Professor");
                resultSet.updateRow(); // Apply the update
                System.out.println("Employee designation updated successfully.");
            } else {
                System.out.println("Employee 'John Doe' with designation 'Assistant Professor' not found.");
            }

            // Step 7: Display all employee details
            String selectQuery = "SELECT * FROM Employee";
            resultSet = statement.executeQuery(selectQuery);
            System.out.println("\nEmployee Details:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Designation: " + resultSet.getString("designation"));
                System.out.println("Department: " + resultSet.getString("dept"));
                System.out.println("Salary: " + resultSet.getBigDecimal("salary"));
                System.out.println("------------------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
