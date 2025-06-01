package be.howest.ti.adria.logic.domain;

import java.util.Objects;

public class Location {
    private String city;
    private String cityCode;
    private String street;
    private String homeNumber;

    public Location() {

    }

    public Location(String city, String cityCode, String street, String homeNumber) {
        this.city = city;
        this.cityCode = cityCode;
        this.street = street;
        this.homeNumber = homeNumber;
    }

    public Location(String cityCode, String street, String homeNumber) {
        this(null, cityCode, street, homeNumber);
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getStreet() {
        return street;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public Location setCity(String city) {
        this.city = city;
        return this;
    }

    public Location setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public Location setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
        return this;
    }

    public Location setStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (!Objects.equals(city, location.city)) return false;
        if (!Objects.equals(cityCode, location.cityCode)) return false;
        if (!Objects.equals(street, location.street)) return false;
        return Objects.equals(homeNumber, location.homeNumber);
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (homeNumber != null ? homeNumber.hashCode() : 0);
        return result;
    }
}
