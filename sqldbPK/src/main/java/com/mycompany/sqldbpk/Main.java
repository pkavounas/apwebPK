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
    
        //SETS MAX DISPLAY LENGTH
    public final static int LENGTH = 50;
    
    
    public static void main(String[] args) {
        
        staticFiles.location("static/");
        
        connect();
        
      
        
        get("/hello", (req, res) -> "Hello World");
        get("/dumpTable", "application/json", (req, res) -> dumpTable(req.queryParams("tablename")), new JSONDB());
        get("/test", (req, res) -> test(req));
        
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
     public static Object[][] dumpTable(String tableName) {

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
            
            
            //Creates new array of arrays and prints up to length (SEE TOP) and all columns
            Object[][] res = new Object[LENGTH][numberOfColumns];
            int counter = 0;
            while (rs.next() && counter < LENGTH) {       
                for (int j = 1; j <= numberOfColumns; j++) {
                    res[counter][j-1] = rs.getString(j);                    
                }              
                counter++;
            }           
            return res;
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    } 

    
    


    static String test(spark.Request req){
        String input = req.queryParams("tablename");
        String changed = input + " has gone through";
        return changed;
    }
}


 