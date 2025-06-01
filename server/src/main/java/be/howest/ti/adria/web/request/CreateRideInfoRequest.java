package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.CreateRideResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;
import org.apache.http.protocol.HTTP;

import java.time.LocalDate;

public class CreateRideInfoRequest extends Request {

    private Location sourceLocation;
    private Location destinationLocation;
    private String adriaId;
    private double price;
    private int seats;
    private CreateRideResponse response;

    private LocalDate date;

    public CreateRideInfoRequest(RoutingContext ctx) {
        super(ctx);
        setSourceLocation();
        setDestinationLocation();
        setAdriaId();
        setPrice();
        setSeats();
        setDate();
    }

    private void setSeats() {
        seats = ctx.getBodyAsJson().getInteger("seats");
    }

    private void setPrice() {
        price = ctx.getBodyAsJson().getDouble("price");
    }

    private void setAdriaId() {
        this.adriaId = getAdriaIdFromRequest();
    }

    private void setSourceLocation() {
        String city = ctx.getBodyAsJson().getJsonObject("source").getString("city");
        String cityCode = ctx.getBodyAsJson().getJsonObject("source").getString("cityCode");
        String homeNumber = ctx.getBodyAsJson().getJsonObject("source").getString("homeNumber");
        String street = ctx.getBodyAsJson().getJsonObject("source").getString("street");

        sourceLocation = new Location(city, cityCode, street, homeNumber);
    }

    private void setDestinationLocation() {
        String city = ctx.getBodyAsJson().getJsonObject("destination").getString("city");
        String cityCode = ctx.getBodyAsJson().getJsonObject("destination").getString("cityCode");
        String homeNumber = ctx.getBodyAsJson().getJsonObject("destination").getString("homeNumber");
        String street = ctx.getBodyAsJson().getJsonObject("destination").getString("street");

        destinationLocation = new Location(city, cityCode, street, homeNumber);
    }

    public void setDate() {
        String dateValue = ctx.getBodyAsJson().getString("date");

        if (dateValue == null || dateValue.isEmpty()) {
            date = LocalDate.now();
            return;
        }

        date = LocalDate.parse(dateValue);
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public String getAdriaId() {
        return adriaId;
    }

    public double getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setResponse(RideInfo rideInfo) {
        response = new CreateRideResponse(rideInfo);
    }
    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.CREATED.getStatusCode(), response);
    }

}
