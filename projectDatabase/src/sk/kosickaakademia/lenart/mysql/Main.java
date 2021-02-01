package sk.kosickaakademia.lenart.mysql;


import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;
import sk.kosickaakademia.lenart.mysql.output.Output;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Output out = new Output();
        Country country = database.getCountryInfo("Turkey");
        out.printCountryInfo(country);
        List cities = (List) database.getCities("Japan");
    }
}
