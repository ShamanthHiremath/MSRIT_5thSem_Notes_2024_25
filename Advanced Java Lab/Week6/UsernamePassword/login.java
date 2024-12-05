package mypack;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String JDBC_USER = "your_database_username";
    private static final String JDBC_PASSWORD = "your_database_password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username and password entered by the user
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Validate user credentials
        if (isValidUser(username, password)) {
            out.println("<html>");
            out.println("<body>");
            out.println("<h2>Welcome, " + username + "!</h2>");
            out.println("</body>");
            out.println("</html>");
        } else {
            out.println("<html>");
            out.println("<body>");
            out.println("<h2>Invalid user. Please try again.</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean isValidUser(String username, String password) {
        boolean valid = false;

        // Establish database connection and verify user credentials
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // If a matching record is found, the user is valid
                    if (resultSet.next()) {
                        valid = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valid;
    }
}
