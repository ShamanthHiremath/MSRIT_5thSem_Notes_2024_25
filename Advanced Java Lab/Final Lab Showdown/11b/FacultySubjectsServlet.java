import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class FacultySubjectsServlet extends HttpServlet {

    // JDBC details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/FacultySubjects";
    private static final String JDBC_USER = "root"; // Replace with your MySQL username
    private static final String JDBC_PASSWORD = ""; // Replace with your MySQL password

    @Override
    public void init() throws ServletException {
        // Create the database and tables if they do not exist
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create the database if it does not exist
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS FacultySubjects";
            stmt.executeUpdate(createDatabaseSQL);

            // Use the created database
            stmt.executeUpdate("USE FacultySubjects");

            // Create the faculty table if it does not exist
            String createFacultyTableSQL = "CREATE TABLE IF NOT EXISTS faculty (" +
                    "Faculty_ID INT PRIMARY KEY, " +
                    "Faculty_Name VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(createFacultyTableSQL);

            // Create the subjects table if it does not exist
            String createSubjectsTableSQL = "CREATE TABLE IF NOT EXISTS subjects (" +
                    "Sub_ID INT PRIMARY KEY, " +
                    "Sub_Name VARCHAR(255) NOT NULL, " +
                    "Faculty_ID INT, " +
                    "FOREIGN KEY (Faculty_ID) REFERENCES faculty(Faculty_ID))";
            stmt.executeUpdate(createSubjectsTableSQL);

            // Insert sample data if not already present
            String insertFacultySQL = "INSERT IGNORE INTO faculty (Faculty_ID, Faculty_Name) VALUES " +
                    "(1, 'Dr. Smith'), (2, 'Prof. Johnson')";
            stmt.executeUpdate(insertFacultySQL);

            String insertSubjectsSQL = "INSERT IGNORE INTO subjects (Sub_ID, Sub_Name, Faculty_ID) VALUES " +
                    "(101, 'Mathematics', 1), (102, 'Physics', 2)";
            stmt.executeUpdate(insertSubjectsSQL);

        } catch (SQLException e) {
            throw new ServletException("Database initialization failed", e);
        }
    }

    // Display subjects allotted to a faculty
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get Faculty ID from request
        int facultyId = Integer.parseInt(request.getParameter("facultyId")); // Faculty ID to filter subjects

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            String query = "SELECT Sub_ID, Sub_Name FROM subjects WHERE Faculty_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, facultyId);
                ResultSet rs = stmt.executeQuery();

                out.println("<h2>Subjects Allotted to Faculty ID: " + facultyId + "</h2>");
                out.println("<table border='1'><tr><th>Subject ID</th><th>Subject Name</th></tr>");
                while (rs.next()) {
                    int subId = rs.getInt("Sub_ID");
                    String subName = rs.getString("Sub_Name");
                    out.println("<tr><td>" + subId + "</td><td>" + subName + "</td></tr>");
                }
                out.println("</table>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

    // Update subject details for a faculty
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get subject details from request
        int subId = Integer.parseInt(request.getParameter("subId"));
        String subName = request.getParameter("subName");
        int facultyId = Integer.parseInt(request.getParameter("facultyId"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            String query = "UPDATE subjects SET Sub_Name = ?, Faculty_ID = ? WHERE Sub_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, subName);
                stmt.setInt(2, facultyId);
                stmt.setInt(3, subId);

                int rowsUpdated = stmt.executeUpdate();
                out.println("<h2>Rows Updated: " + rowsUpdated + "</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

}
