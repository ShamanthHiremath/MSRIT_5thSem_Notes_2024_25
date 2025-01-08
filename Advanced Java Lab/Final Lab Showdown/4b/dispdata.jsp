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
            PreparedStatement prepStmt = null;
            ResultSet rs = null;
            String jdbcURL = "jdbc:mysql://localhost:3306/USN";
            String jdbcUsername = "root";
            String jdbcPassword = "";
            
            try {
                // Load and register MySQL driver (optional in newer JDBC versions)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish a database connection
                conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

                // Prepare SQL query to fetch student details based on USN
                String sql = "SELECT name FROM students WHERE usn = ?";
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setString(1, usn);

                // Execute the query and retrieve results
                rs = prepStmt.executeQuery();

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
                    if (prepStmt != null) prepStmt.close();
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
