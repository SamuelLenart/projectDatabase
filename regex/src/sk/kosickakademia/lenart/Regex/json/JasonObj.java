package sk.kosickakademia.lenart.Regex.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

public class JasonObj {

    private static Throwable e;

    public static void main(String[] args) throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        try {Reader reader = new Reader("resources/testjson") {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        };

            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(file));
            System.out.println(jsonObject);

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        }catch(IOException e){
                e.printStackTrace();
            } catch(ParseException e){
                e.printStackTrace();
            }
        }
}


