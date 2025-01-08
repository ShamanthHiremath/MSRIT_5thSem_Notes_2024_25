<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Ticket Pricing</title>
</head>
<body>

<%
    // Retrieve form data
    String name = request.getParameter("name");
    int age = Integer.parseInt(request.getParameter("age"));

    // Determine the ticket price based on the age
    String priceMessage;
    int ticketPrice;

    if (age > 62) {
        ticketPrice = 50;
        priceMessage = "You are eligible for a senior citizen discount.";
    } else if (age < 10) {
        ticketPrice = 30;
        priceMessage = "You are eligible for a children's discount.";
    } else {
        ticketPrice = 80;
        priceMessage = "Standard ticket price.";
    }
%>

<h2>Ticket Information</h2>

<p>Name: <%= name %></p>
<p>Age: <%= age %></p>
<p>Ticket Price: Rs. <%= ticketPrice %></p>
<p><%= priceMessage %></p>

</body>
</html>
