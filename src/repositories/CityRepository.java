package repositories;

import models.City;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CityRepository {
    // Store cities
    private static ArrayList<City> CITIES_VISITED;

    public CityRepository() {
        CITIES_VISITED = new ArrayList<>();
    }
    public void addCity(City city) {
        CITIES_VISITED.add(city);
    }

    public ArrayList<City> getAll() {
        return CITIES_VISITED;
    }

    public void deleteCity(City city) {
        CITIES_VISITED.remove(city);
    }
}
