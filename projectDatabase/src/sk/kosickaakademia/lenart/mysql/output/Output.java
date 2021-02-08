package sk.kosickaakademia.lenart.mysql.output;

import sk.kosickaakademia.lenart.mysql.entity.CapitalCity;
import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;
import sk.kosickaakademia.lenart.mysql.entity.Monument;

import java.util.List;

public class Output {
    public void printCities(List<City> cities) {
        for (City city : cities)
            System.out.println(city.getName() + " ( " + city.getPopulation() + " )");

    }

    public void printCountryInfo(Country country) {
        System.out.println("Country: " + country.getName());
        System.out.println("Capital: " + country.getCapital());
        System.out.println("Code: " + country.getCode3());
        System.out.println("Continent: " + country.getContinent());
        System.out.println("Surface Area: " + country.getArea());
    }

    public void printCapitalCities(List <CapitalCity> list){
        for (CapitalCity cap : list){
            System.out.println(cap.getCountry() + " -> " + cap.getName() + " -> " + cap.getPopulation());
        }
    }
    public void printMonuments(List<Monument> list) {
        for (Monument monument : list) {
            System.out.println(monument.getId() + ". " + monument.getMonumentName() + " in " + monument.getCityName() + ", " + monument.getCountryName());
        }
    }
}