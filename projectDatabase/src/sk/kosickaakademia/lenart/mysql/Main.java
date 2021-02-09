package sk.kosickaakademia.lenart.mysql;


import sk.kosickaakademia.lenart.mysql.entity.CapitalCity;
import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;
import sk.kosickaakademia.lenart.mysql.entity.Monument;
import sk.kosickaakademia.lenart.mysql.json.Server;
import sk.kosickaakademia.lenart.mysql.output.Output;

import java.util.List;

public class Main {

    private static String url;
    private static String username;
    private static String password;

    public static void main(String[] args) {
        
        Database database = new Database();
        Output out = new Output();
        Country country = database.getCountryInfo("Japan");
        out.printCountryInfo(country);
        List<City> cities = database.getCities(country.getName());
        out.printCities(cities);
        String code = database.getCountryCode("Romania");
        System.out.println(code);

        City newCity = new City("Bardejov",5000);
        database.insertCity(newCity);
        database.updatePopulation("Slovakia", "Ploske", 69);
        List<CapitalCity> capitalCityList = database.getCapitalCities("Europe");
        database.printCapitalCities(capitalCityList);

        List<Monument> monuments = Database.getMonuments();
        out.printMonuments(monuments);

        Server server = new Server();
        String json = "{\"country\":\"Japan\",\"city\":\"Tokyo\",\"monument\":\"Tokyo Tower\"}";
        System.out.println(server.insertNewMonument(json, url, username, password));
    }

}
