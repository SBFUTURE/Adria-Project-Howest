package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.PlanRideResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class PlanRideRequest extends Request {

    private Location startPoint;
    private Location endPoint;
    private Date date;
    private int amount;
    private String driverId;
    private String guestAdriaId;
    private RideInfo rideInfo;

    public PlanRideRequest(RoutingContext ctx) {
        super(ctx);
        setStartPoint();
        setEndPoint();
        setDate();
        setAmount();
        setDriverId();
        setGuestAdriaId();
    }

    private void setGuestAdriaId() {
        guestAdriaId = getAdriaIdFromRequest();
    }

    void setStartPoint() {
        String city = ctx.getBodyAsJson().getJsonObject("startPoint").getString("city");
        String cityCode = ctx.getBodyAsJson().getJsonObject("startPoint").getString("cityCode");
        String street = ctx.getBodyAsJson().getJsonObject("startPoint").getString("street");
        String homeNumber = ctx.getBodyAsJson().getJsonObject("startPoint").getString("homeNumber");

        startPoint = new Location(city, cityCode, street, homeNumber);
    }

    void setEndPoint() {
        String city = ctx.getBodyAsJson().getJsonObject("endPoint").getString("city");
        String cityCode = ctx.getBodyAsJson().getJsonObject("endPoint").getString("cityCode");
        String street = ctx.getBodyAsJson().getJsonObject("endPoint").getString("street");
        String homeNumber = ctx.getBodyAsJson().getJsonObject("endPoint").getString("homeNumber");

        endPoint = new Location(city, cityCode, street, homeNumber);
    }

    void setDate() {
        int day = ctx.getBodyAsJson().getJsonObject("date").getInteger("day");
        int month = ctx.getBodyAsJson().getJsonObject("date").getInteger("month");
        int year = ctx.getBodyAsJson().getJsonObject("date").getInteger("year");

        date = new Date(day, month, year);
    }

    void setAmount() {
        amount = ctx.getBodyAsJson().getInteger("amount");
    }

    void setDriverId() {
        driverId = ctx.getBodyAsJson().getString("driverId");
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public Location getEndPoint() {
        return endPoint;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getGuestAdriaId() {
        return guestAdriaId;
    }

    public void setRideInfo(RideInfo rideInfo) {
        this.rideInfo = rideInfo;
    }
    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.CREATED.getStatusCode(), new PlanRideResponse(rideInfo));
    }
}
