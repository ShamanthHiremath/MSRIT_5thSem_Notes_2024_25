<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Get Cookies</title>
</head>
<body>
    <h1>Getting Cookies</h1>
    <%
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            out.println("<table border='1'><tr><th>Name</th><th>Value</th></tr>");
            for (Cookie cookie : cookies) {
                out.println("<tr><td>" + cookie.getName() + "</td><td>" + cookie.getValue() + "</td></tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>No cookies found.</p>");
        }
    %>
    <a href="index.html">Back to Home</a>
</body>
</html>