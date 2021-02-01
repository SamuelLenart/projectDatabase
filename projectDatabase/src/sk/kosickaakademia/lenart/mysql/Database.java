package sk.kosickaakademia.lenart.mysql;



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

        List<City> list = new ArrayList<>();

        try {
            Connection connection = getConnection();
            if (connection != null) {

                System.out.println("Correct");
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, country);
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("Name");
                    int pop = rs.getInt("Population");
                    City City = new City(name, pop);
                    list.add(City);
                }
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
        public Country getCountryInfo (String country){
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
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, country);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String code3 = rs.getString("Code");
                    String capital = rs.getString("city.Name");
                    String continent = rs.getString("Continent");
                    languages.add(rs.getString("Language"));
                    int area = rs.getInt("SurfaceArea");
                    System.out.println(code3 + " " + capital + " " + continent + " " + area + " " + languages);
                    countryInfo = new Country(country, code3, capital, area, continent, languages);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return countryInfo;
        }
    }

