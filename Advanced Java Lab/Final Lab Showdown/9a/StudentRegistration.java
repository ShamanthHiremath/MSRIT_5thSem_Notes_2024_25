package my_pack;

import java.sql.*;

public class RegistrationDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"; // URL to connect to MySQL server
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Establish the connection to MySQL server
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            // Step 2: Create the 'Registration' database if it doesn't exist
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS Registration";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database 'Registration' created successfully.");

            // Step 3: Use the 'Registration' database
            String useDatabaseQuery = "USE Registration";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'Registration'.");

            // Step 4: Create the 'student' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS student (" +
                                      "Roll_No INT PRIMARY KEY, " +
                                      "Name VARCHAR(50), " +
                                      "Program VARCHAR(50), " +
                                      "Year_of_Admission INT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'student' created successfully.");

            // Step 5: Insert sample student data into the 'student' table
            String insertQuery = "INSERT INTO student (Roll_No, Name, Program, Year_of_Admission) VALUES" +
                                 "(101, 'Alice', 'BE', 2023), " +
                                 "(102, 'Bob', 'BE', 2022), " +
                                 "(103, 'Charlie', 'BE', 2023), " +
                                 "(104, 'David', 'MSc', 2023), " +
                                 "(105, 'Eve', 'BE', 2023)";
            statement.executeUpdate(insertQuery);
            System.out.println("Sample student data inserted successfully.");

            // Step 6: List the name and roll number of all students enrolled in 2023
            String select2023Query = "SELECT Roll_No, Name FROM student WHERE Year_of_Admission = 2023";
            resultSet = statement.executeQuery(select2023Query);

            System.out.println("\nStudents enrolled in the year 2023:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Roll-No: " + resultSet.getInt("Roll_No"));
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("------------------------------------------------------------");
            }

            // Step 7: List the roll number and name of all BE Program students
            String selectBEQuery = "SELECT Roll_No, Name FROM student WHERE Program = 'BE'";
            resultSet = statement.executeQuery(selectBEQuery);

            System.out.println("\nStudents enrolled in BE Program:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Roll-No: " + resultSet.getInt("Roll_No"));
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("------------------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 8: Close resources
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
