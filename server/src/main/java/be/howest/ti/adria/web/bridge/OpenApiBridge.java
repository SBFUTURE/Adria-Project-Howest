package be.howest.ti.adria.web.bridge;

import be.howest.ti.adria.logic.controller.RideController;
import be.howest.ti.adria.logic.controller.UserController;
import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.domain.Location;
import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.logic.domain.RideStatus;
import be.howest.ti.adria.logic.exceptions.ClassConvertionException;
import be.howest.ti.adria.logic.exceptions.NoRidesFoundException;
import be.howest.ti.adria.logic.exceptions.UserNotFoundException;
import be.howest.ti.adria.web.exceptions.MalformedRequestException;
import be.howest.ti.adria.web.middleware.AuthMiddleware;
import be.howest.ti.adria.web.request.*;
import be.howest.ti.adria.web.response.GetAllRidesResponse;
import be.howest.ti.adria.web.response.ResponseCode;
import be.howest.ti.adria.web.response.UserInfoResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * In the AdriaOpenApiBridge class you will create one handler-method per API operation.
 * The job of the "bridge" is to bridge between JSON (request and response) and Java (the controller).
 * <p>
 * For each API operation you should get the required data from the `Request` class.
 * The Request class will turn the HTTP request data into the desired Java types (int, String, Custom class,...)
 * This desired type is then passed to the controller.
 * The return value of the controller is turned to Json or another Web data type in the `Response` class.
 */
public class OpenApiBridge {
    private static final Logger LOGGER = Logger.getLogger(OpenApiBridge.class.getName());
    private final UserController userController;
    private final RideController rideController;

