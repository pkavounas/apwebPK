/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sqldbpk;

import static spark.Spark.*;
import java.sql.*;
/**
 *
 * @author pkavounas
 */
public class Main {
    public static void main(String[] args) {
        
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
}
    
    
        public static void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:chinook.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    static Connection conn = null;
     public static void dumpTable(String tableName) {

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tableName); // select everything in the table

            System.out.println();
            System.out.println(tableName + ":");

            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.println(rsmd.getColumnName(i) + ",  " + rsmd.getColumnTypeName(i)); // prints column name and type
            }

            System.out.println();
            System.out.println("Rows:");

            while (rs.next()) { // prints the id and first two columns of all rows
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
}

