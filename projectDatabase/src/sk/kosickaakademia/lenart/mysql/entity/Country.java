package sk.kosickaakademia.lenart.mysql.entity;

public class Country {

    private String name;
    private String code;
    private String capital;
    private int area;
    private String continent;

    public Country(String name, String code, String capital, int area, String continent) {
        this.name = name;
        this.code = code;
        this.capital = capital;
        this.area = area;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCapital() {
        return capital;
    }

    public int getArea() {
        return area;
    }

    public String getContinent() {
        return continent;
    }
}
