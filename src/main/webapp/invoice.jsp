<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.BookingModel" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
  BookingModel booking = (BookingModel) request.getAttribute("booking");
  String message = (String) request.getAttribute("message");
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Hóa Đơn Đặt Phòng</title>
</head>
<body>
<h2 style="color: green;"><%= message != null ? message : "" %></h2>

<h3>Chi tiết hóa đơn:</h3>
<ul>
  <li><strong>Mã đặt phòng:</strong> <%= booking.getId() %></li>
  <li><strong>Tên khách hàng:</strong> <%= booking.getCustomer().getName() %></li>
  <li><strong>Số điện thoại:</strong> <%= booking.getCustomer().getPhone() %></li>
  <li><strong>CMND/CCCD:</strong> <%= booking.getCustomer().getIdentityNumber() %></li>
  <li><strong>Số phòng:</strong> <%= booking.getRoom().getRoomNumber() %></li>
  <li><strong>Loại phòng:</strong> <%= booking.getRoom().getType() %></li>
  <li><strong>Ngày nhận phòng:</strong> <%= sdf.format(booking.getCheckinDate()) %></li>
  <li><strong>Ngày trả phòng:</strong> <%= sdf.format(booking.getCheckoutDate()) %></li>
  <li><strong>Tổng tiền:</strong> <%= String.format("%,.0f", booking.getTotalPrice()) %> VND</li>
</ul>

<a href="index.jsp">Quay lại trang chính</a>
</body>
</html>

