package mypack;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class visitortracking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type
        response.setContentType("text/html");

        // Get the PrintWriter to send data to the browser
        PrintWriter out = response.getWriter();

        // Get the session object
        HttpSession session = request.getSession(true);

        // Check if a visit count attribute exists in the session
        Integer visitCount = (Integer) session.getAttribute("visitCount");

        if (visitCount == null) {
            // If the user is visiting for the first time, set the visit count to 1
            visitCount = 1;
            session.setAttribute("visitCount", visitCount); // Store visit count in session
            out.println("<html><body>");
            out.println("<h2>Welcome to the website!</h2>");
            out.println("<p>This is your first visit.</p>");
        } else {
            // If the user has visited before, increment the visit count
            visitCount++;
            session.setAttribute("visitCount", visitCount); // Update visit count in session
            out.println("<html><body>");
            out.println("<h2>Welcome back!</h2>");
            out.println("<p>This is your visit number: " + visitCount + "</p>");
        }

        out.println("</body></html>");
    }
}
