package shoo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoo"; // Update this
    private static final String USER = "root"; // Update this
    private static final String PASS = ""; // Update this

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            // Create the employees table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                    "ID INT PRIMARY KEY, " +
                    "FName VARCHAR(50), " +
                    "LName VARCHAR(50), " +
                    "Project VARCHAR(100), " +
                    "Salary DECIMAL(10, 2))";
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'employees' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT INTO employees (ID, FName, LName, Project, Salary) VALUES " +
                    "(1, 'John', 'Doe', 'Web Development', 80000), " +
                    "(2, 'Jane', 'Smith', 'Mobile App', 60000), " +
                    "(3, 'Alice', 'Johnson', 'Web Development', 75000), " +
                    "(4, 'Bob', 'Brown', 'Web Development', 50000), " +
                    "(5, 'Charlie', 'Davis', 'Software Engineering', 45000), " +
                    "(6, 'David', 'Wilson', 'Web Development', 90000), " +
                    "(7, 'Eva', 'Green', 'Data Analysis', 40000), " +
                    "(8, 'Frank', 'Miller', 'Web Development', 70000)";
            statement.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'employees' table.");

            // i. Display details of all the employees
            String queryAllEmployees = "SELECT * FROM employees";
            ResultSet rsAll = statement.executeQuery(queryAllEmployees);
            System.out.println("\nAll Employees:");
            while (rsAll.next()) {
                System.out.println("ID: " + rsAll.getInt("ID") +
                                   ", Name: " + rsAll.getString("FName") + " " + rsAll.getString("LName") +
                                   ", Project: " + rsAll.getString("Project") +
                                   ", Salary: " + rsAll.getBigDecimal("Salary"));
            }

            // ii. Display details of all the employees who work for project “Web Development”
            String queryWebDevelopment = "SELECT * FROM employees WHERE Project = 'Web Development'";
            ResultSet rsWebDev = statement.executeQuery(queryWebDevelopment);
            System.out.println("\nEmployees in 'Web Development':");
            while (rsWebDev.next()) {
                System.out.println("ID: " + rsWebDev.getInt("ID") +
                                   ", Name: " + rsWebDev.getString("FName") + " " + rsWebDev.getString("LName") +
                                   ", Salary: " + rsWebDev.getBigDecimal("Salary"));
            }

            // iii. Display the IDs of all those employees who have salary above 75,000 and are in “Web Development”
            String queryHighSalaryWebDev = "SELECT ID FROM employees WHERE Salary > 75000 AND Project = 'Web Development'";
            ResultSet rsHighSalary = statement.executeQuery(queryHighSalaryWebDev);
            System.out.println("\nEmployees in 'Web Development' with Salary > 75,000:");
            while (rsHighSalary.next()) {
                System.out.println("ID: " + rsHighSalary.getInt("ID"));
            }

            // iv. Display the total number of employees who have salary less than 50,000
            String queryLowSalaryCount = "SELECT COUNT(*) AS total FROM employees WHERE Salary < 50000";
            ResultSet rsTotalLowSalary = statement.executeQuery(queryLowSalaryCount);
            if (rsTotalLowSalary.next()) {
                System.out.println("\nTotal number of employees with Salary < 50,000: " + rsTotalLowSalary.getInt("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
