package mypack;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class Calcserv extends HttpServlet {

    // doPost method to handle form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get parameters from the form
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");
        String operator = request.getParameter("operator");

        try {
            // Parse the numbers
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);

            // Perform the calculation based on the selected operator
            double result = 0;
            String message = "";

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    message = "Result: " + num1 + " + " + num2 + " = " + result;
                    break;
                case "-":
                    result = num1 - num2;
                    message = "Result: " + num1 + " - " + num2 + " = " + result;
                    break;
                case "*":
                    result = num1 * num2;
                    message = "Result: " + num1 + " * " + num2 + " = " + result;
                    break;
                case "/":
                    if (num2 == 0) {
                        message = "Error: Cannot divide by zero!";
                    } else {
                        result = num1 / num2;
                        message = "Result: " + num1 + " / " + num2 + " = " + result;
                    }
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    message = "Result: " + num1 + " ^ " + num2 + " = " + result;
                    break;
                default:
                    message = "Invalid operator selected!";
            }


            if (operator.equals("+")) {
                result = num1 + num2;
                message = "Result: " + num1 + " + " + num2 + " = " + result;
            } else if (operator.equals("-")) {
                result = num1 - num2;
                message = "Result: " + num1 + " - " + num2 + " = " + result;
            } else if (operator.equals("*")) {
                result = num1 * num2;
                message = "Result: " + num1 + " * " + num2 + " = " + result;
            } else if (operator.equals("/")) {
                if (num2 == 0) {
                    message = "Error: Cannot divide by zero!";
                } else {
                    result = num1 / num2;
                    message = "Result: " + num1 + " / " + num2 + " = " + result;
                }
            } else if (operator.equals("^")) {
                result = Math.pow(num1, num2);
                message = "Result: " + num1 + " ^ " + num2 + " = " + result;
            } else {
                message = "Invalid operator selected!";
            }

            // Display the result or error message
            out.println("<html>");
            out.println("<head><title>Calculator Result</title></head>");
            out.println("<body>");
            out.println("<h3>" + message + "</h3>");
            out.println("</body>");
            out.println("</html>");
        } catch (NumberFormatException e) {
            // Handle invalid input
            out.println("<html>");
            out.println("<head><title>Calculator Error</title></head>");
            out.println("<body>");
            out.println("<h3>Error: Invalid number format!</h3>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
