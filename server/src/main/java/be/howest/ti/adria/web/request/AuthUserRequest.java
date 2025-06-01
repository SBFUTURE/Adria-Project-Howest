package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class AuthUserRequest extends Request {

    // TODO: Refactor logic for authentication

    private String adriaId;

    public AuthUserRequest(RoutingContext ctx) {
        super(ctx);
        setAdriaId();
    }

    private void setAdriaId() {
        this.adriaId = ctx.getBodyAsJson().getString("adriaId");
    }

    public String getAdriaId() {
        return adriaId;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), Map.of(
                "message", "User authenticated!"
        ));
    }

}
