package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.Review;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

public class AddReviewRequest extends Request {
    private String driverUserId;
    private String reviewerAdriaId;
    private int reviewScore;
    private String rideId;

    public AddReviewRequest(RoutingContext ctx) {
        super(ctx);
        setDriverUserId();
        setReviewerAdriaId();
        setReviewScore();
        setRideId();
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.CREATED.getStatusCode(), "Review added!");
    }

    public String getDriverUserId() {
        return driverUserId;
    }

    public String getReviewerAdriaId() {
        return reviewerAdriaId;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public String getRideId() {
        return rideId;
    }

    private void setDriverUserId() {
        this.driverUserId = ctx.getBodyAsJson().getString("userId");
    }

    private void setReviewerAdriaId() {
        this.reviewerAdriaId = getAdriaIdFromRequest();
    }

    private void setReviewScore() {
        this.reviewScore = ctx.getBodyAsJson().getInteger("reviewScore");
    }

    public void setRideId() {
        rideId = ctx.getBodyAsJson().getString("rideId");
    }
}
