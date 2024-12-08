import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class CGPACalculationServlet extends HttpServlet {

    // doGet method to display the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>CGPA Calculation</title></head>");
        out.println("<body>");
        out.println("<h2>Enter Your Details to Calculate CGPA</h2>");
        out.println("<form action='calculateCGPA' method='POST'>");
        out.println("USN: <input type='text' name='usn' required><br>");
        out.println("Name: <input type='text' name='name' required><br>");
        out.println("SGPA of Previous Semester: <input type='number' name='sgpa' step='0.01' required><br>");
        out.println("<input type='submit' value='Calculate CGPA'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    // doPost method to process the form data and calculate the CGPA
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the form data
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        double sgpa = Double.parseDouble(request.getParameter("sgpa"));

        // For simplicity, assume that the CGPA calculation is based on the average of SGPA.
        // For a more complex calculation, you could add more semester data or weightage, etc.
        
        // Assuming CGPA is calculated as the average of multiple SGPA values. 
        // If it's the first semester, it is just the SGPA.
        double cgpa = sgpa; // In this case, CGPA is the same as SGPA for simplicity.

        // Display the results
        out.println("<html>");
        out.println("<head><title>CGPA Calculation Result</title></head>");
        out.println("<body>");
        out.println("<h2>CGPA Calculation Result</h2>");
        out.println("<p><b>USN:</b> " + usn + "</p>");
        out.println("<p><b>Name:</b> " + name + "</p>");
        out.println("<p><b>SGPA of Previous Semester:</b> " + sgpa + "</p>");
        out.println("<p><b>Calculated CGPA:</b> " + cgpa + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
