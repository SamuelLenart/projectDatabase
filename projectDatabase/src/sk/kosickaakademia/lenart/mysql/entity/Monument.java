package sk.kosickaakademia.lenart.mysql.entity;

public class Monument {
    private int Id;
    private String cityName, countryName, monumentName;

    public Monument(int id, String cityName, String countryName, String monumentName) {
        Id = id;
        this.cityName = cityName;
        this.countryName = countryName;
        this.monumentName = monumentName;
    }

    public int getId() {
        return Id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getMonumentName() {
        return monumentName;
    }
}
