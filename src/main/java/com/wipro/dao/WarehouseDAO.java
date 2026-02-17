package com.wipro.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wipro.bean.WarehouseBean;
import com.wipro.util.DBUtil;

public class WarehouseDAO {

    
    public boolean recordExists(String itemName, Date receivedDate) {

        boolean exists = false;

        String query = "SELECT 1 FROM WAREHOUSE WHERE ITEM_NAME = ? AND RECEIVED_DATE = ?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, itemName);
            ps.setDate(2, receivedDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    // ✅ Generate Record ID
    public String generateRecordID(String itemName, Date receivedDate) {

        String recordId = null;

        try (Connection con = DBUtil.getDBConnection()) {

            java.text.SimpleDateFormat format =
                    new java.text.SimpleDateFormat("yyyyMMdd");

            String datePart = format.format(receivedDate);

            String itemPart = itemName.length() >= 2
                    ? itemName.substring(0, 2).toUpperCase()
                    : itemName.toUpperCase();

            String seqQuery = "SELECT WAREHOUSE_SEQ.NEXTVAL FROM DUAL";

            try (PreparedStatement ps = con.prepareStatement(seqQuery);
                 ResultSet rs = ps.executeQuery()) {

                int seqNumber = 0;

                if (rs.next()) {
                    seqNumber = rs.getInt(1);
                }

                String seqPart = String.format("%02d", seqNumber);

                recordId = datePart + itemPart + seqPart;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recordId;
    }

    // ✅ Insert Record
    public String createRecord(WarehouseBean warehouseBean) {

        String result = "FAIL";

        String query = "INSERT INTO WAREHOUSE "
                + "(RECORD_ID, ITEM_NAME, LOCATION, RECEIVED_DATE, QUANTITY, STATUS, REMARKS) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, warehouseBean.getRecordId());
            ps.setString(2, warehouseBean.getItemName());
            ps.setString(3, warehouseBean.getLocation());
            ps.setDate(4, warehouseBean.getReceivedDate());
            ps.setInt(5, warehouseBean.getQuantity());
            ps.setString(6, warehouseBean.getStatus());
            ps.setString(7, warehouseBean.getRemarks());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                result = warehouseBean.getRecordId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ✅ Fetch Single Record
    public WarehouseBean fetchRecord(String itemName, Date receivedDate) {

        WarehouseBean bean = null;

        String query = "SELECT * FROM WAREHOUSE WHERE ITEM_NAME = ? AND RECEIVED_DATE = ?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, itemName);
            ps.setDate(2, receivedDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                bean = new WarehouseBean();

                bean.setRecordId(rs.getString("RECORD_ID"));
                bean.setItemName(rs.getString("ITEM_NAME"));
                bean.setLocation(rs.getString("LOCATION"));
                bean.setReceivedDate(rs.getDate("RECEIVED_DATE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

    // ✅ Fetch All Records
    public List<WarehouseBean> fetchAllRecords() {

        List<WarehouseBean> list = new ArrayList<>();

        String query = "SELECT * FROM WAREHOUSE";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                WarehouseBean bean = new WarehouseBean();

                bean.setRecordId(rs.getString("RECORD_ID"));
                bean.setItemName(rs.getString("ITEM_NAME"));
                bean.setLocation(rs.getString("LOCATION"));
                bean.setReceivedDate(rs.getDate("RECEIVED_DATE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));

                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
