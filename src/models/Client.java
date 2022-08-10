package models;

import java.util.*;

public class Client {

    private Locale userLocale;
    private final String username;
    private Map<City, Date> visitedCities;

    public Client(Locale userLocale, String username) {
        this.userLocale = userLocale;
        this.username = username;
    }

    public Locale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public String getUsername() {
        return username;
    }

    public void addCity(City city, Date dateVisited) {
        this.visitedCities.put(city, dateVisited);
    }

    public void removeCity(City city, Date dateVisited) {
        this.visitedCities.remove(city, dateVisited);
    }

}
