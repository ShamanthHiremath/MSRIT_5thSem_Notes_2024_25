import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class VoterEligibilityServlet extends HttpServlet {

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
            int age = today.get(Calendar.YEAR) - dob.getYear() - 1900; //Date class counts years starting from 1900. For example, if you create a Date object for the year 2000, it internally represents the year as 100.

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
