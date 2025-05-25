package controller;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingModel;
import model.CustomerModel;
import model.PaymentModel;
import model.RoomModel;
import service.BookingService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class InvoiceServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        String action = request.getParameter("action");

        if (bookingIdStr != null && !bookingIdStr.isEmpty() && bookingIdStr.matches("\\d+")) {
            int bookingId = Integer.parseInt(bookingIdStr);
            BookingModel booking = null;
            PaymentModel payment = null;
            String errorMessage = null;

            try {
                booking = bookingService.getInvoiceByBookingId(bookingId);
                if (booking != null && "Đã trả".equals(booking.getStatus())) {
                    payment = bookingService.getPaymentByBookingId(bookingId);
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
            }

            if ("download".equals(action) && booking != null) {
                // Tạo PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=invoice_" + bookingId + ".pdf");

                try (PdfWriter writer = new PdfWriter(response.getOutputStream());
                     PdfDocument pdf = new PdfDocument(writer);
                     Document document = new Document(pdf)) {

                    // Sử dụng font Arial hỗ trợ tiếng Việt
                    PdfFont font = PdfFontFactory.createFont("fonts/arial.ttf", "Identity-H");
                    NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    boolean isPaid = booking != null && "Đã trả".equals(booking.getStatus());

                    // Tiêu đề
                    document.add(new Paragraph("HÓA ĐƠN THANH TOÁN")
                            .setFont(font)
                            .setFontSize(16)
                            .setBold());
                    document.add(new Paragraph("CÔNG NGHỆ PHẦN MỀM")
                            .setFont(font)
                            .setFontSize(14));
                    document.add(new Paragraph("KP22, Phường Linh Trung, TP. Thủ Đức, TP. Hồ Chí Min")
                            .setFont(font)
                            .setFontSize(12));

                    // Thông tin hóa đơn
                    document.add(new Paragraph("Mã hóa đơn: " + booking.getId())
                            .setFont(font));
                    document.add(new Paragraph("Thời gian lập: " + now.format(dateFormatter))
                            .setFont(font));
                    document.add(new Paragraph("Trạng thái: " + (isPaid ? (payment != null ? "Đã thanh toán" : "Đã trả nhưng thiếu thông tin thanh toán") : "Chưa thanh toán"))
                            .setFont(font));

                    // Thông tin khách hàng
                    CustomerModel c = booking.getCustomer();
                    document.add(new Paragraph("Khách hàng: " + c.getName())
                            .setFont(font));
                    document.add(new Paragraph("CMND/CCCD: " + c.getIdentityNumber())
                            .setFont(font));
                    document.add(new Paragraph("SĐT: " + c.getPhone())
                            .setFont(font));

                    // Thông tin thanh toán (nếu có)
                    if (isPaid && payment != null) {
                        document.add(new Paragraph("Mã thanh toán: " + payment.getId())
                                .setFont(font));
                        document.add(new Paragraph("Ngày thanh toán: " + payment.getPaymentDate())
                                .setFont(font));
                        document.add(new Paragraph("Phương thức thanh toán: " + payment.getMethod())
                                .setFont(font));
                    }

                    // Bảng chi tiết
                    Table table = new Table(2);
                    table.addCell(new Cell().add(new Paragraph("Mục").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph("Chi tiết").setFont(font)));
                    RoomModel r = booking.getRoom();
                    table.addCell(new Cell().add(new Paragraph("Phòng").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(r.getRoomNumber() + " (" + r.getType() + ")").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph("Ngày nhận").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(booking.getCheckinDate().toString()).setFont(font)));
                    table.addCell(new Cell().add(new Paragraph("Ngày trả").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(booking.getCheckoutDate().toString()).setFont(font)));
                    table.addCell(new Cell().add(new Paragraph("Tổng tiền").setFont(font)));
                    table.addCell(new Cell().add(new Paragraph(currencyFormat.format(booking.getTotalPrice()) + " VND").setFont(font)));
                    document.add(table);

                    // Chân trang
                    document.add(new Paragraph("Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!")
                            .setFont(font)
                            .setFontSize(10)
                            .setItalic());

                } catch (Exception e) {
                    throw new ServletException("Lỗi khi tạo PDF!", e);
                }
                return;
            }

            // Hiển thị giao diện HTML
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='vi'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Hóa đơn Thanh Toán</title>");
                out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
                out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css' rel='stylesheet'>");
                out.println("<style>");
                out.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #e6f0fa 0%, #f8f9fa 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }");
                out.println(".container { max-width: 700px; animation: fadeIn 0.6s ease-in-out; }");
                out.println(".card { border: none; border-radius: 12px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); transition: transform 0.3s ease, box-shadow 0.3s ease; background: white; }");
                out.println(".card:hover { transform: translateY(-8px); box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2); }");
                out.println(".card-header.success { background: linear-gradient(to right, #1e3a8a, #3b82f6); color: white; }");
                out.println(".card-header.error { background: linear-gradient(to right, #dc2626, #f87171); color: white; }");
                out.println(".card-header.warning { background: linear-gradient(to right, #f59e0b, #facc15); color: white; }");
                out.println(".card-header { border-radius: 12px 12px 0 0; padding: 1.5rem; text-align: center; }");
                out.println(".card-body { padding: 2rem; }");
                out.println(".card-body p { margin: 0.5rem 0; font-size: 1.1rem; }");
                out.println(".btn-primary { background-color: #1e40af; border: none; border-radius: 8px; padding: 0.75rem 1.5rem; transition: all 0.3s ease; width: 100%; }");
                out.println(".btn-primary:hover { background-color: #1e3a8a; transform: translateY(-2px); box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2); }");
                out.println(".btn-warning { background-color: #f59e0b; border: none; border-radius: 8px; padding: 0.75rem 1.5rem; transition: all 0.3s ease; width: 100%; }");
                out.println(".btn-warning:hover { background-color: #d97706; transform: translateY(-2px); box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2); }");
                out.println(".status-icon { font-size: 3rem; margin: 0 auto 1rem; display: block; text-align: center; }");
                out.println(".success-icon { color: #1e40af; }");
                out.println(".error-icon { color: #dc2626; }");
                out.println(".warning-icon { color: #f59e0b; }");
                out.println("table { width: 100%; border-collapse: collapse; margin: 1.5rem 0; }");
                out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
                out.println(".total-amount { text-align: right; font-size: 1.5rem; color: #dc2626; font-weight: bold; }");
                out.println(".footer { text-align: center; font-style: italic; margin-top: 1.5rem; color: #6b7280; }");
                out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(15px); } to { opacity: 1; transform: translateY(0); } }");
                out.println("@media print { .no-print { display: none; } }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div class='container'>");
                out.println("<div class='card'>");

                if (booking != null) {
                    CustomerModel c = booking.getCustomer();
                    RoomModel r = booking.getRoom();
                    NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    boolean isPaid = "Đã trả".equals(booking.getStatus());

                    out.println("<div class='card-header " + (isPaid ? (payment != null ? "success" : "warning") : "error") + "'>");
                    out.println("<h1 class='fs-3 mb-0'><i class='fas fa-receipt me-2'></i> Hóa Đơn Thanh Toán</h1>");
                    out.println("</div>");
                    out.println("<div class='card-body'>");
                    out.println("<i class='fas fa-receipt status-icon " + (isPaid ? (payment != null ? "success-icon" : "warning-icon") : "warning-icon") + "'></i>");
                    out.println("<p><strong><i class='fas fa-hotel me-2'></i>Khách sạn:</strong> CÔNG NGHỆ PHẦN MỀM</p>");
                    out.println("<p><strong><i class='fas fa-map-marker-alt me-2'></i>Địa chỉ:</strong> KP22, Phường Linh Trung, TP. Thủ Đức, TP. Hồ Chí Min</p>");
                    out.println("<p><strong><i class='fas fa-receipt me-2'></i>Mã hóa đơn:</strong> " + booking.getId() + "</p>");
                    out.println("<p><strong><i class='fas fa-clock me-2'></i>Thời gian lập:</strong> " + now.format(dateFormatter) + "</p>");
                    out.println("<p><strong><i class='fas fa-user me-2'></i>Khách hàng:</strong> " + c.getName() + "</p>");
                    out.println("<p><strong><i class='fas fa-id-card me-2'></i>CMND/CCCD:</strong> " + c.getIdentityNumber() + "</p>");
                    out.println("<p><strong><i class='fas fa-phone me-2'></i>SĐT:</strong> " + c.getPhone() + "</p>");
                    out.println("<p><strong><i class='fas fa-info-circle me-2'></i>Trạng thái:</strong> " + (isPaid ? (payment != null ? "Đã thanh toán" : "Đã trả nhưng thiếu thông tin thanh toán") : "Chưa thanh toán") + "</p>");
                    if (isPaid && payment != null) {
                        out.println("<p><strong><i class='fas fa-receipt me-2'></i>Mã thanh toán:</strong> " + payment.getId() + "</p>");
                        out.println("<p><strong><i class='fas fa-calendar-alt me-2'></i>Ngày thanh toán:</strong> " + payment.getPaymentDate() + "</p>");
                        out.println("<p><strong><i class='fas fa-credit-card me-2'></i>Phương thức thanh toán:</strong> " + payment.getMethod() + "</p>");
                    }
                    out.println("<table>");
                    out.println("<tr><th>Mục</th><th>Chi tiết</th></tr>");
                    out.println("<tr><td>Phòng</td><td>" + r.getRoomNumber() + " (" + r.getType() + ")</td></tr>");
                    out.println("<tr><td>Ngày nhận</td><td>" + booking.getCheckinDate() + "</td></tr>");
                    out.println("<tr><td>Ngày trả</td><td>" + booking.getCheckoutDate() + "</td></tr>");
                    out.println("<tr><td class='total-amount'>Tổng tiền</td><td class='total-amount'>" + currencyFormat.format(booking.getTotalPrice()) + " VND</td></tr>");
                    out.println("</table>");
                    out.println("<div class='footer'>Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi! 🌟</div>");
                    out.println("<div class='text-center no-print'>");
                    if (!isPaid) {
                        out.println("<a href='" + request.getContextPath() + "/checkout?bookingId=" + bookingId + "' class='btn btn-warning mt-3'><i class='fas fa-credit-card me-2'></i>Thanh toán ngay</a>");
                    } else if (payment != null) {
                        out.println("<button class='btn btn-primary mt-3' onclick='window.print()'><i class='fas fa-print me-2'></i>In hóa đơn</button>");
                        out.println("<a href='" + request.getContextPath() + "/invoice?bookingId=" + bookingId + "&action=download' class='btn btn-primary mt-3 ms-2'><i class='fas fa-download me-2'></i>Tải PDF</a>");
                    }
                    out.println("<a href='" + request.getContextPath() + "/' class='btn btn-primary mt-3 ms-2'><i class='fas fa-arrow-left me-2'></i>Quay lại Trang chủ</a>");
                    out.println("</div>");
                } else {
                    out.println("<div class='card-header error'>");
                    out.println("<h1 class='fs-3 mb-0'><i class='fas fa-exclamation-triangle me-2'></i> Lỗi Hóa Đơn ⚠️</h1>");
                    out.println("</div>");
                    out.println("<div class='card-body'>");
                    out.println("<i class='fas fa-exclamation-triangle status-icon error-icon'></i>");
                    out.println("<p><strong>Thông báo:</strong> " + (errorMessage != null ? errorMessage : "Không tìm thấy hóa đơn với mã đặt phòng: " + bookingId) + "</p>");
                    out.println("<a href='" + request.getContextPath() + "/' class='btn btn-primary mt-3'><i class='fas fa-arrow-left me-2'></i>Quay lại Trang chủ</a>");
                    out.println("</div>");
                }

                out.println("</div>");
                out.println("</div>");
                out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }
    }}