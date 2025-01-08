package my_pack;

import java.sql.*;

public class CountryDatabase {

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

            // Step 2: Create the 'CountryDB' database if it doesn't exist
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS CountryDB";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database 'CountryDB' created successfully.");

            // Step 3: Use the 'CountryDB' database
            String useDatabaseQuery = "USE CountryDB";
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Using database 'CountryDB'.");

            // Step 4: Create the 'Country' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Country (" +
                                      "country_code VARCHAR(10) PRIMARY KEY, " +
                                      "capital VARCHAR(50), " +
                                      "continent VARCHAR(50), " +
                                      "population BIGINT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'Country' created successfully.");

            // Step 5: Insert sample country data into the 'Country' table
            String insertQuery = "INSERT INTO Country (country_code, capital, continent, population) VALUES" +
                                 "('IN', 'New Delhi', 'Asia', 1393409038), " +
                                 "('US', 'Washington, D.C.', 'North America', 331002651), " +
                                 "('BR', 'Brasilia', 'South America', 212559417), " +
                                 "('CN', 'Beijing', 'Asia', 1444216107), " +
                                 "('DE', 'Berlin', 'Europe', 83783942)";
            statement.executeUpdate(insertQuery);
            System.out.println("Sample country data inserted successfully.");

            // Step 6: Display country details in ascending order of population
            String selectQuery = "SELECT * FROM Country ORDER BY population ASC";
            resultSet = statement.executeQuery(selectQuery);

            System.out.println("\nCountry Details (Sorted by Population in Ascending Order):");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.println("Country Code: " + resultSet.getString("country_code"));
                System.out.println("Capital: " + resultSet.getString("capital"));
                System.out.println("Continent: " + resultSet.getString("continent"));
                System.out.println("Population: " + resultSet.getLong("population"));
                System.out.println("------------------------------------------------------------");
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
