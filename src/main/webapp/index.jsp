<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phần mềm Quản lý Khách sạn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #e6f0fa 0%, #f8f9fa 100%);
            margin: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        header {
            background: linear-gradient(to right, #1e3a8a, #3b82f6);
            color: white;
            padding: 2rem;
            text-align: center;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        .nav-tabs {
            background-color: #1f2937;
            border-radius: 8px;
            padding: 0.5rem;
            display: flex;
            justify-content: space-around;
        }
        .nav-tabs .nav-item {
            margin: 0 1.2rem;
        }
        .nav-tabs .nav-link {
            color: #d1d5db;
            border-radius: 6px;
            padding: 1rem 1.5rem;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .nav-tabs .nav-link:hover, .nav-tabs .nav-link.active {
            background-color: #3b82f6;
            color: white;
            transform: scale(1.05);
        }
        .tab-content {
            flex: 1;
            animation: fadeIn 0.6s ease-in-out;
        }
        .tab {
            display: none;
            height: auto;
        }
        .tab.active {
            display: block;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: translateY(-8px);
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
        }
        .table {
            background-color: white;
            border-radius: 8px;
            max-height: 50vh;
            overflow-y: auto;
        }
        .table th {
            background-color: #e9ecef;
            font-weight: 600;
            color: #1f2937;
        }
        .table tr {
            transition: background-color 0.3s ease;
        }
        .table tr:hover {
            background-color: #f1f5f9;
        }
        .btn-primary {
            background-color: #1e40af;
            border: none;
            border-radius: 8px;
            padding: 0.75rem 1.5rem;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #1e3a8a;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }
        .form-control, .form-select {
            border-radius: 8px;
            transition: all 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 0.2rem rgba(59, 130, 246, 0.3);
        }
        .invoice-link {
            color: #1e40af;
            font-weight: 500;
            transition: color 0.3s ease;
        }
        .invoice-link:hover {
            color: #3b82f6;
            text-decoration: underline;
        }
    </style>
</head>
<body>
<header>
    <h1 class="display-4 fw-bold"><i class="fas fa-hotel me-2"></i> Phần mềm Quản lý Khách sạn</h1>
</header>
<nav class="container mt-4">
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#phong" onclick="showTab('phong')"><i class="fas fa-door-open me-2"></i> Quản lý Phòng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#datphong" onclick="showTab('datphong')"><i class="fas fa-calendar-check me-2"></i> Đặt Phòng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#traphong" onclick="showTab('traphong')"><i class="fas fa-sign-out-alt me-2"></i> Trả Phòng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#khachhang" onclick="showTab('khachhang')"><i class="fas fa-users me-2"></i> Quản lý Khách hàng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#hoadon" onclick="showTab('hoadon')"><i class="fas fa-file-invoice me-2"></i> Hóa đơn</a>
        </li>
    </ul>
</nav>

<div class="container py-5 tab-content">
    <!-- Quản lý Khách hàng -->
    <div class="tab" id="khachhang">
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title mb-4"><i class="fas fa-users me-2"></i> Quản lý Khách hàng</h2>
                <form action="searchCustomer" method="get" class="d-flex mb-4">
                    <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập tên, số điện thoại..." required />
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search me-2"></i> Tìm kiếm</button>
                </form>
                <h3 class="h5 mb-3">Danh sách Khách hàng</h3>
                <div class="table-responsive">
                    <table class="table table-hover">
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
            </div>
        </div>
    </div>

    <!-- Hóa đơn -->
    <div class="tab" id="hoadon">
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title mb-4"><i class="fas fa-file-invoice me-2"></i> Hóa đơn Thanh toán</h2>
                <div class="table-responsive">
                    <table class="table table-hover">
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
                                <td><a href="/HotelManagement_war/invoice?bookingId=${invoice.id}" class="invoice-link"><i class="fas fa-file-alt me-2"></i> Xem Hóa Đơn</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Quản lý Phòng -->
    <div class="tab active" id="phong">
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title mb-4"><i class="fas fa-door-open me-2"></i> Danh sách Phòng</h2>
                <div class="table-responsive">
                    <table class="table table-hover">
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
            </div>
        </div>
    </div>

    <!-- Đặt Phòng -->
    <div class="tab" id="datphong">
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title mb-4"><i class="fas fa-calendar-check me-2"></i> Đặt Phòng</h2>
                <form id="bookingForm" action="${pageContext.request.contextPath}/bookRoom" method="post" onsubmit="return validateBookingForm()">
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-phone me-2"></i> Số điện thoại khách hàng:</label>
                        <input type="text" name="phone" id="phoneInput" class="form-control" placeholder="Nhập 10 số, bắt đầu bằng 0" required onblur="fetchCustomerInfo(this.value)" />
                    </div>
                    <div id="customerInfo" class="mb-3">
                        <label class="form-label"><i class="fas fa-user me-2"></i> Họ tên khách:</label>
                        <input type="text" name="customerName" id="customerNameInput" class="form-control" placeholder="Nhập họ tên" />
                        <label class="form-label mt-3"><i class="fas fa-id-card me-2"></i> CCCD:</label>
                        <input type="text" name="identityNumber" id="identityNumberInput" class="form-control" placeholder="Nhập số CCCD" />
                    </div>
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-door-open me-2"></i> Số phòng:</label>
                        <select name="roomId" class="form-select" required>
                            <option value="">Chọn phòng</option>
                            <c:forEach var="room" items="${availableRooms}">
                                <option value="${room.id}">${room.roomNumber} (${room.type})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-calendar-alt me-2"></i> Ngày đặt:</label>
                        <input type="date" name="checkInDate" class="form-control" required />
                    </div>
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-calendar-alt me-2"></i> Ngày trả:</label>
                        <input type="date" name="checkOutDate" class="form-control" required />
                    </div>
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-save me-2"></i> Lưu thông tin</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Trả Phòng -->
    <div class="tab" id="traphong">
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title mb-4"><i class="fas fa-sign-out-alt me-2"></i> Trả Phòng</h2>
                <form action="${pageContext.request.contextPath}/checkOut" method="post" onsubmit="return validateCheckOutForm()">
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-ticket-alt me-2"></i> Mã đặt phòng:</label>
                        <select name="bookingId" class="form-select" required>
                            <option value="">Chọn mã đặt phòng</option>
                            <c:forEach var="booking" items="${activeBookings}">
                                <option value="${booking.id}">Mã: ${booking.id} - Phòng: ${booking.room.roomNumber} - Khách: ${booking.customer.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-credit-card me-2"></i> Phương thức thanh toán:</label>
                        <select name="paymentMethod" class="form-select" required>
                            <option value="Tiền mặt">Tiền mặt</option>
                            <option value="Chuyển khoản">Chuyển khoản</option>
                            <option value="Thẻ">Thẻ</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-check me-2"></i> Trả phòng</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
    function showTab(tabId) {
        const tabs = document.querySelectorAll('.tab');
        tabs.forEach(tab => tab.classList.remove('active'));
        document.getElementById(tabId).classList.add('active');
    }

    function validateBookingForm() {
        const phone = document.querySelector('#phoneInput').value;
        const checkInDate = new Date(document.querySelector('[name="checkInDate"]').value);
        const checkOutDate = new Date(document.querySelector('[name="checkOutDate"]').value);

        if (!/^(0[0-9]{9})$/.test(phone)) {
            alert('Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!');
            return false;
        }
        if (checkInDate >= checkOutDate) {
            alert('Ngày trả phòng phải sau ngày nhận phòng!');
            return false;
        }
        return true;
    }

    function validateCheckOutForm() {
        const bookingId = document.querySelector('[name="bookingId"]').value;
        if (!bookingId) {
            alert('Vui lòng chọn mã đặt phòng!');
            return false;
        }
        return true;
    }

    function fetchCustomerInfo(phone) {
        if (!/^(0[0-9]{9})$/.test(phone)) {
            console.log("Số điện thoại không hợp lệ:", phone);
            document.querySelector('#customerNameInput').value = "";
            document.querySelector('#identityNumberInput').value = "";
            document.querySelector('#customerNameInput').removeAttribute("readonly");
            document.querySelector('#identityNumberInput').removeAttribute("readonly");
            return;
        }

        console.log(`Fetching customer info for phone: ${phone}`);
        fetch(`/HotelManagement_war/getCustomerByPhone?phone=` + phone)
            .then(response => {
                console.log(`Response status: ${response.status}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("Dữ liệu từ API:", data);
                const customerNameInput = document.querySelector('#customerNameInput');
                const identityNumberInput = document.querySelector('#identityNumberInput');
                if (data && data.name && data.identityNumber) {
                    customerNameInput.value = data.name;
                    identityNumberInput.value = data.identityNumber;
                    customerNameInput.setAttribute("readonly", true);
                    identityNumberInput.setAttribute("readonly", true);
                } else {
                    customerNameInput.value = "";
                    identityNumberInput.value = "";
                    customerNameInput.removeAttribute("readonly");
                    identityNumberInput.removeAttribute("readonly");
                }
            })
            .catch(error => {
                console.error("Lỗi khi lấy thông tin khách hàng:", error);
            });
    }
</script>
</body>
</html>