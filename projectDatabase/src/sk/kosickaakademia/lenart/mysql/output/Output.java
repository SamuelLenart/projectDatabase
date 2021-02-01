package sk.kosickaakademia.lenart.mysql.output;

import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;

import java.util.List;

public class Output {
    public void printCities(List<City> cities){
        for (City city : cities)
            System.out.println(city.getName() + " ( " + city.getPopulation() + " )");

    }

    public void printCountryInfo(Country country){
        System.out.println("Country: " + country.getName());
        System.out.println("Capital: " + country.getCapital());
        System.out.println("Code: " + country.getCode());
        System.out.println("Continent: " + country.getContinent());
        System.out.println("Surface Area: " + country.getArea());
    }
}