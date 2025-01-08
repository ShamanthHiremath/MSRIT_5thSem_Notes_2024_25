import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class CookieServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Create 4 cookies
        Cookie cookie1 = new Cookie("cookie1", "value1");
        Cookie cookie2 = new Cookie("cookie2", "value2");
        Cookie cookie3 = new Cookie("cookie3", "value3");
        Cookie cookie4 = new Cookie("cookie4", "value4");

        // Set cookies 1 and 2 to expire in 1 minute
        cookie1.setMaxAge(60); // 60 seconds = 1 minute
        cookie2.setMaxAge(60); // 60 seconds = 1 minute

        // Set cookies 3 and 4 to last until the browser session ends (no expiration)
        cookie3.setMaxAge(-1); // Session cookie
        cookie4.setMaxAge(-1); // Session cookie

        // Add cookies to the response
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        // Retrieve all cookies
        Cookie[] cookies = request.getCookies();

        // Display cookies
        out.println("<html>");
        out.println("<head><title>Cookie Example</title></head>");
        out.println("<body>");
        out.println("<h2>Displaying Cookies</h2>");

        if (cookies != null) {
            out.println("<h3>All Cookies:</h3>");
            for (Cookie cookie : cookies) {
                out.println("<p>" + cookie.getName() + ": " + cookie.getValue() + "</p>");
            }

            // Display message based on expiry
            out.println("<h3>Refresh the page to see the remaining cookies (cookies 1 and 2 will expire after 1 minute).</h3>");
        } else {
            out.println("<p>No cookies found!</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
