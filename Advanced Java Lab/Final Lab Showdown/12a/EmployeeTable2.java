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

            // Step 1: Ask for project name and delete records of employees working on the specified project
            System.out.print("Enter project name to delete records of employees working on it: ");
            String projectName = scanner.nextLine();

            String deleteQuery = "DELETE FROM employee WHERE Project = '" + projectName + "'";
            int rowsDeleted = statement.executeUpdate(deleteQuery);
            if (rowsDeleted > 0) {
                System.out.println("Records of employees working on the project '" + projectName + "' deleted successfully.");
            } else {
                System.out.println("No records found for the project: " + projectName);
            }

            // Step 2: Retrieve and display employee details sorted by their salary in descending order
            String selectQuery = "SELECT Employee_ID, Employee_Name, Designation, Dept, Salary FROM employee ORDER BY Salary DESC";
            resultSet = statement.executeQuery(selectQuery);

            System.out.println("\nEmployee Details Sorted by Salary (Descending Order):");
            System.out.println("----------------------------------------------------------");
            System.out.printf("%-15s %-30s %-20s %-15s %-10s\n", "Employee ID", "Employee Name", "Designation", "Department", "Salary");
            System.out.println("----------------------------------------------------------");
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
