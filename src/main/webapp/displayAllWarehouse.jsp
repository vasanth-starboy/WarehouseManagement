<%@ page import="java.util.*, com.wipro.bean.WarehouseBean" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Warehouse Records</title>
</head>
<body>

<h2>All Warehouse Records</h2>

<%
List<WarehouseBean> list = (List<WarehouseBean>) request.getAttribute("warehouseList");

if (list == null || list.isEmpty()) {
%>
    <h3>No records available!</h3>
<%
} else {
    for (WarehouseBean w : list) {
%>
    <p>
        <strong>Record ID:</strong> <%= w.getRecordId() %><br>
        <strong>Item Name:</strong> <%= w.getItemName() %><br>
        <strong>Location:</strong> <%= w.getLocation() %><br>
        <strong>Date:</strong> <%= w.getReceivedDate() %><br>
        <strong>Quantity:</strong> <%= w.getQuantity() %><br>
        <strong>Status:</strong> <%= w.getStatus() %><br>
        <strong>Remarks:</strong> <%= w.getRemarks() %>
    </p>
    <hr>
<%
    }
}
%>

<br>
<a href="menu.html">Back to Menu</a>

</body>
</html>
