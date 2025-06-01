package be.howest.ti.adria.web.response;

import be.howest.ti.adria.logic.domain.CarInfo;
import be.howest.ti.adria.logic.domain.Review;
import be.howest.ti.adria.logic.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class UserInfoResponse {

    private final String userId;
    private final String name;
    private final Map<String, String> carInfo;

    private final List<Review> reviews;


    public UserInfoResponse(User user) {
        this.userId = user.getUserId();
        this.name = user.getFirstname() + " " + user.getLastname();
        this.carInfo = parseCarInfo(user.getCarInfo());
        this.reviews = user.getReviews();
    }

    private Map<String, String> parseCarInfo(CarInfo carInfo) {
        if (carInfo == null) return null;
        return Map.of(
                "carBrand", carInfo.getBrand(),
                "carModel", carInfo.getModel(),
                "carBuildYear", Integer.toString(carInfo.getBuildYear())
        );
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("carInfo")
    public Map<String, String> getCarInfo() {
        return carInfo;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("reviews")
    public List<Review> getReviews() {
        return reviews;
    }
}
