<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Book</title>
</head>
<body>
    <h1>Book Details</h1>
    <%
        String searchTitle = request.getParameter("searchTitle");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookStore", "root", "");

            String query = "SELECT * FROM Books WHERE Title = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, searchTitle);
            rs = stmt.executeQuery();

            if (rs.next()) {
                out.println("<p>Book Number: " + rs.getInt("Book_No") + "</p>");
                out.println("<p>Title: " + rs.getString("Title") + "</p>");
                out.println("<p>Author: " + rs.getString("Author") + "</p>");
                out.println("<p>Publication: " + rs.getString("Publication") + "</p>");
                out.println("<p>Price: " + rs.getDouble("Price") + "</p>");
            } else {
                out.println("<p>Invalid Title. No book found.</p>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    %>
    <a href="index.html">Back to Form</a>
</body>
</html>