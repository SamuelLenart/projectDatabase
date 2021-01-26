package sk.kosickakademia.lenart.Regex.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Json {

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        obj.put("name","Milan");
        obj.put("age",32);
        obj.put("sport","Hockey");
        obj.put("active",true);

        System.out.println(obj);
        }


    }

