package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.GetAllRidesResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.LinkedList;
import java.util.List;

public class GetAllRidesRequest extends Request {

    private List<GetAllRidesResponse> response = new LinkedList<>();

    public GetAllRidesRequest(RoutingContext ctx) {
        super(ctx);
    }

    public void setResponse(List<GetAllRidesResponse> response) {
        this.response = response;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), response);
    }
}
