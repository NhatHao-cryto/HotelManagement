<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <form>
            <label>Họ tên:</label>
            <input type="text">
            <label>CMND/CCCD:</label>
            <input type="text">
            <label>Số điện thoại:</label>
            <input type="text">
            <button type="submit">Thêm Khách hàng</button>
        </form>
        <h3>Danh sách Khách hàng</h3>
        <table>
            <thead>
            <tr>
                <th>Họ tên</th>
                <th>CMND/CCCD</th>
                <th>SĐT</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Nguyễn Văn A</td>
                <td>123456789</td>
                <td>0901234567</td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- In Hóa đơn -->
    <div id="hoadon" class="tab">
        <h2>In Hóa đơn Thanh toán</h2>
        <form method="get" action="invoice" target="_blank">
            <label>Nhập mã đặt phòng:</label>
            <input type="text" name="bookingId" required>
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
