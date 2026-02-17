package com.wipro.servelt;

import java.io.IOException;
import java.sql.Date;   // ✅ IMPORTANT CHANGE
import java.util.List;

import com.wipro.bean.WarehouseBean;
import com.wipro.service.Administrator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Administrator admin = new Administrator();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        try {

            // 🔹 Add New Record
            if ("newRecord".equals(operation)) {

                WarehouseBean bean = new WarehouseBean();

                bean.setItemName(request.getParameter("itemName"));
                bean.setLocation(request.getParameter("location"));

                String dateStr = request.getParameter("receivedDate");
                Date sqlDate = Date.valueOf(dateStr);  // ✅ Direct sql.Date
                bean.setReceivedDate(sqlDate);

                bean.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                bean.setStatus(request.getParameter("status"));
                bean.setRemarks(request.getParameter("remarks"));

                String result = admin.addRecord(bean);

                if ("FAIL".equals(result) || "INVALID INPUT".equals(result)) {
                    response.sendRedirect("error.html");
                } else {
                    response.sendRedirect("success.html");
                }
            }

            // 🔹 View Single Record
            else if ("viewRecord".equals(operation)) {

                String itemName = request.getParameter("itemName");

                String dateStr = request.getParameter("receivedDate");
                Date sqlDate = Date.valueOf(dateStr);

                WarehouseBean bean = admin.viewRecord(itemName, sqlDate);

                if (bean == null) {
                    request.setAttribute("message",
                            "No matching record exists! Please try again!");
                } else {
                    request.setAttribute("warehouse", bean);
                }

                request.getRequestDispatcher("displayWarehouse.jsp")
                        .forward(request, response);
            }

            // 🔹 View All Records
            else if ("viewAllRecords".equals(operation)) {

                List<WarehouseBean> list = admin.viewAllRecords();

                if (list == null || list.isEmpty()) {
                    request.setAttribute("message", "No records available!");
                } else {
                    request.setAttribute("warehouseList", list);
                }

                request.getRequestDispatcher("displayAllWarehouse.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
