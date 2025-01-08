package my_pack;

import java.sql.*;

public class SubjectDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"; // URL to connect to MySQL server
    private static final String USER = "root"; // Update with your DB username
    private static final String PASS = ""; // Update with your DB password

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            // Step 1: Establish the connection to MySQL server
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Step 2: Use the 'SubjectDB' database
            String useDatabaseQuery = "USE SubjectDB";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'SubjectDB'.");

            // Step 3: Update the name of the subject from "Java Programming Lab" to "Advanced Java Programming Lab" 
            // where the code is "CSL56"
            String updateQuery = "SELECT * FROM Subject WHERE Code = 'CSL56'";
            resultSet = statement.executeQuery(updateQuery);
            if (resultSet.next()) {
                resultSet.updateString("Name", "Advanced Java Programming Lab");
                resultSet.updateRow(); // Apply the update
                System.out.println("Subject name updated successfully.");
            } else {
                System.out.println("Subject with code 'CSL56' not found.");
            }

            // Step 4: Delete the subject "System Programming" using PreparedStatement
            String deleteQuery = "DELETE FROM Subject WHERE Name = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, "System Programming");
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Subject 'System Programming' deleted successfully.");
            } else {
                System.out.println("Subject 'System Programming' not found.");
            }

            // Step 5: Display all subjects
            String selectQuery = "SELECT * FROM Subject";
            resultSet = statement.executeQuery(selectQuery);
            System.out.println("\nSubject Details:");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Code: " + resultSet.getString("Code"));
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("Department: " + resultSet.getString("Department"));
                System.out.println("Credits: " + resultSet.getInt("Credits"));
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
