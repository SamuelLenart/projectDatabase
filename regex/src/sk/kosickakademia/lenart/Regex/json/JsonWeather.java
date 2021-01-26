package sk.kosickakademia.lenart.Regex.json;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class JsonWeather {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try{
            Reader reader = new FileReader("resource/test.json");
            JSONObject object = (JSONObject) parser.parse(reader);



            JSONObject tempIn = (JSONObject) object.get("main");
            double temperature = (double) tempIn.get("temp");
            System.out.println("Actual temperature: " + (Math.round((temperature - 273.15) * 100)/100.0) + "°C");
            long pressure = (long) tempIn.get("pressure");
            System.out.println("Pressure: " + Math.round(pressure*100)/100.0 + "hPa");
            long visibility = (long) object.get("visibility");
            System.out.println("Visibility: " + visibility + "m");
            long humidity = (long) tempIn.get("humidity");
            System.out.println("Humidity: " + humidity + "%");
            JSONObject sys = (JSONObject) object.get("sys");
            long sunrise = (long) sys.get("sunrise");
            long sunset = (long) sys.get("sunset");

        } catch (IOException | ParseException e) {e.printStackTrace();
        }

    }
}

