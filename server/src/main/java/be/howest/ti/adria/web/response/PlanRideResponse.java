package be.howest.ti.adria.web.response;

import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;

public class PlanRideResponse {
    private final String date;
    private final String rideId;
    private final Location sourceDestination;
    private final Location destinationLocation;
    private final Double pricePerSeat;
    private final int seats;

    public PlanRideResponse(RideInfo rideInfo) {
        date = rideInfo.getRouteInfo().getDate();
        rideId = rideInfo.getRideId();
        sourceDestination = rideInfo.getRouteInfo().getSourceLocation();
        destinationLocation = rideInfo.getRouteInfo().getDestinationLocation();
        pricePerSeat = rideInfo.getRouteInfo().getPricePerSeat();
        seats = rideInfo.getSeats();
    }

    public String getRideId() {
        return rideId;
    }
    public String getDate() {
        return date;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public Location getSourceDestination() {
        return sourceDestination;
    }

    public int getSeats() {
        return seats;
    }

    public Double getPricePerSeat() {
        return pricePerSeat;
    }
}
