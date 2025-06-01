package be.howest.ti.adria.logic.domain;

import com.google.firebase.database.Exclude;

import java.util.LinkedList;
import java.util.List;

public class User {

    private String adriaId;
    private String userId;
    private String firstname;
    private String lastname;
    private CarInfo carInfo;
    private final List<Review> reviews = new LinkedList<>();

    public User() {
    }
    public User(String adriaId, String firstname, String lastname) {
        this(adriaId, firstname, lastname, null);
    }
    public User(String adriaId, String firstname, String lastname, CarInfo carInfo) {
        this.adriaId = adriaId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.carInfo = carInfo;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getAdriaId() {
        return adriaId;
    }
    @Exclude
    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public User setAdriaId(String adriaId) {
        this.adriaId = adriaId;
        return this;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    @Exclude
    public String getUserId() {
        return userId;
    }

    public User setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
        return this;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
