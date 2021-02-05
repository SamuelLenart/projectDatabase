package sk.kosickaakademia.lenart.mysql.entity;

public class CapitalCity extends City {
    private String country;

    public CapitalCity(String city, String country, int pop) {
        super(city, pop);
        this.country = country;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int getPopulation() {
        return super.getPopulation();
    }
}
