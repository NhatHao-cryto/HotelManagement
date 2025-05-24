<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
    <button onclick="showTab('datphong')">Đặt Phòng</button>
    <button onclick="showTab('traphong')">Trả Phòng</button>
    <button onclick="showTab('khachhang')">Quản lý Khách hàng</button>
    <button onclick="showTab('hoadon')">Hóa đơn</button>
</nav>

<div class="content">
    <div id="khachhang" class="tab">
        <h2>Quản lý Khách hàng</h2>
        <form action="searchCustomer" method="get">
            <input type="text" name="keyword" placeholder="Nhập tên, số điện thoại..." required />
            <button type="submit">Tìm kiếm</button>
        </form>
        <h3>Danh sách Khách hàng</h3>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Họ tên</th>
                <th>SĐT</th>
                <th>CCCD</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.phone}</td>
                    <td>${customer.identityNumber}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="hoadon" class="tab">
        <h2>Hóa đơn Thanh toán</h2>
        <table>
            <thead>
            <tr>
                <th>Mã đặt phòng</th>
                <th>Khách hàng</th>
                <th>Phòng</th>
                <th>Ngày nhận</th>
                <th>Ngày trả</th>
                <th>Thành tiền</th>
                <th>Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="invoice" items="${activeBookings}">
                <tr>
                    <td>${invoice.id}</td>
                    <td>${invoice.customer.name}</td>
                    <td>${invoice.room.roomNumber}</td>
                    <td>${invoice.checkinDate}</td>
                    <td>${invoice.checkoutDate}</td>
                    <td>${invoice.totalPrice} VND</td>
                    <td><a href="/HotelManagement_war/invoice?bookingId=${invoice.id}">🧾 Xem Hóa Đơn</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
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
            <c:forEach var="room" items="${availableRooms}">
                <tr>
                    <td>${room.roomNumber}</td>
                    <td>${room.type}</td>
                    <td>${room.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Đặt Phòng -->
    <div id="datphong" class="tab">
        <h2>Đặt Phòng</h2>
        <form action="/HotelManagement_war/bookRoom" method="post" onsubmit="return validateBookingForm()">
            <label>Số điện thoại khách hàng:</label>
            <input type="text" name="phone" placeholder="Nhập 10 số, bắt đầu bằng 0" required onblur="fetchCustomerInfo(this.value)">
            <div id="customerInfo">
                <label>Họ tên khách:</label>
                <input type="text" name="customerName" placeholder="Nhập họ tên">
                <label>CCCD:</label>
                <input type="text" name="identityNumber" placeholder="Nhập số CCCD">
            </div>
            <label>Số phòng:</label>
            <select name="roomId" required>
                <option value="">Chọn phòng</option>
                <c:forEach var="room" items="${availableRooms}">
                    <option value="${room.id}">${room.roomNumber} (${room.type})</option>
                </c:forEach>
            </select>
            <label>Ngày đặt (yyyy-MM-dd):</label>
            <input type="date" name="checkInDate" required>
            <label>Ngày trả (yyyy-MM-dd):</label>
            <input type="date" name="checkOutDate" required>
            <button type="submit">Lưu thông tin</button>
        </form>
    </div>

    <!-- Trả Phòng -->
    <div id="traphong" class="tab">
        <h2>Trả Phòng</h2>
        <form action="/HotelManagement_war/checkOut" method="post" onsubmit="return validateCheckOutForm()">
            <label>Mã đặt phòng:</label>
            <select name="bookingId" required>
                <option value="">Chọn mã đặt phòng</option>
                <c:forEach var="booking" items="${activeBookings}">
                    <option value="${booking.id}">Mã: ${booking.id} - Phòng: ${booking.room.roomNumber} - Khách: ${booking.customer.name}</option>
                </c:forEach>
            </select>
            <label>Phương thức thanh toán:</label>
            <select name="paymentMethod" required>
                <option value="Tiền mặt">Tiền mặt</option>
                <option value="Chuyển khoản">Chuyển khoản</option>
                <option value="Thẻ">Thẻ</option>
            </select>
            <button type="submit">Trả phòng</button>
        </form>
    </div>
</div>
<c:forEach var="booking" items="${bookings}">
    <a href="/HotelManagement_war/invoice?bookingId=${booking.id}">🧾 Xem Hóa Đơn</a>
</c:forEach>
<script>
    function fetchCustomerInfo(phone) {
        if (!/^(0[0-9]{9})$/.test(phone)) {
            alert("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!");
            return;
        }

        fetch(`/HotelManagement_war/getCustomerByPhone?phone=` + phone)
            .then(response => response.json())
            .then(data => {
                console.log("Dữ liệu từ API:", data); // Kiểm tra dữ liệu API
                if (data && data.name && data.identityNumber) {
                    document.querySelector('[name="customerName"]').value = data.name;
                    document.querySelector('[name="identityNumber"]').value = data.identityNumber;
                    document.querySelector('[name="customerName"]').setAttribute("readonly", true);
                    document.querySelector('[name="identityNumber"]').setAttribute("readonly", true);
                } else {
                    document.querySelector('[name="customerName"]').value = "";
                    document.querySelector('[name="identityNumber"]').value = "";
                    document.querySelector('[name="customerName"]').removeAttribute("readonly");
                    document.querySelector('[name="identityNumber"]').removeAttribute("readonly");
                }
            })
            .catch(error => console.error("Lỗi khi lấy thông tin khách hàng:", error));
    }
    function showTab(tabId) {
        const tabs = document.querySelectorAll('.tab');
        tabs.forEach(tab => tab.classList.remove('active'));
        document.getElementById(tabId).classList.add('active');
    }
</script>
</body>
</html>