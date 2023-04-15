package com.example.restapi.model;

import net.minidev.json.JSONObject;

public class StatusController {
    public JSONObject Success(){
        String res;
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("message", "Sucess");
        res = json.toString();
        return json;
    }
    public JSONObject MissingPayload(){
        String res;
        JSONObject json = new JSONObject();
        json.put("code", 400);
        json.put("message", "Missing Payload");
        res = json.toString();
        return json;
    }
    public JSONObject IncorrectPassword(){
        String res;
        JSONObject json = new JSONObject();
        json.put("code", 401);
        json.put("message", "Incorrect Password");
        res = json.toString();
        return json;
    }
}
