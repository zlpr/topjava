<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
    <style>
        tr[excess="false"] {
            color: green;
        }
        tr[excess="true"] {
            color: red;
        }
        table {
            border-collapse: collapse;
            border: 1px solid black;
        }
        th,td {
            border: 1px solid black;
            padding: 10px 10px;
        }
    </style>
</head>
<body>
<%@include file="header.jsp" %>
<div>
    <h1>Meals</h1>
    <a href="${pageContext.request.contextPath}/edit">Add Meal</a>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var="meal" items="${requestScope.meals}">
            <tr excess="${meal.excess}">
                <td>${fn:replace(meal.dateTime,"T"," ")}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="${pageContext.request.contextPath}/edit?id=${meal.id}">Update</a></td>
                <td><a href="${pageContext.request.contextPath}/delete?id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

</div>
<%@include file="footer.jsp" %>
</body>
</html>
