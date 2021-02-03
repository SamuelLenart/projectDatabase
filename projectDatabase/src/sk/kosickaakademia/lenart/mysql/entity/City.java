package sk.kosickaakademia.lenart.mysql.entity;

public class City {
    private String name;
    private int population;
    private String district;
    private String code3;
    private String country;

    

    public City(String name, int population){
        this.name=name;
        this.population=population;
        this.district = district;
        this.country = country;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getDistrict() {
        return district;
    }

    public String getCountry() {
        return country;
    }

    public String getCode3() {
        return code3;
    }

    public String setCode3(){
        return code3;
    }

    public void setCode3(String code3) {
    }
}
