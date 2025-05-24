<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm khách hàng</title>
    <style>body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f8f9fa;
    }
    .container {
        max-width: 800px;
        margin: auto;
        padding: 20px;
        background: white;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
    }
    h1 {
        text-align: center;
        color: #007bff;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background-color: #ffffff;
    }
    th, td {
        border: 1px solid #dee2e6;
        padding: 12px;
        text-align: center;
    }
    th {
        background-color: #007bff;
        color: white;
    }
    tr:nth-child(even) {
        background-color: #f2f2f2;
    }
    p {
        text-align: center;
        font-size: 18px;
        color: #dc3545;
    }
    .btn {
        display: block;
        width: 200px;
        margin: 20px auto;
        padding: 10px;
        background-color: #007bff;
        color: white;
        text-align: center;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        text-decoration: none;
        font-size: 16px;
    }
    .btn:hover {
        background-color: #0056b3;
    }
    </style>
</head>
<body>
<h1>Kết quả tìm kiếm khách hàng</h1>

<%
    //6.	Nếu tìm thấy khách hàng khớp với từ khóa
    //      hiển thị trên trang searchCustomer.jsp.
    // Lấy danh sách khách hàng từ request attribute
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    // Kiểm tra xem danh sách có dữ liệu không
    if (customers != null && !customers.isEmpty()) {
%>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Họ tên</th>
        <th>SĐT</th>
        <th>CCCD</th>
    </tr>
    </thead>
    <tbody>
    <% for (Customer customer : customers) { %>
    <tr>
        <td><%= customer.getId() %></td>
        <td><%= customer.getName() %></td>
        <td><%= customer.getPhone() %></td>
        <td><%= customer.getIdentityNumber() %></td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } else { %>
<!-- 7.	Nếu không tìm thấy ,thông báo Không tìm thấy khách hàng nào -->
<p>Không tìm thấy khách hàng nào.</p>
<% } %>
<a href="index.jsp" class="btn">Quay lại</a>
</body>
</html>