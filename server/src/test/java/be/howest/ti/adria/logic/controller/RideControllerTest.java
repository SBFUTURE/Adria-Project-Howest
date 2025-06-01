package be.howest.ti.adria.logic.controller;

import be.howest.ti.adria.TestHelper;
import be.howest.ti.adria.WebClientAuth;
import be.howest.ti.adria.logic.data.FirebaseRepository;
import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.logic.domain.RideStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class RideControllerTest {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final FirebaseRepository db = FirebaseRepository.getInstance();
    private final RideController rideController = new RideController();

    @Test
    void createRide() {
        AtomicReference<RideInfo> rideInfo = new AtomicReference(null);
        assertDoesNotThrow(() -> {
            rideInfo.set(rideController.createRide(
                            TestHelper.getDriverAdriaId(),
                            TestHelper.createLocation(),
                            TestHelper.createLocation(),
                            1.0D,
                            1,
                            LocalDate.now()
                    )
            );
        });
        assertNotNull(rideInfo.get());
    }

    @Test
    void planRide() {
        RideInfo rideInfo = rideController.createRide(TestHelper.getDriverAdriaId(), TestHelper.createLocation(), TestHelper.createLocation(), 1.0D, 100, LocalDate.now());
        assertDoesNotThrow(() -> rideController.planRide(
                rideInfo.getRouteInfo().getSourceLocation(),
                rideInfo.getRouteInfo().getDestinationLocation(),
                new Date(LocalDate.now()),
                1,
                rideInfo.getRouteInfo().getDriverId(),
                TestHelper.getAdriaId()
        ));
    }

    @Test
    void getRides() {
        assertDoesNotThrow(() -> rideController.getRides());
    }

    @Test
    void cancelRide() {
        RideInfo rideInfo = rideController.createRide(
                TestHelper.getDriverAdriaId(),
                TestHelper.createLocation(),
                TestHelper.createLocation(),
                1.0D,
                100,
                LocalDate.now()
        );
        rideController.updateRide(TestHelper.getDriverAdriaId(), rideInfo.getRideId(), RideStatus.CANCELLED);
    }

    @Test
    void getRide() {
        WebClientAuth webClientAuth = new WebClientAuth();
        TestHelper.createRide(webClientAuth).onSuccess(r -> {
            String rideId = r.bodyAsJsonObject().getString("rideId");
            RideInfo rideInfo = rideController.getRide(rideId);
            assertNotNull(rideInfo);
        }).onFailure(throwable -> {
            fail("Test failed due to a error in createRide.");
        });
    }
}
