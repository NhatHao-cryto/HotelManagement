<%@ page import="model.MyConnectDB" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Phần mềm Quản lý Khách sạn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f8f9fa;
        }
        header {
            background-color: #007bff;
            color: white;
            padding: 1rem;
            text-align: center;
        }
        nav {
            display: flex;
            background-color: #343a40;
        }
        nav button {
            flex: 1;
            padding: 1rem;
            background-color: #343a40;
            color: white;
            border: none;
            cursor: pointer;
        }
        nav button:hover {
            background-color: #495057;
        }
        .content {
            padding: 2rem;
        }
        .tab {
            display: none;
        }
        .tab.active {
            display: block;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        table, th, td {
            border: 1px solid #dee2e6;
        }
        th, td {
            padding: 0.5rem;
            text-align: left;
        }
        input, select {
            padding: 0.5rem;
            margin: 0.5rem 0;
            width: 100%;
        }
    </style>
</head>
<body>
<header>
    <h1>Phần mềm Quản lý Khách sạn</h1>
</header>
<nav>
    <button onclick="showTab('phong')">Quản lý Phòng</button>
    <button onclick="showTab('datphong')">Đặt/Trả Phòng</button>
    <button onclick="showTab('khachhang')">Quản lý Khách hàng</button>
    <button onclick="showTab('hoadon')">Hóa đơn</button>
</nav>

<div class="content">
    <!-- Quản lý Phòng -->
    <div id="phong" class="tab active">
        <h2>Danh sách Phòng</h2>
        <table>
            <thead>
            <tr>
                <th>Số phòng</th>
                <th>Loại phòng</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>101</td>
                <td>Đơn</td>
                <td>Trống</td>
            </tr>
            <tr>
                <td>102</td>
                <td>Đôi</td>
                <td>Đã đặt</td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- Đặt/Trả Phòng -->
    <div id="datphong" class="tab">
        <h2>Ghi nhận Đặt/Trả Phòng</h2>
        <form>
            <label>Chọn khách hàng:</label>
            <select>
                <option>Nguyễn Văn A</option>
                <option>Trần Thị B</option>
            </select>
            <label>Số phòng:</label>
            <input type="text" placeholder="VD: 101">
            <label>Ngày đặt:</label>
            <input type="date">
            <label>Ngày trả:</label>
            <input type="date">
            <button type="submit">Lưu thông tin</button>
        </form>
    </div>

    <!-- Quản lý Khách hàng -->
    <div id="khachhang" class="tab">
        <h2>Quản lý Khách hàng</h2>
        <!-- 4.4. Hệ thống gửi yêu cầu đến SearchCustomerServlet -->
        <form action="searchCustomer" method="get">
            <input type="text" name="keyword" placeholder="Nhập tên, số điện thoại..." required />
            <button type="submit">Tìm kiếm</button>
        </form>
        <h3>Danh sách Khách hàng</h3>

        <%
            MyConnectDB con= new MyConnectDB();
            ResultSet rs = con.corecttoDB("SELECT \n" +
                    "    c.id, c.name, c.phone, c.identityNumber, \n" +
                    "    r.roomNumber, b.checkin, b.checkout\n" +
                    "FROM \n" +
                    "    customer c\n" +
                    "JOIN \n" +
                    "    booking b ON b.customer_id = c.id\n" +
                    "JOIN \n" +
                    "    room r ON r.id = b.room_id\n" +
                    "LIMIT 0, 25;");
        %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Họ tên</th>
                <th>SĐT</th>
                <th>Cccd</th>
                <th>room</th>
                <th>checkin</th>
                <th>checkout</th>

            </tr>
            </thead>
            <tbody>
            <%
                while(rs.next()){
            %>

            <tr>
                <td><%=rs.getInt(1)%></td>
                <td><%=rs.getString(2)%></td>
                <td><%=rs.getString(3)%></td>
                <td><%=rs.getString(4)%></td>
                <td><%=rs.getString(5)%></td>
                <td><%=rs.getDate(6)%></td>
                <td><%=rs.getDate(7)%></td>
            </tr>


            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- In Hóa đơn -->
    <div id="hoadon" class="tab">
        <h2>In Hóa đơn Thanh toán</h2>
        <form>
            <label>Chọn khách hàng:</label>
            <select>
                <option>Nguyễn Văn A</option>
                <option>Trần Thị B</option>
            </select>
            <label>Chi tiết hóa đơn:</label>
            <textarea rows="5" style="width:100%;" placeholder="Chi tiết dịch vụ, số đêm, giá..."></textarea>
            <button type="submit">In Hóa đơn</button>
        </form>
    </div>
</div>

<script>
    function showTab(tabId) {
        const tabs = document.querySelectorAll('.tab');
        tabs.forEach(tab => tab.classList.remove('active'));
        document.getElementById(tabId).classList.add('active');
    }
</script>
</body>
</html>
