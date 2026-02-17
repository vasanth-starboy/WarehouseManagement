<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>View Warehouse Record</title>
</head>
<body>

<h2>View Warehouse Record</h2>

<form action="MainServlet" method="post">

    <input type="hidden" name="operation" value="viewRecord">

    <table border="0">

        <tr>
            <td>Item Name:</td>
            <td>
                <input type="text" name="itemName" required>
            </td>
        </tr>

        <tr>
            <td>Received Date:</td>
            <td>
                <input type="date" name="receivedDate" required>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="center">
                <br>
                <input type="submit" value="View Record">
            </td>
        </tr>

    </table>

</form>

<br><br>

<a href="menu.html">Back to Menu</a>

</body>
</html>
