package my_pack;

import java.sql.*;

public class StudentDatabase {

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

            // Step 2: Create the 'StudentDB' database if it doesn't exist
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS StudentDB";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database 'StudentDB' created successfully.");

            // Step 3: Use the 'StudentDB' database
            String useDatabaseQuery = "USE StudentDB";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'StudentDB'.");

            // Step 4: Create the 'Student' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Student (" +
                                      "name VARCHAR(50), " +
                                      "usn VARCHAR(15), " +
                                      "sem INT, " +
                                      "course VARCHAR(50), " +
                                      "grade CHAR(1))";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'Student' created successfully.");

            // Step 5: Insert sample student data into the 'Student' table
            String insertQuery = "INSERT INTO Student (name, usn, sem, course, grade) VALUES" +
                                 "('Ram', '101', 4, 'Computer Science', 'A'), " +
                                 "('John', '102', 3, 'Mathematics', 'B'), " +
                                 "('Alice', '103', 2, 'Physics', 'A')";
            statement.executeUpdate(insertQuery);
            System.out.println("Sample student data inserted successfully.");

            // Step 6: Update the grade of student 'Ram' from 'A' to 'S'
            String updateQuery = "UPDATE Student SET grade = ? WHERE name = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, "S");  // Update grade to 'S'
            preparedStatement.setString(2, "Ram"); // Condition for 'Ram'
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("\nGrade of student 'Ram' updated to 'S'.");
            } else {
                System.out.println("\nStudent 'Ram' not found.");
            }

            // Step 7: Display all student details
            String selectQuery = "SELECT * FROM Student";
            resultSet = statement.executeQuery(selectQuery);

            System.out.println("\nStudent Details:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("USN: " + resultSet.getString("usn"));
                System.out.println("Semester: " + resultSet.getInt("sem"));
                System.out.println("Course: " + resultSet.getString("course"));
                System.out.println("Grade: " + resultSet.getString("grade"));
                System.out.println("------------------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 8: Close resources
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
