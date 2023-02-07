<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Edit</title>
    <style>
        .attrs {
            margin: 0 0 20px 0;
            overflow: hidden;
        }
        .attrs dt {
            margin: 0;
            padding: 5px 0;
            width: 150px;
            float: left;
            clear: both;
        }
        .attrs dd {
            margin: 0 0 0 150px;
            padding: 5px 0;
        }
    </style>
</head>
<body>
<%@include file="header.jsp" %>
<div>
    <h1>Edit meals</h1>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${requestScope.meal.id}">
        <dl class="attrs">
            <dt>Date Time:</dt>
            <dd><input type="datetime-local" value="${requestScope.meal.dateTime}" name="dateTime" required></dd>
            <dt>Description:</dt>
            <dd><input type="text" value="${requestScope.meal.description}" name="description" required></dd>
            <dt>Calories:</dt>
            <dd><input type="number" value="${requestScope.meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</div>
<%@include file="footer.jsp" %>

</body>
</html>
