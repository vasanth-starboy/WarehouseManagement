package com.wipro.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getDBConnection() {

        try {
        	
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String user = "warehouse";
            String pass = "root";

            return DriverManager.getConnection(URL, user, pass);
           

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
