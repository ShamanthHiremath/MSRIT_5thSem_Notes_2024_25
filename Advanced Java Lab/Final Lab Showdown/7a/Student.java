package my_pack;

import java.sql.*;

public class CollegeDatabase {

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
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Step 2: Use the 'College' database
            String useDatabaseQuery = "USE College";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'College'.");

            // Step 3: Display details of students who have CGPA less than 9
            String selectQuery = "SELECT * FROM Student WHERE CGPA < 9";
            resultSet = statement.executeQuery(selectQuery);

            System.out.println("Students with CGPA less than 9:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("USN: " + resultSet.getString("usn"));
                System.out.println("Department: " + resultSet.getString("dept"));
                System.out.println("CGPA: " + resultSet.getFloat("CGPA"));
                System.out.println("------------------------------------------------------------");
            }

            // Step 4: Update the CGPA of student named "John" from 8.50 to 9.4
            String updateQuery = "SELECT * FROM Student WHERE name = 'John' AND CGPA = 8.50";
            resultSet = statement.executeQuery(updateQuery);
            if (resultSet.next()) {
                resultSet.updateFloat("CGPA", 9.4f);  // Update the CGPA
                resultSet.updateRow();  // Apply the update
                System.out.println("\nCGPA of student 'John' updated to 9.4.");
            } else {
                System.out.println("\nStudent 'John' with CGPA 8.50 not found.");
            }

            // Step 5: Display all student details after update
            resultSet = statement.executeQuery("SELECT * FROM Student");
            System.out.println("\nUpdated Student Details:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("USN: " + resultSet.getString("usn"));
                System.out.println("Department: " + resultSet.getString("dept"));
                System.out.println("CGPA: " + resultSet.getFloat("CGPA"));
                System.out.println("------------------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
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
