package my_pack;

import java.sql.*;
import java.util.Scanner;

public class StudentDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/my_db"; // Update with your DB details
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            // Step 1: Create the 'student' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS student (" +
                                      "Name VARCHAR(50), " +
                                      "USN VARCHAR(15) PRIMARY KEY, " +
                                      "Branch VARCHAR(50), " +
                                      "Admission_Method VARCHAR(20))";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'student' created successfully.");

            // Step 2: Read student details from the user
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter USN: ");
            String usn = scanner.nextLine();

            System.out.print("Enter Branch: ");
            String branch = scanner.nextLine();

            System.out.print("Enter Admission Method (CET or COMED_K): ");
            String admissionMethod = scanner.nextLine();

            // Step 3: Insert student data into the table
            String insertQuery = "INSERT INTO student (Name, USN, Branch, Admission_Method) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, usn);
            preparedStatement.setString(3, branch);
            preparedStatement.setString(4, admissionMethod);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student data inserted successfully.");
            }

            // Step 4: Extract students admitted through CET and with CSE branch
            String selectQuery = "SELECT Name, USN, Branch, Admission_Method FROM student WHERE Admission_Method = 'CET' AND Branch = 'CSE'";
            resultSet = statement.executeQuery(selectQuery);

            // Step 5: Display the extracted results in a proper format
            System.out.println("\nStudents admitted through CET with CSE branch:");
            System.out.println("---------------------------------------------------------");
            System.out.printf("%-30s %-15s %-15s %-20s\n", "Name", "USN", "Branch", "Admission Method");
            System.out.println("---------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-30s %-15s %-15s %-20s\n",
                        resultSet.getString("Name"),
                        resultSet.getString("USN"),
                        resultSet.getString("Branch"),
                        resultSet.getString("Admission_Method"));
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