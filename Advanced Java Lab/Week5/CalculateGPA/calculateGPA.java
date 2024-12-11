package mypack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/Case")


public class calculateGPA extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the user input
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        int semesters = Integer.parseInt(request.getParameter("semesters"));
        String sgpaList = request.getParameter("sgpaList");

        // Calculate CGPA
        String[] sgpas = sgpaList.split(",");
        double totalSGPA = 0.0;

        for (String sgpa : sgpas) {
            totalSGPA += Double.parseDouble(sgpa.trim());
        }

        double cgpa = totalSGPA / semesters;

        // Display results
        out.println("<html>");
        out.println("<head><title>CGPA Result</title></head>");
        out.println("<body>");
        out.println("<h2>CGPA Calculation Result</h2>");
        out.println("<p><b>USN:</b> " + usn + "</p>");
        out.println("<p><b>Name:</b> " + name + "</p>");
        out.println("<p><b>Number of Semesters:</b> " + semesters + "</p>");
        out.println("<p><b>Entered SGPAs:</b> " + sgpaList + "</p>");
        out.println("<p><b>Calculated CGPA:</b> " + String.format("%.2f", cgpa) + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}