package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/* main class */
public class RankingsController {

    /* main method */
    public static void main(String[] args) {
       JsonParser parser = new JsonParser();
         String json = "{\"name\":\"Mahesh Kumar\", \"age\":21}";
            JsonElement object = parser.parse(json);
            System.out.println(object);
    }
}


