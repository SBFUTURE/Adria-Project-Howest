package be.howest.ti.adria.logic.controller;

import be.howest.ti.adria.logic.data.FirebaseRepository;
import be.howest.ti.adria.logic.domain.*;
import be.howest.ti.adria.logic.exceptions.UserNotFoundException;
import be.howest.ti.adria.web.response.UserInfoResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final FirebaseRepository db = FirebaseRepository.getInstance();

    public void authUser(String adriaId) {
        if (!db.adriaIdExists("citizens", adriaId)) {
            throw new UserNotFoundException("Adria ID does not exist");
        }

        if (!db.adriaIdExists("users", adriaId)) {
            Optional<Citizen> citizen = db.getCitizen(adriaId);
            if (citizen.isEmpty()) {
                throw new UserNotFoundException("User does not exist!");
            }

            db.createUser(adriaId, citizen.get().getFirstname(), citizen.get().getLastname());
        }
    }

    public List<UserInfoResponse> getUsers() {
        List<UserInfoResponse> res = new LinkedList<>();

        List<User> users = db.getUsers();

        for (User user : users) {
            res.add(new UserInfoResponse(user));
        }

        return res;
    }

    public void addCarInfo(String adriaId, CarInfo carInfo) {
        Optional<User> user = db.getUser(adriaId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        if (db.licensePlateExists(carInfo.getLicensePlate())) {
            throw new UserNotFoundException("License plate already occupied by other car");
        }

        db.updateDocument("users", user.get().getUserId(), "carInfo", carInfo);
    }

    public UserInfoResponse getUserByAdriaId(String adriaId){
        Optional<User> user = db.getUser(adriaId);
        if (user.isEmpty()) {
            LOGGER.log(Level.SEVERE, "There is no user with that id");
            throw new UserNotFoundException("Can't find the user with the specific id.");
        } else {
            return new UserInfoResponse(user.get());
        }
    }

    public UserInfoResponse getUserByUserId(String userId) throws UserNotFoundException {
        Optional<User> user = db.getUserByUserId(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Could not find user with userId: %s", userId));
        }

        return new UserInfoResponse(user.get());
    }

    public Review addReview(String rideId, String driverUserId, String reviewerAdriaId, int reviewScore) {
        Optional<User> user = db.getUser(reviewerAdriaId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Adria ID invalid");
        }

        if (reviewScore < 1 || reviewScore > 5) {
            throw new IllegalArgumentException("Score should be between 1 and 5");
        }

        Optional<RideInfo> rideInfo = db.getRide(rideId);
        if (rideInfo.isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no ride with the following rideId: %s!", rideId));
        }

        List<User> guests = rideInfo.get().getGuests();
        if (!isGuestOfRide(guests, reviewerAdriaId)) {
            throw new IllegalStateException("User is not part of the ride!");
        }

        List<Review> reviews = db.getReviews(reviewerAdriaId);
        if (reviewAlreadyExists(reviews, rideId)) {
            throw new IllegalStateException("User already reviewed this ride!");
        }

        Review review = new Review(rideId, user.get().toString(), reviewScore);
        db.addReview(review, driverUserId);

        return review;
    }

    private boolean reviewAlreadyExists(List<Review> reviews, String rideId) {
        return reviews.stream()
                .anyMatch(review -> review.getRideId().equals(rideId));
    }

    private boolean isGuestOfRide(List<User> guests, String adriaId) {
        return guests.stream()
                .anyMatch(guest -> guest.getAdriaId().equals(adriaId));
    }
}
