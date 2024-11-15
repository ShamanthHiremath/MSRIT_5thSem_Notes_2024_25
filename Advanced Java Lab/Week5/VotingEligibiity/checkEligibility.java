import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class VoterEligibilityServlet extends HttpServlet {

    // doGet method to display the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Voter Eligibility</title></head>");
        out.println("<body>");
        out.println("<h2>Check Voter Eligibility</h2>");
        out.println("<form action='checkEligibility' method='POST'>");
        out.println("First Name: <input type='text' name='firstName' required><br>");
        out.println("Last Name: <input type='text' name='lastName' required><br>");
        out.println("Email ID: <input type='email' name='email' required><br>");
        out.println("Date of Birth: <input type='date' name='dob' required><br>");
        out.println("<input type='submit' value='Check Eligibility'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    // doPost method to process the form data
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String dobString = request.getParameter("dob");

        try {
            // Parse the date of birth
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse(dobString);

            // Get the current date
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.getYear() - 1900;

            if (today.get(Calendar.MONTH) + 1 < dob.getMonth() + 1 || 
                (today.get(Calendar.MONTH) + 1 == dob.getMonth() + 1 && today.get(Calendar.DATE) < dob.getDate())) {
                age--;
            }

            // Check if the user is eligible to vote (18 or older)
            if (age >= 18) {
                out.println("<h3>Welcome " + firstName + " " + lastName + "</h3>");
                out.println("<p>You are eligible to vote.</p>");
                out.println("<p>Email: " + email + "</p>");
                out.println("<p>Date of Birth: " + dobString + "</p>");
            } else {
                out.println("<h3>Sorry, " + firstName + " " + lastName + "</h3>");
                out.println("<p>You are not eligible to vote. You must be at least 18 years old.</p>");
            }
        } catch (ParseException e) {
            out.println("<h3>Error: Invalid date format. Please enter a valid date of birth.</h3>");
        }
    }
}
