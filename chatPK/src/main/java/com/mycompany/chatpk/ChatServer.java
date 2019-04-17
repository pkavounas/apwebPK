/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatpk;
import static spark.Spark.*;

public class ChatServer {
    public static void main(String[] args) {
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
        get("/factorial", (req, res) -> factorial(req));
    }
    
    public static String factorial(spark.Request req){
        int num;
    
        try{
            num = Integer.parseInt(req.queryParams("number"));
        }catch(Exception e) {
            return "parsing exception";
        }
        
        
        double res = 1;
        for (int i = 1; i <= num; i++){
            res *= i;
        }
        return ""+ res;
    }
}