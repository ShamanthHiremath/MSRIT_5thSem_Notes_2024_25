package mypack;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class Cookies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Create cookies with different attributes
        Cookie cookie1 = new Cookie("cookie1", "value1");
        Cookie cookie2 = new Cookie("cookie2", "value2");
        Cookie cookie3 = new Cookie("cookie3", "value3");

        // Set different expiration times for the cookies
        cookie1.setMaxAge(60); // 60 seconds = 1 minute (cookie1 will expire in 1 minute)
        cookie2.setMaxAge(120); // 120 seconds = 2 minutes (cookie2 will expire in 2 minutes)
        cookie3.setMaxAge(3600); // 3600 seconds = 1 hour (cookie3 will expire in 1 hour)

        // Set paths for cookies (cookie1 will be available for "/path1", cookie2 for "/path2", and cookie3 for "/")
        cookie1.setPath("/path1");
        cookie2.setPath("/path2");
        cookie3.setPath("/");

        // Set cookie's domain (optional) (for now we leave it as default)
        // cookie1.setDomain("yourdomain.com");

        // Set Secure flag (optional)
        // cookie2.setSecure(true);

        // Set HttpOnly flag (optional)
        // cookie3.setHttpOnly(true);

        // Add cookies to the response
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);

        // Retrieve all cookies from the request
        Cookie[] cookies = request.getCookies();

        // Display cookies
        out.println("<html>");
        out.println("<head><title>Cookies Example</title></head>");
        out.println("<body>");
        out.println("<h2>Displaying Cookies</h2>");

        if (cookies != null) {
            out.println("<h3>All Cookies:</h3>");
            for (Cookie cookie : cookies) {
                out.println("<p>Name: " + cookie.getName() + "<br>");
                out.println("Value: " + cookie.getValue() + "<br>");
                out.println("Domain: " + cookie.getDomain() + "<br>");
                out.println("Path: " + cookie.getPath() + "<br>");
                out.println("Max Age: " + cookie.getMaxAge() + " seconds</p>");
                out.println("<hr>");
            }
        } else {
            out.println("<p>No cookies found!</p>");
        }

        out.println("<h3>Refresh the page to see the remaining cookies (after 1 minute, cookie1 will expire).</h3>");
        out.println("</body>");
        out.println("</html>");
    }
}
