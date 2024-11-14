package webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class DisplayName
 */
public class DisplayName extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the name parameter from the request
        String name = request.getParameter("name");

        // Set the content type to text/html
        response.setContentType("text/html");

        // Write the response
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Hello, " + name + "!</h1>");
        response.getWriter().println("</body></html>");
    }
}