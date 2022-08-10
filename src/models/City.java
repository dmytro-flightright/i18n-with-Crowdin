package models;

import java.util.Date;
import java.util.Locale;

public class City {
    private Locale cityLocale;
    private String cityName;

    private Date visitedDate;

    public City(Locale cityLocale, String cityName, Date visitedDate) {
        this.cityLocale = cityLocale;
        this.cityName = cityName;
        this.visitedDate = visitedDate;
    }

    public City() {
        cityLocale = Locale.getDefault();
        cityName = null;
        visitedDate = null;
    }

    public Locale getCityLocale() {
        return cityLocale;
    }

    public void setCityLocale(Locale cityLocale) {
        this.cityLocale = cityLocale;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(Date visitedDate) {
        this.visitedDate = visitedDate;
    }

    public boolean equals(City city) {
        return ((this.cityName.equals(city.getCityName()))&&(this.visitedDate.equals(city.getVisitedDate())));
    }
}
