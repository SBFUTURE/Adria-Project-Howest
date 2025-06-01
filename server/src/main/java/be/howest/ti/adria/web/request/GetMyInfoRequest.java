package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import be.howest.ti.adria.web.response.UserInfoResponse;
import io.vertx.ext.web.RoutingContext;

public class GetMyInfoRequest extends Request {

    private UserInfoResponse response;

    public GetMyInfoRequest(RoutingContext ctx) {
        super(ctx);
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), response);
    }

    public void setUserInfoResponse(UserInfoResponse user) {
        this.response = user;
    }

}
