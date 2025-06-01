package be.howest.ti.adria.web.request;

import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import be.howest.ti.adria.web.response.UserInfoResponse;
import io.vertx.ext.web.RoutingContext;
import java.util.LinkedList;
import java.util.List;

public class GetUsersRequest extends Request {

    public GetUsersRequest(RoutingContext ctx) {
        super(ctx);
    }

    public List<UserInfoResponse> users = new LinkedList<>();

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.SUCCESS.getStatusCode(), users);
    }

    public void setUsers(List<UserInfoResponse> users) {
        this.users = users;
    }

}
