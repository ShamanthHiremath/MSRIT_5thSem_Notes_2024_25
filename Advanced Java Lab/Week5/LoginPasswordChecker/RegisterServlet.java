package logincheck;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Basic validation for username and password
        if (username == null || username.isEmpty()) {
            out.println("<html><body><h1>Username is required!</h1></body></html>");
            return;
        }
        
        if (password.length() < 8) {
            out.println("<html><body><h1>Password must be at least 8 characters long!</h1></body></html>");
            return;
        }
        
        // Check if the password and repassword match
        if (!password.equals(repassword)) {
            response.sendRedirect("LoginPassword.html");  // Redirect back to the form if passwords don't match
            return;
        }
        
        // If everything is valid, greet the user
        out.println("<html><body>");
        out.println("<h1>Welcome, " + username + "!</h1>");
        out.println("<p>You have successfully registered.</p>");
        out.println("</body></html>");
    }
}