    public Router buildRouter(RouterBuilder routerBuilder) {
        // Adding session support
        LOGGER.log(Level.INFO, "Setting up session handler for all operations");

        LOGGER.log(Level.INFO, "Installing cors handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing failure handlers for all operations");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing handler for: getRideInfo");
        routerBuilder.operation("getRideInfo").handler(AuthMiddleware::authorization).handler(ctx -> getRideInfo(new GetRideInfoRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: updateRide");
        routerBuilder.operation("updateRide").handler(AuthMiddleware::authorization).handler(ctx -> updateRideStatus(new UpdateRideRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: getMyInfo");
        routerBuilder.operation("getMyInfo").handler(AuthMiddleware::authorization).handler(ctx -> getMyInfo(new GetMyInfoRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: getUserInfoById");
        routerBuilder.operation("getUserInfoById").handler(AuthMiddleware::authorization).handler(ctx -> getUserInfoById(new GetUserInfoByIdRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: getAllRides");
        routerBuilder.operation("getRides").handler(AuthMiddleware::authorization).handler(ctx -> getAllRides(new GetAllRidesRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: addReview");
        routerBuilder.operation("addReview").handler(AuthMiddleware::authorization).handler(ctx -> addReview(new AddReviewRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: authUser");
        routerBuilder.operation("authUser").handler(ctx -> authUser(new AuthUserRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: getUsers");
        routerBuilder.operation("getUsers").handler(AuthMiddleware::authorization).handler(ctx -> getUsers(new GetUsersRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: addCarInfo");
        routerBuilder.operation("addCarInfo").handler(AuthMiddleware::authorization).handler(ctx -> addCarInfo(new AddCarInfoRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: createRide");
        routerBuilder.operation("createRide").handler(AuthMiddleware::authorization).handler(ctx -> createRide(new CreateRideInfoRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: planRide");
        routerBuilder.operation("planRide").handler(AuthMiddleware::authorization).handler(ctx -> planRide(new PlanRideRequest(ctx)));

        LOGGER.log(Level.INFO, "Installing handler for: getUserRides");
        routerBuilder.operation("getUserRides").handler(AuthMiddleware::authorization).handler(ctx -> getUserRides(new GetUserRidesRequest(ctx)));

        LOGGER.log(Level.INFO, "All handlers are installed, creating router.");

        return routerBuilder.createRouter();
    }

    private void getUserRides(GetUserRidesRequest request) {
        try {
            String adriaId = request.getAdriaId();
            List<GetAllRidesResponse> rides = rideController.getRides(adriaId);
            request.setResponse(rides);
            request.sendResponse();
        } catch (NoRidesFoundException e) {
            Response.sendFailure(request.ctx, ResponseCode.SERVER_ERROR.getStatusCode(), e.getMessage());
        }
    }

    private void planRide(PlanRideRequest request) {
        try {
            Location startPoint = request.getStartPoint();
            Location endPoint = request.getEndPoint();
            Date date = request.getDate();
            int amount = request.getAmount();
            String driverId = request.getDriverId();
            String guestAdriaID = request.getGuestAdriaId();

            RideInfo rideInfo = rideController.planRide(startPoint, endPoint, date, amount, driverId, guestAdriaID);
            request.setRideInfo(rideInfo);
            request.sendResponse();
        } catch (IllegalArgumentException | IllegalStateException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    private void createRide(CreateRideInfoRequest request) {
        try {
            Location sourceLocation = request.getSourceLocation();
            Location destinationLocation = request.getDestinationLocation();
            String adriaId = request.getAdriaId();
            double price = request.getPrice();
            int seats = request.getSeats();
            LocalDate date = request.getDate();

            RideInfo rideInfo = rideController.createRide(adriaId, sourceLocation, destinationLocation, price, seats, date);
            request.setResponse(rideInfo);
            request.sendResponse();
        } catch (IllegalArgumentException | IllegalStateException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    public void getRideInfo(GetRideInfoRequest request) {
        try {
            String rideId = request.ctx.request().getParam("rideId");
            RideInfo rideInfo = rideController.getRide(rideId);
            request.setRidInfo(rideInfo).sendResponse();
        } catch (NoRidesFoundException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    public void updateRideStatus(UpdateRideRequest request) {
        String adriaId = request.getAdriaId();
        String rideId = request.getRideId();
        RideStatus rideStatus = request.getRideStatus();

        rideController.updateRide(adriaId, rideId, rideStatus);

        request.sendResponse();
    }

    public void getMyInfo(GetMyInfoRequest request) {
        try {
            String adriaId = request.getAdriaIdFromRequest();
            request.setUserInfoResponse(userController.getUserByAdriaId(adriaId));
            request.sendResponse();
        } catch (IllegalArgumentException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    public void getUserInfoById(GetUserInfoByIdRequest request) {
        try {
            String userId = request.getUserId();
            request.setResponse(userController.getUserByUserId(userId));
            request.sendResponse();
        } catch (UserNotFoundException | ClassConvertionException e) {
            Response.sendFailure(request.ctx, ResponseCode.SERVER_ERROR.getStatusCode(), e.getMessage());
        }
    }

    public void getUsers(GetUsersRequest request) {
        List<UserInfoResponse> users = userController.getUsers();
        request.setUsers(users);
        request.sendResponse();
    }

    public void getAllRides(GetAllRidesRequest request) {
        try {
            List<GetAllRidesResponse> rides = rideController.getRides();

            request.setResponse(rides);
            request.sendResponse();
        } catch (RuntimeException e) {
            Response.sendFailure(request.ctx, ResponseCode.SERVER_ERROR.getStatusCode(), e.getMessage());
        }
    }

    public void addReview(AddReviewRequest request) {
        String driverUserId = request.getDriverUserId();
        String reviewerAdriaId = request.getReviewerAdriaId();
        int reviewScore = request.getReviewScore();
        String rideId = request.getRideId();

        try {
            userController.addReview(rideId, driverUserId, reviewerAdriaId, reviewScore);
            request.sendResponse();
        } catch (IllegalArgumentException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    public void authUser(AuthUserRequest request) {
        try {
            userController.authUser(request.getAdriaId());
            request.sendResponse();
        } catch (IllegalStateException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    public void addCarInfo(AddCarInfoRequest request) {
        try {
            userController.addCarInfo(request.getAdriaId(), request.getCarInfo());
            request.sendResponse();
        } catch (IllegalStateException e) {
            Response.sendFailure(request.ctx, ResponseCode.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();
        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Failed request", cause);
        if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof MalformedRequestException) {
            code = 400;
        } else if (cause instanceof NoSuchElementException) {
            code = 404;
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.")
                .allowedHeader("x-requested-with")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowCredentials(true)
                .allowedHeader("origin")
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization")
                .allowedHeader("accept")
                .allowedMethod(HttpMethod.HEAD)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.PATCH)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT);
    }

    public OpenApiBridge() {
        this.userController = new UserController();
        this.rideController = new RideController();
    }
}
