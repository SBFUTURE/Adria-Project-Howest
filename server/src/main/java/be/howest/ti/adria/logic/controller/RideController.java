package be.howest.ti.adria.logic.controller;

import be.howest.ti.adria.logic.data.FirebaseRepository;
import be.howest.ti.adria.logic.domain.*;
import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.exceptions.NoRidesFoundException;
import be.howest.ti.adria.web.response.GetAllRidesResponse;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class RideController {
    private static final Logger LOGGER = Logger.getLogger(RideController.class.getName());
    private final FirebaseRepository db = FirebaseRepository.getInstance();
    
    public RideInfo createRide(String adriaId, Location sourceLocation, Location destinationLocation, double price, int seats, LocalDate date) {
        Optional<User> user = db.getUser(adriaId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        if (seats <= 0) {
            throw new IllegalArgumentException("Invalid number of seats entered");
        }

        if (user.get().getCarInfo() == null) {
            throw new IllegalStateException("User is not a driver");
        }

        RideInfo rideInfo = new RideInfo(RideStatus.ACTIVE, seats, new RouteInfo(user.get().getUserId(), user.get().toString(), price, sourceLocation, destinationLocation, date));
        return db.createRide(rideInfo);
    }

    public RideInfo planRide(Location startPoint, Location endPoint, Date date, int amount, String driverId, String guestAdriaId) {
        Optional<RideInfo> rideInfoOptional = db.getRide(driverId, startPoint, endPoint, date);

        if (rideInfoOptional.isEmpty()) {
            throw new IllegalArgumentException("No ride available!");
        }

        if (rideInfoOptional.get().isRideCancelled()) {
            throw new IllegalStateException("This ride has been cancelled!");
        }

        if (rideInfoOptional.get().getSeats() < amount) {
            throw new IllegalStateException("Not enough seats available!");
        }

        Optional<User> guest = db.getUser(guestAdriaId);
        if (guest.isEmpty()) {
            throw new IllegalArgumentException(String.format("User with adriaId %s does not exist!", guestAdriaId));
        }

        RideInfo rideInfo = rideInfoOptional.get().addGuest(guest.get(), amount);

        db.updateRide(driverId, startPoint, endPoint, date, rideInfo);
        return rideInfo;
    }

    public List<GetAllRidesResponse> getRides() {
        List<GetAllRidesResponse> res = new LinkedList<>();
        Map<String, RideInfo> rides = db.getRides();

        for (String key : rides.keySet()) {
            res.add(new GetAllRidesResponse(key, rides.get(key)));
        }

        return res;
    }

    public List<GetAllRidesResponse> getRides(String adriaId) throws NoRidesFoundException {
        List<GetAllRidesResponse> res = new LinkedList<>();
        Map<String, RideInfo> rides = db.getRides(adriaId);

        for (String key : rides.keySet()) {
            res.add(new GetAllRidesResponse(key, rides.get(key)));
        }

        return res;
    }

    public void updateRide(String adriaId, String rideId, RideStatus rideStatus) {
        Optional<User> user = db.getUser(adriaId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user.");
        }

        Optional<RideInfo> rideInfo = db.getRide(rideId);
        if (rideInfo.isEmpty()) {
            throw new RuntimeException("Could not find ride!");
        }
        
        if (!user.get().getUserId().equals(rideInfo.get().getRouteInfo().getDriverId())) {
            throw new IllegalStateException("Only the driver can cancel a ride!");
        }

        rideInfo.get().setRideStatus(rideStatus.toString());
        db.updateRide(rideId, rideInfo.get());
    }

    public RideInfo getRide(String rideId) {
        Optional<RideInfo> rideInfo = db.getRide(rideId);
        if (rideInfo.isEmpty()) throw new NoRidesFoundException("No ride found with the following adriaId!");
        return rideInfo.get();
    }
}
