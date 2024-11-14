package logincheck;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Serve the form when the user first visits the page (GET request)
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Welcome to the Registration Page</h1>");
        response.getWriter().println("<form action='RegisterServlet' method='POST'>");
        response.getWriter().println("Username: <input type='text' name='username' required><br>");
        response.getWriter().println("Password: <input type='password' name='password' required><br>");
        response.getWriter().println("Re-enter Password: <input type='password' name='repassword' required><br>");
        response.getWriter().println("<input type='submit' value='Register'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        
        // Set response content type
        response.setContentType("text/html");
        
        // Basic validation for username and password
        if (username == null || username.isEmpty()) {
            response.getWriter().println("<html><body><h1>Username is required!</h1></body></html>");
            return;
        }
        
        if (password.length() < 8) {
            response.getWriter().println("<html><body><h1>Password must be at least 8 characters long!</h1></body></html>");
            return;
        }
        
        // Check if the password and repassword match
        if (!password.equals(repassword)) {
            response.sendRedirect("LoginPassword.html");  // Redirect back to the form if passwords don't match
            return;
        }
        
        // If everything is valid, greet the user
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Welcome, " + username + "!</h1>");
        response.getWriter().println("<p>You have successfully registered.</p>");
        response.getWriter().println("</body></html>");
    }
}
