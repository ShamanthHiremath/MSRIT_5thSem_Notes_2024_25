package week4;
import java.sql.*;
import java.math.BigDecimal;

public class CustomerDatabase {

    // Database credentials
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank";  // Change the URL to your DB
    static final String USER = "root";  // Replace with your username
    static final String PASSWORD = "";  // Replace with your password

    public static void main(String[] args) {
        // Establish connection and create table
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

            // Step 1: Create Customer table if it doesn't exist
            createTable(conn);

            // Step 2: Insert customer records using PreparedStatement
            insertCustomerData(conn);

            // Step 3: Display customer data
            displayAllCustomers(conn);

            // Step 4: Demonstrate DatabaseMetaData
            displayDatabaseMetadata(conn);

            // Step 5: Demonstrate ResultSetMetaData
            displayResultSetMetadata(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create the table if not exists
    public static void createTable(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Customer ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                + "Name VARCHAR(255), "
                + "Type_of_Customer VARCHAR(255), "
                + "Amount_Spent DECIMAL(10, 2))";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Customer table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert customer data using PreparedStatement
    public static void insertCustomerData(Connection conn) {
        String insertSQL = "INSERT INTO Customer (Name, Type_of_Customer, Amount_Spent) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Inserting sample data
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "Regular");
            pstmt.setBigDecimal(3, new BigDecimal("150.75"));
            pstmt.executeUpdate();

            pstmt.setString(1, "Jane Smith");
            pstmt.setString(2, "Premium");
            pstmt.setBigDecimal(3, new BigDecimal("320.50"));
            pstmt.executeUpdate();

            pstmt.setString(1, "Bill Gates");
            pstmt.setString(2, "VIP");
            pstmt.setBigDecimal(3, new BigDecimal("5000.00"));
            pstmt.executeUpdate();

            System.out.println("Sample customer records inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display all customers
    public static void displayAllCustomers(Connection conn) {
        String selectSQL = "SELECT * FROM Customer";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\nCustomer Table Contents:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String type = rs.getString("Type_of_Customer");
                BigDecimal amountSpent = rs.getBigDecimal("Amount_Spent");

                System.out.printf("ID: %d, Name: %s, Type: %s, Amount Spent: %.2f\n", 
                                  id, name, type, amountSpent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to demonstrate DatabaseMetaData
    public static void displayDatabaseMetadata(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            // Displaying general DB information
            System.out.println("\nDatabase Metadata:");
            System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());
            System.out.println("URL: " + metaData.getURL());
            System.out.println("Username: " + metaData.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to demonstrate ResultSetMetaData
    public static void displayResultSetMetadata(Connection conn) {
        String selectSQL = "SELECT * FROM Customer";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            // Retrieving ResultSetMetaData
            ResultSetMetaData rsMetaData = rs.getMetaData();

            System.out.println("\nResultSet Metadata:");
            int columnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("Column %d: %s (Type: %s)\n", 
                                  i, rsMetaData.getColumnName(i), rsMetaData.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

