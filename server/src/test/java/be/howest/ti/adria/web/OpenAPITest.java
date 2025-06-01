package be.howest.ti.adria.web;

import be.howest.ti.adria.TestHelper;
import be.howest.ti.adria.WebClientAuth;
import be.howest.ti.adria.logic.data.Repositories;
import be.howest.ti.adria.web.bridge.OpenApiBridge;
import be.howest.ti.adria.web.bridge.RtcBridge;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;

import static be.howest.ti.adria.TestHelper.HOST;
import static be.howest.ti.adria.TestHelper.PORT;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.AvoidDuplicateLiterals"})
/*
 * PMD.JUnitTestsShouldIncludeAssert: VertxExtension style asserts are marked as false positives.
 * PMD.AvoidDuplicateLiterals: Should all be part of the spec (e.g., urls and names of req/res body properties, ...)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenAPITest {
    public static final String MSG_200_EXPECTED = "If all goes right, we expect a 200 status";
    public static final String MSG_201_EXPECTED = "If a resource is successfully created.";
    public static final String MSG_204_EXPECTED = "If a resource is successfully deleted";
    private Vertx vertx;
    private WebClient webClient;
    private WebClientAuth authWebClient;

    @BeforeAll
    void deploy(final VertxTestContext testContext) {
        Repositories.shutdown();
        vertx = Vertx.vertx();

        WebServer webServer = new WebServer(new OpenApiBridge(), new RtcBridge());
        vertx.deployVerticle(
                webServer,
                testContext.succeedingThenComplete()
        );
        webClient = WebClient.create(vertx);
        authWebClient = new WebClientAuth(vertx);
    }

    @AfterAll
    void close(final VertxTestContext testContext) {
        vertx.close(testContext.succeedingThenComplete());
        webClient.close();
        Repositories.shutdown();
    }

//    @Test
//    void postApiUserAuth(final VertxTestContext testContext) {
//        JsonObject json = new JsonObject().put("adriaId", "adria-0");
//        webClient.post(PORT, HOST, "/api/user/auth").sendJsonObject(json)
//                .onFailure(testContext::failNow)
//                .onSuccess(response -> testContext.verify(() -> {
//                    assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
//                    assertTrue(
//                            StringUtils.isNotBlank(response.bodyAsJsonObject().getString("message")),
//                            "User authenticated!"
//                    );
//                    testContext.completeNow();
//                }));
//    }

    @Test
    void postApiUserAuthMalformed(final VertxTestContext testContext) {
        webClient.post(PORT, HOST, "/api/user/auth").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }


    @Test
    void getApiUsers(final VertxTestContext testContext) {
        authWebClient.get(PORT, HOST, "/api/users").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
                    assertFalse(response.bodyAsJsonArray().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiUsersNotAuth(final VertxTestContext testContext) {
        webClient.get(PORT, HOST, "/api/users").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiUserInfo(final VertxTestContext testContext) {
        authWebClient.get(PORT, HOST, "/api/user/info/adria-3").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
                    assertEquals("Jordy Badisco", response.bodyAsJsonObject().getString("name"));
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiUserInfoMalformed(final VertxTestContext testContext) {
        String userId = "fakeId";
        authWebClient.get(PORT, HOST, "/api/user/info/" + userId).send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiUserInfoNotAuth(final VertxTestContext testContext) {
        webClient.get(PORT, HOST, "/api/user/info/" + TestHelper.getAdriaId()).send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiUserCarInfo(final VertxTestContext testContext) {
        JsonObject json = new JsonObject()
                .put("carBrand", "Ford Mustang")
                .put("carModel", "GT Premium Fastback")
                .put("carBuildYear", 2000)
                .put("carLicensePlate", "ABC123");

        authWebClient.post(PORT, HOST, "/api/user/carinfo").sendJsonObject(json)
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiUserCarInfoMalformed(final VertxTestContext testContext) {
        authWebClient.post(PORT, HOST, "/api/user/carinfo").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiUserCarInfoNotAuth(final VertxTestContext testContext) {
        JsonObject json = new JsonObject()
                .put("carBrand", "Ford Mustang")
                .put("carModel", "GT Premium Fastback")
                .put("carBuildYear", 2000)
                .put("carLicensePlate", "ABC123");

        webClient.post(PORT, HOST, "/api/user/carinfo").sendJsonObject(json)
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiRideInfo(final VertxTestContext testContext) {
        TestHelper.createRide(authWebClient).onSuccess(r -> {
            String rideId = r.bodyAsJsonObject().getString("rideId");
            authWebClient.get(PORT, HOST, "/api/ride/info/" + rideId).send()
                    .onFailure(testContext::failNow)
                    .onSuccess(response -> testContext.verify(() -> {
                        assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
                        assertFalse(response.bodyAsJsonObject().isEmpty());
                        testContext.completeNow();
                    }));
        }).onFailure(testContext::failNow);
    }

    @Test
    void getApiRideInfoMalformed(final VertxTestContext testContext) {
        String rideId = "NotAId";
        authWebClient.get(PORT, HOST, "/api/ride/info/" + rideId).send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    assertFalse(response.bodyAsJsonObject().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiRideInfoNotAuth(final VertxTestContext testContext) {
        TestHelper.createRide(authWebClient).onSuccess(r -> {
                    String rideId = r.bodyAsJsonObject().getString("rideId");
                    webClient.get(PORT, HOST, "/api/ride/info/" + rideId).send()
                            .onFailure(testContext::failNow)
                            .onSuccess(response -> testContext.verify(() -> {
                                assertEquals(403, response.statusCode());
                                assertFalse(response.bodyAsJsonObject().isEmpty());
                                testContext.completeNow();
                            }));
                })
                .onFailure(testContext::failNow);
    }

    @Test
    void getApiRides(final VertxTestContext testContext) {
        authWebClient.get(PORT, HOST, "/api/rides").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(200, response.statusCode(), MSG_200_EXPECTED);
                    assertFalse(response.bodyAsJsonArray().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void getApiRidesNotAuth(final VertxTestContext testContext) {
        webClient.get(PORT, HOST, "/api/rides").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    assertFalse(response.bodyAsJsonObject().isEmpty());
                    testContext.completeNow();
                }));
    }

    //@Test
    //void postApiUserReview(final VertxTestContext testContext) {
    //    TestHelper.createRide(authWebClient).onSuccess(r -> {
    //        authWebClient.setTempAdriaId(TestHelper.getAdriaId()).post(PORT, HOST, "/api/ride/plan").sendJsonObject(TestHelper.createRideGuestAsJson()).onSuccess(r2 -> {
    //            authWebClient.setTempAdriaId(TestHelper.getAdriaId()).post(PORT, HOST, "/api/user/review").sendJsonObject(TestHelper.createReviewAsJson(r.bodyAsJsonObject().getString("rideId"))).onFailure(testContext::failNow).onSuccess(response -> testContext.verify(() -> {
    //                assertEquals(201, response.statusCode());
    //                testContext.completeNow();
    //            }));
    //        });
    //    }).onFailure(testContext::failNow);
    //}

    //@Test
    //void postApiUserReviewNoRide(final VertxTestContext testContext) {
        // TODO: the endpoint is not yet done for this part
    //    TestHelper.createRide(authWebClient).onSuccess(r -> {
    //        authWebClient.setTempAdriaId("adria-2").post(PORT, HOST, "/api/user/review").sendJsonObject(TestHelper.createReviewAsJson(r.bodyAsJsonObject().getString("rideId")))
    //                .onFailure(testContext::failNow)
    //               .onSuccess(response -> testContext.verify(() -> {
    //                   assertEquals(400, response.statusCode());
    //                  testContext.completeNow();
    //              }));
    //  }).onFailure(testContext::failNow);
    //}

    @Test
    void postApiUserReviewNoAuth(final VertxTestContext testContext) {
        TestHelper.createRide(authWebClient).onSuccess(r -> {
            webClient.post(PORT, HOST, "/api/user/review").sendJsonObject(TestHelper.createReviewAsJson(r.bodyAsJsonObject().getString("rideId")))
                    .onFailure(testContext::failNow)
                    .onSuccess(response -> testContext.verify(() -> {
                        assertEquals(403, response.statusCode());
                        testContext.completeNow();
                    }));
        }).onFailure(testContext::failNow);
    }

    @Test
    void postApiUserReviewMalformed(final VertxTestContext testContext) {
        authWebClient.post(PORT, HOST, "/api/user/review").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiRideCreate(final VertxTestContext testContext) {
        authWebClient.post(PORT, HOST, "/api/ride/create").sendJsonObject(TestHelper.createRideAsJson())
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(201, response.statusCode());
                    assertFalse(response.bodyAsJsonObject().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiRideCreateNotAuth(final VertxTestContext testContext) {
        webClient.post(PORT, HOST, "/api/ride/create").sendJsonObject(TestHelper.createRideAsJson())
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    assertFalse(response.bodyAsJsonObject().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiRoutePlan(final VertxTestContext testContext) {
        TestHelper.createRide(authWebClient).onFailure(testContext::failNow).onSuccess(r -> {
            JsonObject resultJson = r.bodyAsJsonObject();
            JsonObject json = new JsonObject()
                    .put("startPoint", resultJson.getJsonObject("sourceDestination"))
                    .put("endPoint", resultJson.getJsonObject("destinationLocation"))
                    .put("amount", 1)
                    .put("date", TestHelper.createDateAsJsonObject(resultJson.getString("date")))
                    .put("driverId", resultJson.getString("driverId"));
            authWebClient.post(PORT, HOST, "/api/ride/plan").sendJsonObject(json)
                    .onFailure(testContext::failNow)
                    .onSuccess(response -> testContext.verify(() -> {
                        assertEquals(201, response.statusCode(), MSG_200_EXPECTED);
                        assertFalse(response.bodyAsJsonObject().isEmpty());
                        testContext.completeNow();
                    }));
        });
    }

    @Test
    void postApiRoutePlanNotAuth(final VertxTestContext testContext) {
        JsonObject json = new JsonObject()
                .put("startPoint", TestHelper.createLocationAsJson())
                .put("endPoint", TestHelper.createLocationAsJson())
                .put("amount", 1)
                .put("date", TestHelper.createDateAsJson())
                .put("driverId", TestHelper.getDriverId());
        webClient.post(PORT, HOST, "/api/ride/plan").sendJsonObject(json)
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(403, response.statusCode());
                    assertFalse(response.bodyAsJsonObject().isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    void postApiRoutePlanMalformed(final VertxTestContext testContext) {
        authWebClient.post(PORT, HOST, "/api/ride/plan").send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

}