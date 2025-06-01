package be.howest.ti.adria;

import be.howest.ti.adria.logic.controller.UserController;
import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.domain.Location;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.sql.Statement;
import java.time.LocalDate;

public class TestHelper {
    public static final int PORT = 8080;
    public static final String HOST = "localhost";
    public static Future<HttpResponse<Buffer>> loginWebClient(WebClient webClient) {
        return loginWebClient(webClient, getDriverAdriaId());
    }

    public static Future<HttpResponse<Buffer>> loginWebClient(WebClient webClient, String user) {
        JsonObject json = new JsonObject().put("adriaId", user);
        return webClient.post(PORT, HOST, "/api/user/auth").sendJsonObject(json);
    }

    public static Future<HttpResponse<Buffer>> createRide(WebClient webClient) {
        return createRide(webClient, createRideAsJson());
    }

    public static Future<HttpResponse<Buffer>> createRide(WebClientAuth webClient) {
        return createRide(webClient, createRideAsJson());
    }

    public static Future<HttpResponse<Buffer>> createRide(WebClient webClient, JsonObject json) {
        return webClient.post(PORT, HOST, "/api/ride/create").sendJsonObject(json);
    }

    public static Future<HttpResponse<Buffer>> createRide(WebClientAuth webClient, JsonObject json) {
        return webClient.post(PORT, HOST, "/api/ride/create").sendJsonObject(json);
    }

    public static JsonObject createRideGuestAsJson() {
        return new JsonObject()
                .put("startPoint", createLocationAsJson())
                .put("endPoint", createLocationAsJson())
                .put("date", createDateAsJson())
                .put("amount", 1)
                .put("driverId", getDriverId());
    }


    public static JsonObject createReviewAsJson(String rideId) {
        return new JsonObject()
                .put("userId", getDriverId())
                .put("reviewScore", 5)
                .put("reviewText", "Jordy is the best driver ever")
                .put("rideId", rideId);
    }

    public static JsonObject createLocationAsJson() {
        return new JsonObject()
                .put("cityCode", "8200")
                .put("street", "Rijselstraat")
                .put("homeNumber", "5")
                .put("city", "brugge");
    }
    public static Location createLocation() {
        Location location = new Location();
        location.setCityCode("8200");
        location.setCity("Brugge");
        location.setStreet("Rijselstraat");
        location.setHomeNumber("5");
        return location;
    }

    public static JsonObject createDateAsJson() {
        return new JsonObject()
                .put("day", 1)
                .put("month", 1)
                .put("year", 2085);
    }

    public static JsonObject createRideAsJson() {
        return new JsonObject()
                .put("source", createLocationAsJson())
                .put("destination", createLocationAsJson())
                .put("seats", 100)
                .put("price", 1.0D);
    }

    public static Date createDate() {
        return new Date(LocalDate.now());
    }

    public static JsonObject createDateAsJsonObject(String date) {
        String[] parts = date.split("-");
        return new JsonObject()
                .put("year", Integer.parseInt(parts[0]))
                .put("month", Integer.parseInt(parts[1]))
                .put("day", Integer.parseInt(parts[2]));
    }

    public static String getDriverId() {
        return "HuuYo8FKJv2hIAoyZpDu"; // jordy's static driverId look at logic.utils.FirebaseUtils::createFakeUsers
    }

    public static String getUserId() {
        return getDriverId();
    }

    public static String getAdriaId() {
        return "adria-0";
    }

    public static String getDriverAdriaId() {
        return "adria-3";
    }
}
