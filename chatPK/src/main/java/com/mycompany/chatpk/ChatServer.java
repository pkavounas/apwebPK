/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatpk;

import java.util.ArrayList;
import java.util.List;
import static spark.Spark.*;

public class ChatServer {

    static ArrayList<String> msgs;

    public static void main(String[] args) {
        msgs = new ArrayList<>();
        staticFiles.location("static/");
        get("/hello", (req, res) -> "Hello World");
        get("/shutdown", (req, res) -> {System.exit(0); return"";});
        
        put("/protected/putmessage", (req, res) -> putmessage(req));

        get("/protected/getnewmessages", "application/json", (req, res) -> getNewMessages(req,res));


        get("/login", (req, res) -> login(req));
        before("/protected/*", (req, res) -> {
            if (req.session().attribute("username")== null){
                halt(401, "You can only send messages when logged in");
            }
        });

        
       

    }


    public static String putmessage(spark.Request req){
        ContextClass context = getCtx(req.session());
        msgs.add(req.session().attribute("username") + ":" + req.body());
        return req.session().id();
    }


    public static ContextClass getCtx(spark.Request session){
        ContextClass ctx = session.attribute("context");
        if(ctx==null){
            ctx = new ContextClass();
            req.session().attribute("Context", ctx);
        }
        return ctx;
    }





    public static String login(spark.Request req){
        req.session().attribute("username", req.body());
        return req.body();

    }


    public static Object getNewMessages(spark.Request req, spark.Response res){
        ContextClass ctx = getCtx(req.session());
        List<String> myMessages;

        synchronized(ctx){
            synchronized(msgs){
                myMessages = msgs.subList(ctx.numOfMessages, msgs.size());
                ctx.numOfMessages = msgs.size();
            }
        }
        return myMessages;
    }
}

class ContextClass{
    int numOfMessages = 0;
}










