<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hóa đơn Thanh Toán</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(135deg, #e6f0fa 0%, #f8f9fa 100%);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 0;
    }
    .container {
      max-width: 700px;
      animation: fadeIn 0.6s ease-in-out;
    }
    .card {
      border: none;
      border-radius: 12px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      background: white;
    }
    .card:hover {
      transform: translateY(-8px);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
    }
    .card-header.success {
      background: linear-gradient(to right, #1e3a8a, #3b82f6);
      color: white;
    }
    .card-header.warning {
      background: linear-gradient(to right, #f59e0b, #facc15);
      color: white;
    }
    .card-header.error {
      background: linear-gradient(to right, #dc2626, #f87171);
      color: white;
    }
    .card-header {
      border-radius: 12px 12px 0 0;
      padding: 1.5rem;
      text-align: center;
    }
    .card-body {
      padding: 2rem;
    }
    .card-body p {
      margin: 0.5rem 0;
      font-size: 1.1rem;
    }
    .btn-primary {
      background-color: #1e40af;
      border: none;
      border-radius: 8px;
      padding: 0.75rem 1.5rem;
      transition: all 0.3s ease;
      width: 100%;
    }
    .btn-primary:hover {
      background-color: #1e3a8a;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }
    .btn-warning {
      background-color: #f59e0b;
      border: none;
      border-radius: 8px;
      padding: 0.75rem 1.5rem;
      transition: all 0.3s ease;
      width: 100%;
    }
    .btn-warning:hover {
      background-color: #d97706;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }
    .status-icon {
      font-size: 3rem;
      margin: 0 auto 1rem;
      display: block;
      text-align: center;
    }
    .success-icon { color: #1e40af; }
    .warning-icon { color: #f59e0b; }
    .error-icon { color: #dc2626; }
    table {
      width: 100%;
      border-collapse: collapse;
      margin: 1.5rem 0;
    }
    th, td {
      padding: 12px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    .total-amount {
      text-align: right;
      font-size: 1.5rem;
      color: #dc2626;
      font-weight: bold;
    }
    .footer {
      text-align: center;
      font-style: italic;
      margin-top: 1.5rem;
      color: #6b7280;
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(15px); }
      to { opacity: 1; transform: translateY(0); }
    }
    @media print { .no-print { display: none; } }
  </style>
</head>
<body>
<div class="container">
  <div class="card">
    <c:choose>
      <c:when test="${not empty booking}">
        <div class="card-header ${booking.status == 'Đã trả' ? (not empty payment ? 'success' : 'warning') : 'error'}">
          <h1 class="fs-3 mb-0"><i class="fas fa-receipt me-2"></i> Hóa Đơn Thanh Toán</h1>
        </div>
        <div class="card-body">
          <i class="fas fa-receipt status-icon ${booking.status == 'Đã trả' ? (not empty payment ? 'success-icon' : 'warning-icon') : 'warning-icon'}"></i>
          <p><strong><i class="fas fa-hotel me-2"></i>Khách sạn:</strong> KHÁCH SẠN HÀ NỘI</p>
          <p><strong><i class="fas fa-map-marker-alt me-2"></i>Địa chỉ:</strong> 123 Đường Láng, Đống Đa, Hà Nội</p>
          <p><strong><i class="fas fa-receipt me-2"></i>Mã hóa đơn:</strong> ${booking.id}</p>
          <p><strong><i class="fas fa-clock me-2"></i>Thời gian lập:</strong>
            <fmt:formatDate value="${now}" pattern="dd/MM/yyyy HH:mm:ss" />
          </p>
          <p><strong><i class="fas fa-user me-2"></i>Khách hàng:</strong> ${booking.customer.name}</p>
          <p><strong><i class="fas fa-id-card me-2"></i>CMND/CCCD:</strong> ${booking.customer.identityNumber}</p>
          <p><strong><i class="fas fa-phone me-2"></i>SĐT:</strong> ${booking.customer.phone}</p>
          <p><strong><i class="fas fa-info-circle me-2"></i>Trạng thái:</strong>
              ${booking.status == 'Đã trả' ? (not empty payment ? 'Đã thanh toán' : 'Đã trả nhưng thiếu thông tin thanh toán') : 'Chưa thanh toán'}
          </p>
          <c:if test="${booking.status == 'Đã trả' && not empty payment}">
            <p><strong><i class="fas fa-receipt me-2"></i>Mã thanh toán:</strong> ${payment.id}</p>
            <p><strong><i class="fas fa-calendar-alt me-2"></i>Ngày thanh toán:</strong>
              <fmt:formatDate value="${payment.paymentDate}" pattern="dd/MM/yyyy" />
            </p>
            <p><strong><i class="fas fa-credit-card me-2"></i>Phương thức thanh toán:</strong> ${payment.method}</p>
          </c:if>
          <table>
            <tr><th>Mục</th><th>Chi tiết</th></tr>
            <tr><td>Phòng</td><td>${booking.room.roomNumber} (${booking.room.type})</td></tr>
            <tr><td>Ngày nhận</td><td><fmt:formatDate value="${booking.checkinDate}" pattern="dd/MM/yyyy" /></td></tr>
            <tr><td>Ngày trả</td><td><fmt:formatDate value="${booking.checkoutDate}" pattern="dd/MM/yyyy" /></td></tr>
            <tr><td class="total-amount">Tổng tiền</td><td class="total-amount">
              <fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true" maxFractionDigits="0" /> VND
            </td></tr>
          </table>
          <div class="footer">Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi! 🌟</div>
          <div class="text-center no-print">
            <c:if test="${booking.status != 'Đã trả'}">
              <a href="${pageContext.request.contextPath}/checkOut?bookingId=${booking.id}" class="btn btn-warning mt-3">
                <i class="fas fa-credit-card me-2"></i>Thanh toán ngay
              </a>
            </c:if>
            <c:if test="${booking.status == 'Đã trả' && not empty payment}">
              <button class="btn btn-primary mt-3" onclick="window.print()">
                <i class="fas fa-print me-2"></i>In hóa đơn
              </button>
              <a href="${pageContext.request.contextPath}/invoice?bookingId=${booking.id}&action=download" class="btn btn-primary mt-3 ms-2">
                <i class="fas fa-download me-2"></i>Tải PDF
              </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3 ms-2">
              <i class="fas fa-arrow-left me-2"></i>Quay lại Trang chủ
            </a>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <div class="card-header error">
          <h1 class="fs-3 mb-0"><i class="fas fa-exclamation-triangle me-2"></i> Lỗi Hóa Đơn ⚠️</h1>
        </div>
        <div class="card-body">
          <i class="fas fa-exclamation-triangle status-icon error-icon"></i>
          <p><strong>Thông báo:</strong> ${errorMessage}</p>
          <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3">
            <i class="fas fa-arrow-left me-2"></i>Quay lại Trang chủ
          </a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.6.0/dist/confetti.browser.min.js"></script>
<script>
  <c:if test="${booking.status == 'Đã trả' && not empty payment}">
  confetti({
    particleCount: 100,
    spread: 70,
    origin: { y: 0.6 }
  });
  </c:if>
</script>
</body>
</html>