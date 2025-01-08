import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class VisitCounterServlet extends HttpServlet {
    
    // Override the doGet method to handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set the response content type to HTML
        response.setContentType("text/html");
        
        // Get the PrintWriter object to write the response to the browser
        PrintWriter out = response.getWriter();
        
        // Get the HttpSession object. This will create a new session if it doesn't exist.
        HttpSession session = request.getSession(true); 
        
        // Get the visit count from the session object. Default is 0 if it doesn't exist.
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        
        if (visitCount == null) {
            // First-time visitor, initialize the visit count to 1
            visitCount = 1;
            session.setAttribute("visitCount", visitCount); // Store the count in the session
            out.println("<h2>Welcome! This is your first visit.</h2>");
        } else {
            // Not the first visit, increment the visit count
            visitCount++;
            session.setAttribute("visitCount", visitCount); // Update the count in the session
            out.println("<h2>Welcome back! You've visited this page " + visitCount + " times.</h2>");
        }
        
        // Optionally, you can also display the session ID for debugging or logging
        out.println("<p>Session ID: " + session.getId() + "</p>");
        
        // Close the PrintWriter stream
        out.close();
    }
}