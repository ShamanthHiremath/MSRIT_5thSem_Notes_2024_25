<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head><title>Login</title></head>
<body>
    <h2>Login Page</h2>
    <form method="post">
        Username: <input type="text" name="username"><br><br>
        Password: <input type="password" name="password"><br><br>
        <input type="submit" value="Login">
    </form>
    
    <% 
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            if (!username.equals("Admin") || !password.equals("Admin123")) {
                // Throw an exception if credentials don't match
                <!-- throw new Exception("Invalid username or password!"); -->

                response.sendRedirect("register.html");
            } else {
                out.println("<h3>Welcome, " + username + "!</h3>");

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
            }
        }
        else{
            response.sendRedirect("register.html");
        }
    %>
</body>
</html>