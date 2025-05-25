<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Thanh toán</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
  <style>
    body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #e6f0fa 0%, #f8f9fa 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; }
    .container { max-width: 500px; }
    .card { border: none; border-radius: 12px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); }
    .card-header { background: linear-gradient(to right, #1e3a8a, #3b82f6); color: white; border-radius: 12px 12px 0 0; text-align: center; }
    .btn-primary { background-color: #1e40af; border: none; }
    .btn-primary:hover { background-color: #1e3a8a; }
    .btn-secondary { background-color: #6b7280; border: none; }
    .btn-secondary:hover { background-color: #4b5563; }
  </style>
</head>
<body>
<div class="container mt-5">
  <div class="card">
    <div class="card-header">
      <h1 class="fs-3 mb-0"><i class="fas fa-credit-card me-2"></i>Thanh toán đặt phòng #${bookingId}</h1>
    </div>
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/checkout" method="post">
        <input type="hidden" name="bookingId" value="${bookingId}">
        <div class="mb-3">
          <label class="form-label">Phương thức thanh toán</label>
          <select name="paymentMethod" class="form-select" required>
            <option value="Tiền mặt">Tiền mặt</option>
            <option value="Chuyển khoản">Chuyển khoản</option>
            <option value="Thẻ">Thẻ</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary w-100"><i class="fas fa-credit-card me-2"></i>Thanh toán</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary w-100 mt-2">Hủy</a>
      </form>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>