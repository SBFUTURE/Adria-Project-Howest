package be.howest.ti.adria.web.response;

import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Locale;

public class GetAllRidesResponse {

    private final String rideStatus;
    private final String rideId;
    private final String driverId;
    private final String driverName;
    private final Location sourceLocation;
    private final Location destinationLocation;
    private final int availableSeats;
    private final double pricePerSeat;
    private final String date;

    public GetAllRidesResponse(String id, RideInfo rideInfo) {
        rideStatus = rideInfo.getRideStatus();
        rideId = id;
        driverId = rideInfo.getRouteInfo().getDriverId();
        driverName = rideInfo.getRouteInfo().getDriverName();
        sourceLocation = rideInfo.getRouteInfo().getSourceLocation();
        destinationLocation = rideInfo.getRouteInfo().getDestinationLocation();
        availableSeats = rideInfo.getSeats();
        pricePerSeat = rideInfo.getRouteInfo().getPricePerSeat();
        date = rideInfo.getRouteInfo().getDate();
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("rideId")
    public String getRideId() {
        return rideId;
    }

    @JsonProperty("driverId")
    public String getDriverId() {
        return driverId;
    }

    @JsonProperty("source")
    public Location getSourceLocation() {
        return sourceLocation;
    }

    @JsonProperty("destination")
    public Location getDestinationLocation() {
        return destinationLocation;
    }

    @JsonProperty("availableSeats")
    public int getAvailableSeats() {
        return availableSeats;
    }

    @JsonProperty("pricePerSeat")
    public double getPricePerSeat() {
        return pricePerSeat;
    }

    @JsonProperty("driverName")
    public String getDriverName() {
        return driverName;
    }

    @JsonProperty("rideStatus")
    public String getRideStatus() {
        return rideStatus;
    }

}
