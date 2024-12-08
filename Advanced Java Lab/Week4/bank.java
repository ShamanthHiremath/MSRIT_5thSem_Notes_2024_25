package week4;

import java.sql.*;
import java.util.Scanner;

public class stu {
	private static final String URL = "jdbc:mysql://localhost:3306/bank"; // Change your DB URL
    private static final String USER = "root"; // Change your DB username
    private static final String PASSWORD = ""; // Change your DB password

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Establishing Connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false); // Disable auto-commit for transaction management

            // Insert using PreparedStatement
            String insertSQL = "INSERT INTO bank (no, name, type, balance) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);

            // Inserting sample accounts
            System.out.println("Inserting sample accounts...");
//            insertAccount(preparedStatement, 101, "Alice", "Savings", 5000.75);
//            insertAccount(preparedStatement, 102, "Bob", "Checking", 10000.50);
//            insertAccount(preparedStatement, 103, "Charlie", "Savings", 3000.25);
            
            // Commit the transaction
            connection.commit();
            System.out.println("Sample accounts inserted successfully!");

            // Step 2: Displaying all Accounts
            System.out.println("\nDisplaying all Accounts:");
            displayAccounts(connection);

            // Step 3: Demonstrating Rollback and Commit
            System.out.println("\nDemonstrating Rollback and Commit...");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            // Inserting new accounts but let's simulate a failure and roll back the transaction
            String faultyInsertSQL = "INSERT INTO bank (no, name, type, balance) VALUES (104, 'Dave', 'Current', -1500.0)";
            try {
                statement.executeUpdate(faultyInsertSQL); // This will fail due to negative balance
                connection.commit(); // This will not be reached if the exception is thrown
            } catch (SQLException e) {
                connection.rollback(); // Rollback in case of an error
                System.out.println("Transaction rolled back due to error: " + e.getMessage());
            }

            // Step 4: Demonstrating Savepoints
            System.out.println("\nDemonstrating Savepoints...");
            Savepoint savepoint = connection.setSavepoint("BeforeFaultyInsert");

            try {
                // Inserting some new accounts
                insertAccount(preparedStatement, 105, "Eve", "Savings", 12000.00);
                insertAccount(preparedStatement, 106, "Frank", "Checking", 6000.00);
                connection.commit(); // Commit if successful

                // Introduce an intentional error for rollback
                String erroneousSQL = "INSERT INTO bank (no, name, type, balance) VALUES (107, 'Grace', 'Checking', NULL)";
                statement.executeUpdate(erroneousSQL); // This will cause an error because balance cannot be null
                connection.commit(); // This won't be reached if the exception occurs
            } catch (SQLException e) {
                connection.rollback(savepoint); // Rollback to savepoint if error occurs
                System.out.println("Rolled back to savepoint due to error: " + e.getMessage());
            }

            // Step 5: Displaying final state of accounts
            System.out.println("\nFinal Accounts state after rollback to savepoint:");
            displayAccounts(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback in case of any error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to insert account into the bank table
    private static void insertAccount(PreparedStatement preparedStatement, int no, String name, String type, double balance) throws SQLException {
        preparedStatement.setInt(1, no);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, type);
        preparedStatement.setDouble(4, balance);
        preparedStatement.executeUpdate();
    }

    // Method to display all accounts in the bank table
    private static void displayAccounts(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM bank";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        while (resultSet.next()) {
            int no = resultSet.getInt("no");
            String name = resultSet.getString("name");
            String type = resultSet.getString("type");
            double balance = resultSet.getDouble("balance");

            System.out.println("Account No: " + no + ", Name: " + name + ", Type: " + type + ", Balance: " + balance);
        }
    }
}
