package be.howest.ti.adria.logic.domain;

import com.google.firebase.database.Exclude;

import java.util.LinkedList;
import java.util.List;

public class RideInfo {
    private RideStatus rideStatus;
    private Integer seats;
    private RouteInfo routeInfo;
    private List<User> guests;
    private String rideId;

    public RideInfo() {

    }

    public RideInfo(String rideId, RideStatus rideStatus, Integer seats, RouteInfo routeInfo, List<User> guests) {
        this.rideId = rideId;
        this.rideStatus = rideStatus;
        this.seats = seats;
        this.routeInfo = routeInfo;
        this.guests = guests;
    }

    public RideInfo(RideStatus rideStatus, Integer seats, RouteInfo routeInfo) {
        this("", rideStatus, seats, routeInfo, new LinkedList<>());
    }

    public RideInfo setGuests(List<User> guests) {
        this.guests = guests;
        return this;
    }

    public List<User> getGuests() {
        return guests;
    }

    public String getRideStatus() {
        return rideStatus.toString();
    }

    @Exclude
    public boolean isRideCancelled() {
        return rideStatus.toString().equals("CANCELLED");
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = RideStatus.valueOf(rideStatus);
    }

    public Integer getSeats() {
        return seats;
    }

    public RideInfo setSeats(Integer seats) {
        this.seats = seats;
        return this;
    }

    public RouteInfo getRouteInfo() {
        return routeInfo;
    }

    public RideInfo setRouteInfo(RouteInfo routeInfo) {
        this.routeInfo = routeInfo;
        return this;
    }

    public RideInfo addGuest(User guest, int amount) {
        guests.add(guest);
        seats -= amount;
        return this;
    }

    public RideInfo setRideId(String rideId) {
        this.rideId = rideId;
        return this;
    }

    public String getRideId() {
        return rideId;
    }
}
