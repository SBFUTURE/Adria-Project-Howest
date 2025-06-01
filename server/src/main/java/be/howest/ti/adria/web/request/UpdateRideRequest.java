package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.RideStatus;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.CreateRideResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class UpdateRideRequest extends Request {

    private String rideId;
    private String adriaId;
    private RideStatus rideStatus;

    public UpdateRideRequest(RoutingContext ctx) {
        super(ctx);
        setRideId();
        setRideStatus();
        setAdriaId();
    }

    private void setRideId() {
        rideId = ctx.pathParam("rideId");
    }

    public String getRideId() {
        return rideId;
    }

    private void setAdriaId() {
        adriaId = getAdriaIdFromRequest();
    }

    public String getAdriaId() {
        return adriaId;
    }

    private void setRideStatus() {
        rideStatus = RideStatus.valueOf(ctx.pathParam("status"));
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.MODIFIED.getStatusCode(), "");
    }

}
