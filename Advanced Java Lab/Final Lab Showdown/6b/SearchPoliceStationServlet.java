import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchPoliceStationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/police_db";
        String user = "root";  // Replace with your database username
        String password = "";  // Replace with your database password

        // Fetch user input
        String searchKey = request.getParameter("searchKey");

        // Initialize response content
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Prepare SQL query to fetch police station details
            String query = "SELECT area, phone_number FROM police_station WHERE area = ? OR phone_number = ?";
            try (PreparedStatement prepStmt = conn.prepareStatement(query)) {
                prepStmt.setString(1, searchKey);
                prepStmt.setString(2, searchKey);

                try (ResultSet resultSet = prepStmt.executeQuery()) {
                    if (resultSet.next()) {
                        out.println("<h2>Police Station Details</h2>");
                        out.println("<p>Area: " + resultSet.getString("area") + "</p>");
                        out.println("<p>Phone Number: " + resultSet.getString("phone_number") + "</p>");
                    } else {
                        out.println("<p>No police station found for the given input.</p>");
                    }
                }
            }
        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</body></html>");
        out.close();
    }
}
