<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.io.*"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Information</title>
</head>
<body>
    <h2>Student Information</h2>

    <%
        // Retrieve the USN from the form
        String usn = request.getParameter("usn");

        if (usn != null && !usn.trim().isEmpty()) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String jdbcURL = "jdbc:mysql://localhost:3306/your_database_name";
            String jdbcUsername = "your_username";
            String jdbcPassword = "your_password";
            
            try {
                // Load and register MySQL driver (optional in newer JDBC versions)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish a database connection
                conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

                // Prepare SQL query to fetch student details based on USN
                String sql = "SELECT name FROM students WHERE usn = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, usn);

                // Execute the query and retrieve results
                rs = stmt.executeQuery();

                if (rs.next()) {
                    // If USN is found, display student details
                    String studentName = rs.getString("name");
                    out.println("<p><strong>USN:</strong> " + usn + "</p>");
                    out.println("<p><strong>Name:</strong> " + studentName + "</p>");
                } else {
                    // If USN is not found, display error message
                    out.println("<p style='color:red;'><strong>Invalid USN!</strong></p>");
                }
            } catch (Exception e) {
                out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
                e.printStackTrace();
            } finally {
                // Close resources
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            out.println("<p style='color:red;'>USN is required!</p>");
        }
    %>

</body>
</html>
