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
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String employeeName = request.getParameter("employeeName");
        double hoursWorked = Double.parseDouble(request.getParameter("hoursWorked"));
        double hourlyRate = Double.parseDouble(request.getParameter("hourlyRate"));
        double taxPercentage = Double.parseDouble(request.getParameter("tax"));

        // Calculate the gross pay, tax, and net pay
        double grossPay = hoursWorked * hourlyRate;
        double taxAmount = grossPay * (taxPercentage / 100);
        double netPay = grossPay - taxAmount;

        // Generate the payroll statement
        out.println("<html><body>");
        out.println("<h2>Payroll Statement</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>Employee Name</th><td>" + employeeName + "</td></tr>");
        out.println("<tr><th>Hours Worked</th><td>" + hoursWorked + "</td></tr>");
        out.println("<tr><th>Hourly Rate ($)</th><td>" + hourlyRate + "</td></tr>");
        out.println("<tr><th>Gross Pay ($)</th><td>" + grossPay + "</td></tr>");
        out.println("<tr><th>Tax (" + taxPercentage + "%)</th><td>" + taxAmount + "</td></tr>");
        out.println("<tr><th>Net Pay ($)</th><td>" + netPay + "</td></tr>");
        out.println("</table>");
        out.println("</body></html>");
    }
}
