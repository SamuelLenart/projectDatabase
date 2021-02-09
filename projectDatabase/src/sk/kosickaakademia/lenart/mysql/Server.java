package sk.kosickaakademia.lenart.mysql.json;

import sk.kosickaakademia.lenart.mysql.Database;
import sk.kosickaakademia.lenart.mysql.entity.Monument;
import sk.kosickaakademia.lenart.mysql.Helper.Help;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Server {
    public String getJSON(String url, String username, String password){
        List<Monument> list = Help.getMonuments(url, password, username);
        if (list.isEmpty())
            return "{}";
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Monument monument : list){
            JSONObject temp = new JSONObject();
            temp.put("id", monument.getId());
            temp.put("country", monument.getCountryName());
            temp.put("city", monument.getCityName());
            temp.put("monument", monument.getMonumentName());
            jsonArray.add(temp);
        }
        jsonObject.put("count", list.size());
        jsonObject.put("Monuments", jsonArray);
        return jsonObject.toJSONString();
    }

    public boolean insertNewMonument(String json, String url, String username, String password) {
        Database dat = new Database();
        try {
            Object object = new JSONParser().parse(json);
            JSONObject jsonObject = (JSONObject) object;
            String countryName = (String) jsonObject.get("country");
            String cityName = (String) jsonObject.get("city");
            String monumentName = (String) jsonObject.get("monument");
            dat.insertNewMonument(Help.getCountryCode(countryName, url, username, password), cityName, monumentName);
        } catch (ParseException e) { e.printStackTrace(); }
        return true;
    }
}
