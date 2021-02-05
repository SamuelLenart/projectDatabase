package sk.kosickaakademia.lenart.mysql;



import sk.kosickaakademia.lenart.mysql.entity.CapitalCity;
import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String url = "jdbc:mysql://localhost:3306/world_x";
    private String username = "root";
    private String password = "";

    public List<City> getCities(String country) {
        String query = "SELECT city.Name, JSON_EXTRACT(Info, '$.Population') AS Population " +
                "FROM city " +
                "INNER JOIN country ON country.code = city.CountryCode " +
                "WHERE country.name LIKE ? ORDER BY Population DESC ";

        List<City> cities = new ArrayList<>();

        try {
            Connection con = getConnection();
            if (con != null) {

                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, country);
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("Name");
                    int pop = rs.getInt("Population");
                    City City = new City(name, pop);
                    cities.add(City);
                }
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    public Country getCountryInfo(String country) {
        String query = "SELECT country.Name, Code, city.Name, " +
                "Language, " +
                "JSON_UNQUOTE(JSON_EXTRACT(doc, '$.geography.Continent')) AS Continent, " +
                "JSON_EXTRACT(doc, '$.geography.SurfaceArea') AS SurfaceArea " +
                "FROM country " +
                "INNER JOIN city ON city.ID = country.Capital " +
                "INNER JOIN countryinfo ON country.Code = countryinfo._id " +
                "INNER JOIN countrylanguage ON country.Code = countrylanguage.CountryCode " +
                "WHERE country.Name LIKE ? AND IsOfficial = 'T'";

        Country countryInfo = null;

        List<String> languages = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String code3 = rs.getString("country.code");
                String capital = rs.getString("city.name");
                String continent = rs.getString("continent");
                languages.add(rs.getString("Language"));
                int area = rs.getInt("SurfaceArea");
                //System.out.println(code3 + " " + capital + " " + continent + " " + area + " " + languages);
                countryInfo = new Country(country, code3, capital, area, continent, languages);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryInfo;
    }

    public String getCountryCode(String name) {
        if (name == null || name.equalsIgnoreCase("")) {

            try {
                Connection con = getConnection();
                String query = "SELECT Code FROM country WHERE Name LIKE ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String code = rs.getString("Code");
                    con.close();
                    return code;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }

    public void insertCity(City newCity) {
        String country = newCity.getCountry();
        String code3 = getCountryCode(country);
        if (code3 == null) {
            System.out.println("This country " + country + " does not exist!");
        } else {
            newCity.setCode3(code3);
            String query = "INSERT INTO city (Name, CountryCode, District, Info)" + "VALUES (?,?,?,?)";
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, newCity.getName());
                ps.setString(2, newCity.getCode3());
                ps.setString(3, newCity.getDistrict());
                String json = "{\"Population\": " + newCity.getPopulation() + "}";
                ps.setString(4, json);
                ps.execute();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePopulation(String country, String city, int pop){
        String query = "UPDATE city " +
                "SET Info = ? " +
                "WHERE Name like ?";
        try {
            Connection connection = getConnection();
            if (connection != null) {
                if (isCityInCountry(city, country)) {
                    System.out.println("Wrong country or city name!");
                    return;
                }
                if (pop <= 0){
                    System.out.println("Bad number!");
                    return;
                }
                int previousPop = getPreviousPop(country, city);
                String json = "{\"Population\": " + pop + "}";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, json);
                ps.setString(2, city);
                ps.executeUpdate();
                System.out.println("Successfully updated population of " + city + " from " + previousPop + " to " + pop + " ");
                connection.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private boolean isCityInCountry(String city, String country) {
        return false;
    }

    private int getPreviousPop(String country, String city) {
        int previousPop = 0;
        for (City city1 : getCities(country)){
            if (city1.getName().equals(city)) {
                previousPop = city1.getPopulation();
                break;
            }
        }
        return previousPop;
    }

    public List<String> getCountryInContinent(String continent){
        List<String> countries = new ArrayList<>();
        if (continent == null || continent.equalsIgnoreCase("")){
            System.out.println("Wrong continent");
            return null;
        }
        String query = "SELECT JSON_EXTRACT(doc, '$._id') AS Code, JSON_EXTRACT(doc, '$.geography.Continent') AS Con " +
                "FROM countryinfo " +
                "WHERE JSON_EXTRACT(doc, '$.geography.Continent') = ?";
        try {
            Connection connection = getConnection();
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1 , continent);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    countries.add(rs.getString("Code"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return countries;
    }

    public CapitalCity getCapital(String countryCode){
        if (countryCode == null || countryCode.equalsIgnoreCase(""))
            return null;
        String query = "SELECT Capital, country.Name, city.Name, JSON_EXTRACT(Info, '$.Population') AS Population " +
                "FROM country " +
                "INNER JOIN city ON country.Capital = city.ID " +
                "WHERE country.Code LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, countryCode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("city.Name");
                    String country = rs.getString("country.Name");
                    int pop = rs.getInt("Population");
                    return new CapitalCity(name, country, pop);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<CapitalCity> getCapitalCities(String continent){
        List<CapitalCity> capitals = new ArrayList<>();
        for (String country : getCountryInContinent(continent)){
            capitals.add(getCapital(country.replace("\"", "")));
        }
        return capitals;
    }

    public void printCapitalCities(List <CapitalCity> list){
        for (CapitalCity cap : list){
            System.out.println(cap.getCountry() + " -> " + cap.getName() + " -> " + cap.getPopulation());
        }
    }
}
