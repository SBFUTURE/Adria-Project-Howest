package be.howest.ti.adria.web;

import be.howest.ti.adria.logic.data.Repositories;
import be.howest.ti.adria.web.bridge.OpenApiBridge;
import be.howest.ti.adria.web.bridge.RtcBridge;
import be.howest.ti.adria.web.middleware.AuthMiddleware;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer extends AbstractVerticle {
    private static final String REALTIME_COMM_URI = "/events/*";
    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());
    private Promise<Void> startPromise;
    private final OpenApiBridge openApiBridge;
    private final RtcBridge rtcBridge;

    public WebServer(OpenApiBridge bridge, RtcBridge rtcBridge) {
        this.openApiBridge = bridge;
        this.rtcBridge = rtcBridge;
    }

    public WebServer() {
        this(new OpenApiBridge(), new RtcBridge());
    }

    @Override
    public void start(Promise<Void> startPromise) {
        this.startPromise = startPromise;
        ConfigRetriever.create(vertx).getConfig()
                .onFailure(cause -> shutDown("Failed to load configuration", cause))
                .onSuccess(configuration -> {
                    LOGGER.log(Level.INFO, "Configuration loaded: {0}", configuration);
                    final int port = configuration.getJsonObject("http").getInteger("port");
                    LOGGER.log(Level.INFO, "Server will be listening at port {0}", port);

                    RouterBuilder.create(vertx, configuration.getJsonObject("api").getString("url"))
                            .onFailure(cause -> shutDown("Failed to load API specification", cause))
                            .onSuccess(routerBuilder -> {
                                LOGGER.log(Level.INFO, "API specification loaded: {0}",
                                        routerBuilder.getOpenAPI().getOpenAPI().getJsonObject("info").getString("version"));

                                Repositories.init();
                                LOGGER.log(Level.INFO, "Firebase initialized");


                                Router mainRouter = Router.router(vertx);
                                mainRouter.route().handler(createCorsHandler());
                                mainRouter.route(REALTIME_COMM_URI).handler(rtcBridge.getSockJSHandler(vertx));

                                Router openApiRouter = openApiBridge.buildRouter(routerBuilder);
                                mainRouter.mountSubRouter("/", openApiRouter);

                                vertx.createHttpServer()
                                        .requestHandler(mainRouter)
                                        .listen(port)
                                        .onFailure(cause -> shutDown("Failed to start server", cause))
                                        .onSuccess(server -> {
                                            LOGGER.log(Level.INFO, "Server is listening on port: {0}", server.actualPort());
                                            startPromise.complete();
                                        });
                            });

                });
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

    private void shutDown(String quote, Throwable cause) {
        LOGGER.log(Level.SEVERE, quote, cause);
        LOGGER.info("Shutting down");
        vertx.close();
        startPromise.fail(cause);
    }

    @Override
    public void stop() {
        Repositories.shutdown();
    }
}