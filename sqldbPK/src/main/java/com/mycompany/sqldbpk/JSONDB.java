/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sqldbpk;
import com.google.gson.*;
import spark.*;
/**
 *
 * @author pkavounas
 */
public class JSONDB implements ResponseTransformer{
     final static private Gson gson = new Gson();

    @Override
    public String render(Object o) {
        return gson.toJson(o);
    }
}
