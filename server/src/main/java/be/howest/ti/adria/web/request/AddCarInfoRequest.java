package be.howest.ti.adria.web.request;

import be.howest.ti.adria.logic.domain.CarInfo;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import be.howest.ti.adria.web.response.ResponseCode;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class AddCarInfoRequest extends Request {

    private String adriaId;
    private CarInfo carInfo;

    public AddCarInfoRequest(RoutingContext ctx) {
        super(ctx);
        setAdriaIdFromSession();
        setCarInfoFromRequest();
    }

    private void setAdriaIdFromSession() {
        adriaId = getAdriaIdFromRequest();
    }

    private void setCarInfoFromRequest() {
        String carBrand = ctx.getBodyAsJson().getString("carBrand");
        String carModel = ctx.getBodyAsJson().getString("carModel");
        String carLicensePlate = ctx.getBodyAsJson().getString("carLicensePlate");
        int carBuildYear = Integer.parseInt(ctx.getBodyAsJson().getString("carBuildYear"));

        if (carBrand == null || carModel == null) {
            Response.sendFailure(ctx, 400, "Bad request");
            return;
        }

        carInfo = new CarInfo(carBrand, carModel, carBuildYear, carLicensePlate);
    }

    public String getAdriaId() {
        return adriaId;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    @Override
    public void sendResponse() {
        Response.sendJsonResponse(ctx, ResponseCode.MODIFIED.getStatusCode(), Map.of(
                "message", "Car info added!"
        ));
    }

}
