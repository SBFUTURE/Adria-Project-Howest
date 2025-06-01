package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import be.howest.ti.adria.web.response.UserInfoResponse;
import io.vertx.ext.web.RoutingContext;

public class GetUserInfoByIdRequest extends Request {

    private String userId;
    private UserInfoResponse response;

    public GetUserInfoByIdRequest(RoutingContext ctx) {
        super(ctx);
        setUserId();
    }

    private void setUserId() {
        userId = ctx.pathParam("userId");
    }

    public String getUserId() {
        return userId;
    }

    public void setResponse(UserInfoResponse response) {
        this.response = response;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), response);
    }
}
