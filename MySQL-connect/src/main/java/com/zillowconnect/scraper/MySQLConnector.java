package com.zillowconnect.scraper;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.*;

/**
 * Hello world!
 *
 */

public class MySQLConnector {
    Connection connection;
    public MySQLConnector(String url, String user, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {System.out.println(e);}
    }

    public ResultSet execute_query(String query) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            stmt.close();
            return res;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return null;
        }
    }
  
    public void export_query(String query, String filename){
        try {
            ResultSet res = execute_query(query);
    
            FileWriter writer = new FileWriter(filename);
            CSVWriter csv_writer = new CSVWriter(writer);
            Boolean include_headers = true;
    
            csv_writer.writeAll(res, include_headers);
  
            csv_writer.close();
            writer.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close_connection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
