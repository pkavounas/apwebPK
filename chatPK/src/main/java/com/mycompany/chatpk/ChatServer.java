/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatpk;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import static spark.Spark.*;
   






public class ChatServer{

    
    public static ArrayList<String> allMessages = new ArrayList<String>();
    
    public static void main(String[] args) {
        
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
        
        
        put("/send", (req, res) -> send(req));

        get("/getnewmessages", "application/json", (req, res) -> getNewMessages(req, res), new JSONRT());
        get("/getCtx", (req, res) -> getCtx(req));

        get("/login", (req, res) -> login(req));
        

        
       

    }
 //takes username and puts it to context
    public static String login(spark.Request req){
        Context ctx = getCtx(req);
        ctx.username = req.queryParams("name"); 
        ctx.lastRead = 0;
        String result = ctx.username != null ? "ok" : "not nice"; 
        System.out.println(ctx.username + "is verified");
        return result;
    }
   //join message and user and put to array
    public static String send(spark.Request req){
        verifyLoggedIn(req);
        Context ctx = getCtx(req);
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
        req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                multipartConfigElement);
        
        String message = req.queryParams("myMessage");
        String name = ctx.username; 
        String complete = name + ": " + message;
        

        synchronized(allMessages) {
            allMessages.add(complete);
        }
        
      

        return message; 
    
    }
//return a string of all new messages (NOT WORKING, ONLY RETRIEVES MOST RECENT MESSAGE)
  public static Object getNewMessages(spark.Request req, spark.Response res) {
        Context ctx = getCtx(req);
        List<String> myMessages;
        
        
            synchronized (allMessages) {
                myMessages = allMessages.subList(ctx.lastRead, allMessages.size());
                ctx.lastRead = allMessages.size();
            }
        
        return myMessages;
    }




//login filter (NOT WORKING)
  public static void verifyLoggedIn(spark.Request req){
        Context ctx = getCtx(req);
        if (ctx.username == null) {
           halt(404, "please enter a username");
        }
        System.out.println("verified");
        
    }
    
//Gives User new Context 
    public static Context getCtx(spark.Request req) {
        Context ctx = req.session().attribute("context");
        
        if (ctx == null) {
            ctx = new Context();
            req.session().attribute("context", ctx);
        }
        return ctx;
    }
}
