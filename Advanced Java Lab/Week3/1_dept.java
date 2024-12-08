package shooo;

import java.sql.*;
import java.util.Scanner;

public class Department {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Week3"; // Update with your DB details
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            
            // i. Display details of all the Departments using Statement object
            String queryAllDepartments = "SELECT * FROM Department";
            resultSet = statement.executeQuery(queryAllDepartments);
            System.out.println("\nAll Departments:");
            while (resultSet.next()) {
                System.out.println("Dept_ID: " + resultSet.getInt("Dept_ID") +
                                   ", Name: " + resultSet.getString("Name") +
                                   ", Year_Established: " + resultSet.getInt("Year_Established") +
                                   ", Head_Name: " + resultSet.getString("Head_Name") +
                                   ", No_of_Employees: " + resultSet.getInt("No_of_Employees"));
            }

            // ii. Display departments established in the year 2000 using PreparedStatement
            System.out.print("\nEnter the year to filter departments: ");
            int year = scanner.nextInt();
            String queryByYear = "SELECT * FROM Department WHERE Year_Established = ?";
            preparedStatement = connection.prepareStatement(queryByYear);
            preparedStatement.setInt(1, year);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nDepartments established in the year " + year + ":");
                do {
                    System.out.println("Dept_ID: " + resultSet.getInt("Dept_ID") +
                                       ", Name: " + resultSet.getString("Name") +
                                       ", Year_Established: " + resultSet.getInt("Year_Established") +
                                       ", Head_Name: " + resultSet.getString("Head_Name") +
                                       ", No_of_Employees: " + resultSet.getInt("No_of_Employees"));
                } while (resultSet.next());
            } else {
                System.out.println("No departments found established in the year " + year);
            }

            // iii. Display departments by reading Dept_ID and Department Name from user
            System.out.print("\nEnter Dept_ID: ");
            int deptId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter Department Name: ");
            String deptName = scanner.nextLine();

            String queryByIdName = "SELECT * FROM Department WHERE Dept_ID = ? AND Name = ?";
            preparedStatement = connection.prepareStatement(queryByIdName);
            preparedStatement.setInt(1, deptId);
            preparedStatement.setString(2, deptName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nDepartment details for Dept_ID: " + deptId + " and Name: " + deptName);
                do {
                    System.out.println("Dept_ID: " + resultSet.getInt("Dept_ID") +
                                       ", Name: " + resultSet.getString("Name") +
                                       ", Year_Established: " + resultSet.getInt("Year_Established") +
                                       ", Head_Name: " + resultSet.getString("Head_Name") +
                                       ", No_of_Employees: " + resultSet.getInt("No_of_Employees"));
                } while (resultSet.next());
            } else {
                System.out.println("No departments found with the given Dept_ID and Name.");
            }

            // iv. Insert a new department using PreparedStatement and display the updated list
            System.out.print("\nEnter new Dept_ID: ");
            int newDeptId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new Department Name: ");
            String newDeptName = scanner.nextLine();
            System.out.print("Enter Year Established: ");
            int newYearEstablished = scanner.nextInt();
            System.out.print("Enter Head Name: ");
            scanner.nextLine(); // Consume newline
            String newHeadName = scanner.nextLine();
            System.out.print("Enter Number of Employees: ");
            int newNoOfEmployees = scanner.nextInt();

            String insertQuery = "INSERT INTO Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) " +
                                 "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, newDeptId);
            preparedStatement.setString(2, newDeptName);
            preparedStatement.setInt(3, newYearEstablished);
            preparedStatement.setString(4, newHeadName);
            preparedStatement.setInt(5, newNoOfEmployees);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("\nNew department inserted, " + rowsAffected + " row(s) affected.");

            // Display all departments after insertion
            resultSet = statement.executeQuery(queryAllDepartments);
            System.out.println("\nUpdated list of all Departments:");
            while (resultSet.next()) {
                System.out.println("Dept_ID: " + resultSet.getInt("Dept_ID") +
                                   ", Name: " + resultSet.getString("Name") +
                                   ", Year_Established: " + resultSet.getInt("Year_Established") +
                                   ", Head_Name: " + resultSet.getString("Head_Name") +
                                   ", No_of_Employees: " + resultSet.getInt("No_of_Employees"));
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
                if (scanner != null) scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
