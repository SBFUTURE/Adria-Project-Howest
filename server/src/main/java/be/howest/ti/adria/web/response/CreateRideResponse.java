package be.howest.ti.adria.web.response;

import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRideResponse {
    private final String rideStatus;
    private final String rideId;
    private final String driverId;
    private final String date;
    private final Location sourceLocation;
    private final Location destinationLocation;
    private final Double pricePerSeat;
    private final int seats;

    public CreateRideResponse(RideInfo rideInfo) {
        rideStatus = rideInfo.getRideStatus();
        driverId = rideInfo.getRouteInfo().getDriverId();
        date = rideInfo.getRouteInfo().getDate();
        rideId = rideInfo.getRideId();
        sourceLocation = rideInfo.getRouteInfo().getSourceLocation();
        destinationLocation = rideInfo.getRouteInfo().getDestinationLocation();
        pricePerSeat = rideInfo.getRouteInfo().getPricePerSeat();
        seats = rideInfo.getSeats();
    }

    @JsonProperty("rideId")
    public String getRideId() {
        return rideId;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("destinationLocation")
    public Location getDestinationLocation() {
        return destinationLocation;
    }

    @JsonProperty("sourceLocation")
    public Location getSourceLocation() {
        return sourceLocation;
    }

    @JsonProperty("seats")
    public int getSeats() {
        return seats;
    }

    @JsonProperty("pricePerSeat")
    public Double getPricePerSeat() {
        return pricePerSeat;
    }

    @JsonProperty("driverId")
    public String getDriverId() {
        return driverId;
    }

    @JsonProperty("rideStatus")
    public String getRideStatus() {
        return rideStatus;
    }
}
