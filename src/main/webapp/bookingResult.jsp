<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Kết quả Đặt Phòng</title>
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
      max-width: 600px;
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
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(15px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
</head>
<body>
<div class="container">
  <div class="card">
    <c:choose>
      <c:when test="${not empty booking}">
        <div class="card-header success">
          <h1 class="fs-3 mb-0"><i class="fas fa-check-circle me-2"></i> Đặt Phòng Thành Công! 🎉</h1>
        </div>
        <div class="card-body">
          <p><strong><i class="fas fa-ticket-alt me-2"></i> Mã đặt phòng:</strong> ${booking.id}</p>
          <p><strong><i class="fas fa-user me-2"></i> Khách hàng:</strong> ${booking.customer.name}</p>
          <p><strong><i class="fas fa-door-open me-2"></i> Phòng:</strong> ${booking.room.roomNumber} (${booking.room.type})</p>
          <p><strong><i class="fas fa-calendar-alt me-2"></i> Ngày nhận:</strong>
            <fmt:formatDate value="${booking.checkinDate}" pattern="dd/MM/yyyy" />
          </p>
          <p><strong><i class="fas fa-calendar-alt me-2"></i> Ngày trả:</strong>
            <fmt:formatDate value="${booking.checkoutDate}" pattern="dd/MM/yyyy" />
          </p>
          <p><strong><i class="fas fa-money-bill me-2"></i> Tổng tiền:</strong>
            <fmt:formatNumber value="${booking.totalPrice}" type="number" groupingUsed="true" maxFractionDigits="0" /> VND
          </p>
          <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3"><i class="fas fa-arrow-left me-2"></i> Quay lại Trang chủ</a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="card-header error">
          <h1 class="fs-3 mb-0"><i class="fas fa-exclamation-triangle me-2"></i> Lỗi Đặt Phòng ⚠️</h1>
        </div>
        <div class="card-body">
          <p><strong>Thông báo:</strong> ${errorMessage}</p>
          <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3"><i class="fas fa-arrow-left me-2"></i> Quay lại Trang chủ</a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>