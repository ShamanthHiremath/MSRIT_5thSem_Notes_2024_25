package mypack;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class cookiesss extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Create cookies
        Cookie cookie1 = new Cookie("cookie1", "value1");
        Cookie cookie2 = new Cookie("cookie2", "value2");
        Cookie cookie3 = new Cookie("cookie3", "value3");
        Cookie cookie4 = new Cookie("cookie4", "value4");

        // Set expiry date for cookie1 and cookie2 to 1 minute
        cookie1.setMaxAge(60);  // 60 seconds = 1 minute
        cookie2.setMaxAge(60);

        // Set the cookies in the response
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        // Retrieve all cookies
        Cookie[] cookies = request.getCookies();

        // Display cookies
        out.println("<html>");
        out.println("<head><title>Cookies Example</title></head>");
        out.println("<body>");
        out.println("<h2>Displaying Cookies</h2>");

        if (cookies != null) {
            out.println("<h3>All Cookies:</h3>");
            for (Cookie cookie : cookies) {
                out.println("<p>" + cookie.getName() + ": " + cookie.getValue() + "</p>");
            }

            // Display message based on expiry
            out.println("<h3>Refresh the page to see the remaining cookies (after 1 minute, cookies 1 and 2 will expire).</h3>");
        } else {
            out.println("<p>No cookies found!</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
