package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class GetRoutesRequest extends Request {

    public GetRoutesRequest(RoutingContext ctx) {
        super(ctx);
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), Map.of(
                "message", "this is working"
        ));
    }
}
