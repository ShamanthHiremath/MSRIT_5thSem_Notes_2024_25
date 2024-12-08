package shoo2;

import java.sql.*;
import java.util.Scanner;

public class SubjectDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoo"; // Update with your DB details
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // i. Update the Name of the subject from "Java Programming Lab" to "Advanced Java Programming Lab" with Code as CSL56
            String updateQuery = "SELECT * FROM Subject WHERE Code = 'CSL56' AND Name = 'Java Programming Lab'";
            resultSet = statement.executeQuery(updateQuery);

            if (resultSet.next()) {
                resultSet.updateString("Name", "Advanced Java Programming Lab");
                resultSet.updateRow();  // Apply the update to the database
                System.out.println("Updated subject Name to 'Advanced Java Programming Lab' where Code = 'CSL56'.");
            } else {
                System.out.println("Subject with Code 'CSL56' and Name 'Java Programming Lab' not found.");
            }

            // ii. Delete the subject "System Programming" from the table using PreparedStatement
            String deleteQuery = "DELETE FROM Subject WHERE Name = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, "System Programming");
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Deleted subject 'System Programming' from the table.");
            } else {
                System.out.println("Subject 'System Programming' not found.");
            }

            // iii. Display details of all the subjects
            String displayQuery = "SELECT * FROM Subject";
            resultSet = statement.executeQuery(displayQuery);

            System.out.println("\nAll Subjects:");
            while (resultSet.next()) {
                System.out.println("Code: " + resultSet.getString("Code") +
                                   ", Name: " + resultSet.getString("Name") +
                                   ", Department: " + resultSet.getString("Department") +
                                   ", Credits: " + resultSet.getInt("Credits"));
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
