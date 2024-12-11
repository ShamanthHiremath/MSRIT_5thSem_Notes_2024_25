


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @WebServlet("/InsertEmployee")
public class InsertEmployeeServ extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String empId = request.getParameter("Emp_ID");
        String empName = request.getParameter("Employee_Name");
        String address = request.getParameter("Address");
        String dob = request.getParameter("Date_of_Birth");

        // Validation for empty fields
        if (empId == null || empName == null || address == null || dob == null ||
            empId.isEmpty() || empName.isEmpty() || address.isEmpty() || dob.isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>All fields are required. Please go back and fill the form correctly.</h3>");
            out.println("</body></html>");
            return;
        }

        // Insert data into the database
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String insertSQL = "INSERT INTO Employee (Emp_ID, Employee_Name, Address, Date_of_Birth) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, Integer.parseInt(empId));
                preparedStatement.setString(2, empName);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, dob);
                preparedStatement.executeUpdate();
            }

            // Fetch and display the inserted data
            String selectSQL = "SELECT * FROM Employee WHERE Emp_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setInt(1, Integer.parseInt(empId));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    out.println("<html><body>");
                    out.println("<h2>Employee Details</h2>");
                    out.println("<table border='1'>");
                    out.println("<tr><th>Emp_ID</th><th>Employee_Name</th><th>Address</th><th>Date_of_Birth</th></tr>");
                    if (resultSet.next()) {
                        out.println("<tr>");
                        out.println("<td>" + resultSet.getInt("Emp_ID") + "</td>");
                        out.println("<td>" + resultSet.getString("Employee_Name") + "</td>");
                        out.println("<td>" + resultSet.getString("Address") + "</td>");
                        out.println("<td>" + resultSet.getDate("Date_of_Birth") + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</body></html>");
                }
            }
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }
}
