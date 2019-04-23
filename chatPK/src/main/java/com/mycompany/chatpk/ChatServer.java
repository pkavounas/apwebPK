/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatpk;

import java.util.ArrayList;
import java.util.List;
import static spark.Spark.*;
   






public class ChatServer{

    
    public static List<String> allMessages = new ArrayList<String>();
    
    public static void main(String[] args) {
        
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
        
        
        put("/send", (req, res) -> send(req));

        get("/getnewmessages",  (req, res) -> getNewMessages(req));
        

        get("/login", (req, res) -> login(req));
        

        
       

    }
 //
    public static String login(spark.Request req){
        Context ctx = getCtx(req);
        ctx.username = req.queryParams("name");
        return ("" + ((ctx.username != null && !ctx.username.equals(""))==true)); 
    }
 //sends message   
    public static String send(spark.Request req){
        verifyLoggedIn(req);
        Context ctx = getCtx(req);
        String message = req.queryParams("message");
        return message;
    }
//return a string of all new messages (NOT WORKING, ONLY RETRIEVES MOST RECENT MESSAGE)
    public static String getNewMessages(spark.Request req){
        verifyLoggedIn(req);
        Context ctx = getCtx(req);
        String unread = "";
        List<String> temp = allMessages.subList(ctx.lastRead, allMessages.size());
        ctx.lastRead = allMessages.size();
        for (int i = 0; i < temp.size(); i++) {
            unread += temp.get(i);
    }
        return unread;
    }

//login filter (NOT WORKING)
  public static void verifyLoggedIn(spark.Request req){
        Context ctx = getCtx(req);
        if (ctx.username == null) {
            halt(404, "please enter a username");
        }
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

//Context Class
class Context{
    int numOfMessages = 0;
    public int lastRead = 0;
    String username;
    
}

 















