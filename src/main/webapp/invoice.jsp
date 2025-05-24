<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.BookingModel" %>
<%@ page import="model.CustomerModel" %>
<%@ page import="model.RoomModel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
  BookingModel booking = (BookingModel) request.getAttribute("booking");
  CustomerModel customer = booking.getCustomer();
  RoomModel room = booking.getRoom();
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Hóa Đơn Thanh Toán</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f4f6f8;
      margin: 0;
      padding: 0;
    }

    .container {
      max-width: 700px;
      margin: 50px auto;
      background: white;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }

    h1 {
      text-align: center;
      color: #007bff;
      margin-bottom: 30px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 25px;
    }

    th, td {
      padding: 10px 15px;
      text-align: left;
    }

    th {
      background-color: #007bff;
      color: white;
    }

    tr:nth-child(even) {
      background-color: #f1f1f1;
    }

    .total {
      font-weight: bold;
      color: #e74c3c;
      font-size: 1.2rem;
      text-align: right;
    }

    .footer {
      text-align: center;
      margin-top: 30px;
      color: gray;
      font-size: 0.9rem;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>HÓA ĐƠN THANH TOÁN</h1>

  <table>
    <tr>
      <th>Mã Đặt Phòng</th>
      <td><%= booking.getId() %></td>
    </tr>
    <tr>
      <th>Khách hàng</th>
      <td><%= customer.getName() %> (CMND: <%= customer.getIdentityNumber() %>, SĐT: <%= customer.getPhone() %>)</td>
    </tr>
    <tr>
      <th>Số Phòng</th>
      <td><%= room.getRoomNumber() %> - Loại: <%= room.getType() %></td>
    </tr>
    <tr>
      <th>Ngày Nhận Phòng</th>
      <td><%= sdf.format(booking.getCheckinDate()) %></td>
    </tr>
    <tr>
      <th>Ngày Trả Phòng</th>
      <td><%= sdf.format(booking.getCheckoutDate()) %></td>
    </tr>
    <tr>
      <th>Tổng Tiền</th>
      <td class="total"><%= String.format("%,.0f VND", booking.getTotalPrice()) %></td>
    </tr>
  </table>

  <div class="footer">
    Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!<br>
    Chúc quý khách một ngày tốt lành.
  </div>
</div>
<script>
  window.onload = function () {
    alert("In hóa đơn thành công!");
  }
</script>
</body>
</html>
