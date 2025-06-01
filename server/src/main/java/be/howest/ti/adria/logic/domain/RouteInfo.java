package be.howest.ti.adria.logic.domain;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;

public class RouteInfo {
    private String driverId;
    private String driverName;
    private Double pricePerSeat;
    private Location sourceLocation;
    private Location destinationLocation;
    private transient LocalDate date;

    public RouteInfo() {

    }

    public RouteInfo(String driverId, String driverName, Double pricePerSeat, Location sourceLocation, Location destinationLocation, LocalDate date) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.pricePerSeat = pricePerSeat;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.date = date;
    }

    public String getDriverId() {
        return driverId;
    }

    public RouteInfo setDriverId(String driverId) {
        this.driverId = driverId;
        return this;
    }

    public Double getPricePerSeat() {
        return pricePerSeat;
    }

    public RouteInfo setPricePerSeat(Double totalPrice) {
        this.pricePerSeat = totalPrice;
        return this;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public RouteInfo setSourceLocation(Location sourceLocation) {
        this.sourceLocation = sourceLocation;
        return this;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public RouteInfo setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
        return this;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String date) { //this methode can't be chainable because of firebase
        this.date = LocalDate.parse(date);
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
