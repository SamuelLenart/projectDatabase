package sk.kosickaakademia.lenart.mysql.Helper;

import mysql.DatabaseID;
import mysql.entity.City;
import mysql.entity.Monument;

import sk.kosickaakademia.lenart.mysql.entity.City;
import sk.kosickaakademia.lenart.mysql.entity.Monument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Help {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DatabaseID.getUrl(), DatabaseID.getUsername(), DatabaseID.getPassword());
    }

    public static String getCountryCode(String country){
        if (country == null || country.equalsIgnoreCase("")) {
            System.out.println("Wrong country name!");
            return null;
        }
        String query = "SELECT Code FROM country WHERE Name LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, country);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String code = rs.getString("Code");
                    connection.close();
                    return code;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<City> getCities(String country){
        //prepare statement
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name, JSON_EXTRACT(Info, '$.Population') AS Population " +
                "FROM city " +
                "INNER JOIN country ON country.Code = city.CountryCode " +
                "WHERE country.Name LIKE ? ORDER BY Population DESC";
        try {
            Connection connection = getConnection();
            if (connection != null) {
                //make statement
                PreparedStatement ps = connection.prepareStatement(query);
                //insert, update, delete executeUpdate
                //store to ResultSet
                ps.setString(1, country);
                ResultSet rs = ps.executeQuery();
                if (getCountryCode(country) == null){
                    System.out.println("Wrong country!");
                    return null;
                }
                while (rs.next()){
                    String name = rs.getString("Name");
                    int pop = rs.getInt("Population");
                    City newCity = new City(name, pop);
                    cities.add(newCity);
                }
                connection.close();
            }
        }catch (Exception e) { e.printStackTrace(); }
        return cities;
    }

    public static boolean isCityInCountry(String city, String country){
        String query = "SELECT Name, CountryCode from city " +
                "WHERE CountryCode LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection != null){
                String code = getCountryCode(country);
                if (code == null) {
                    System.out.println("Wrong country name!");
                    return false;
                }
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, code);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    if (city.equalsIgnoreCase(rs.getString("Name")))
                        return true;
                }
                connection.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static boolean isCityInCountryCode(String city, String code3){
        String query = "SELECT Name, CountryCode from city " +
                "WHERE CountryCode LIKE ?";
        if (code3 == null) {
            System.out.println("Wrong country name!");
            return false;
        }
        try {
            Connection connection = getConnection();
            if (connection != null){

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, code3);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    if (city.equalsIgnoreCase(rs.getString("Name")))
                        return true;
                }
                connection.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static int getPreviousPop(String country, String city) {
        int previousPop = 0;
        for (City city1 : getCities(country)){
            if (city1.getName().equals(city)) {
                previousPop = city1.getPopulation();
                break;
            }
        }
        return previousPop;
    }

    public static int getMonumentId(){
        String query = "SELECT * FROM monument";
        int count = 0;
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("id");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    public static Integer getCityId(String city){
        String query = "SELECT ID, Name " +
                "FROM city " +
                "WHERE Name LIKE ?";
        try {
            Connection connection = getConnection();
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, city);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    if (rs.getString("Name").equalsIgnoreCase(city)){
                        return rs.getInt("ID");
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static List<Monument> getMonuments(){
        String query = "SELECT monument.id, monument.name, city.Name, country.Name " +
                "FROM monument " +
                "INNER JOIN city ON city.ID = monument.city " +
                "INNER JOIN country ON country.Code = city.CountryCode";
        List<Monument> monuments = new ArrayList<>();
        try {
            Connection connection = getConnection();
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    String name = rs.getString("monument.name");
                    String cityName = rs.getString("city.Name");
                    String countryName = rs.getString("country.Name");
                    int id = rs.getInt("monument.id");
                    monuments.add(new Monument(id, cityName, countryName, name));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return monuments;
    }


}
