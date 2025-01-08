<%@ page import="java.sql.*,java.io.*" %>
<%@ page contentType="text/html" %>
<html>
<head>
    <title>Insert Book and Display Details</title>
</head>
<body>
    <h2>Book Details</h2>
    <%
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/book_db"; // Database URL
        String user = "root"; // Database username
        String password = "password"; // Database password

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection(url, user, password);

            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS books ("
                + "book_no INT PRIMARY KEY AUTO_INCREMENT, "
                + "title VARCHAR(255), "
                + "author VARCHAR(255), "
                + "publication VARCHAR(255), "
                + "price DECIMAL(10, 2)"
                + ")";
            ps = conn.prepareStatement(createTableSQL);
            ps.executeUpdate();

            // Get form data
            String bookNo = request.getParameter("bookNo");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publication = request.getParameter("publication");
            String price = request.getParameter("price");

            // Insert book details into the database
            String insertSQL = "INSERT INTO books (book_no, title, author, publication, price) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSQL);
            ps.setInt(1, Integer.parseInt(bookNo));
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, publication);
            ps.setBigDecimal(5, new java.math.BigDecimal(price));
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<p>Book details inserted successfully!</p>");
            }

            // Retrieve and display the book details for the inserted title
            String selectSQL = "SELECT * FROM books WHERE title = ?";
            ps = conn.prepareStatement(selectSQL);
            ps.setString(1, title);
            rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h3>Book Details:</h3>");
                out.println("<p><strong>Book No:</strong> " + rs.getInt("book_no") + "</p>");
                out.println("<p><strong>Title:</strong> " + rs.getString("title") + "</p>");
                out.println("<p><strong>Author:</strong> " + rs.getString("author") + "</p>");
                out.println("<p><strong>Publication:</strong> " + rs.getString("publication") + "</p>");
                out.println("<p><strong>Price:</strong> " + rs.getBigDecimal("price") + "</p>");
            } else {
                out.println("<p>No book found with the given title.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    %>
</body>
</html>
