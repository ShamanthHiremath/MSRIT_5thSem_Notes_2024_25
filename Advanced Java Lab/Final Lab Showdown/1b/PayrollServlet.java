import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        
        // Retrieve form data
        String employeeName = request.getParameter("employeeName");
        double hoursWorked = Double.parseDouble(request.getParameter("hoursWorked"));
        double hourlyRate = Double.parseDouble(request.getParameter("hourlyRate"));
        // double taxPercentage = 20.0;

        // Calculate the gross pay, tax, and net pay
        double grossPay = hoursWorked * hourlyRate;
        double taxAmount = grossPay * 0.2;
        double netPay = grossPay - taxAmount;

        // Output payroll statement
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Payroll Statement</title></head>");
        out.println("<body>");
        out.println("<h1>Employee Payroll Statement</h1>");
        out.println("<p><strong>Employee Name:</strong> " + employeeName + "</p>");
        out.println("<p><strong>Hours Worked:</strong> " + hoursWorked + "</p>");
        out.println("<p><strong>Hourly Pay Rate:</strong> $" + hourlyRate + "</p>");
        out.println("<p><strong>Gross Pay:</strong> $" + grossPay + "</p>");
        out.println("<p><strong>Tax (20%):</strong> $" + tax + "</p>");
        out.println("<p><strong>Net Pay:</strong> $" + netPay + "</p>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
