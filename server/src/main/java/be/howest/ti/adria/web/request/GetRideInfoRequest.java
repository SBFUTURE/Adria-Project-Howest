package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class GetRideInfoRequest extends Request {
    private RideInfo rideInfo;
    public GetRideInfoRequest(RoutingContext ctx) {
        super(ctx);
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), Map.of(
                "message", "this works!"
        ));
    }

    public GetRideInfoRequest setRidInfo(RideInfo rideInfo) {
        this.rideInfo = rideInfo;
        return this;
    }
}
