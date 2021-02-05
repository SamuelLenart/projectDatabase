package sk.kosickaakademia.lenart.mysql;


import sk.kosickaakademia.lenart.mysql.entity.CapitalCity;
import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;
import sk.kosickaakademia.lenart.mysql.output.Output;

import java.util.List;

public class Main {
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
    }
}
