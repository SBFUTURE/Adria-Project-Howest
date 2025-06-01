package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.GetAllRidesResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class GetUserRidesRequest extends Request {

    private String adriaId;
    private List<GetAllRidesResponse> response;

    public GetUserRidesRequest(RoutingContext ctx) {
        super(ctx);
        setAdriaId();
    }

    private void setAdriaId() {
        adriaId = getAdriaIdFromRequest();
    }

    public String getAdriaId() {
        return adriaId;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), response);
    }

    public void setResponse(List<GetAllRidesResponse> response) {
        this.response = response;
    }

}
