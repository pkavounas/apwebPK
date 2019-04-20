/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatpk;
import java.util.ArrayList;
import static spark.Spark.*;

public class ChatServer {
    public static void main(String[] args) {
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
        get("/factorial", (req, res) -> factorial(req));
        get("/login", (req, res) -> login(req));
        get("/send"), (req, res) -> send(req));
        ArrayList<String> messages = new ArrayList<String>();

    }

    public static String login(spark.Request req){
        Context ctx = getCtx
        ctx.name = req.queryParams("name");
        return(ctx.name != null?"ok":"ew no")

    }


    public static void verifyLoggedIn(spark.Request req){
        Context ctc = getCtx(req);
        if(ctx.name==null){
            halt(401, "Go Away");
        }
    }

    public static String send(spark.Request req) {
        verifyLoggedIn(req);
        Context ctx = getCtx(req); 
        
        String message = req.queryParams(message); 
        messages.add(message);

        return "sent!";
    }

    public static String getNewMessages(spark.Request req){
        verifyLoggedIn(req);
        Context ctx = getCtx(req);
        String update = String.join(",", messages); 
        return update;
        
         
    }


    public static Context getCtx(spark.Request req){
        Context ctx = req.session().attribute("context");
        if(ctx==null){
            ctx = new Context();
            req.session().attribute("context", ctx);
        }
        return ctx;
    }



    public static String factorial(spark.Request req){
        verifyLoggedIn(req);
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
    


