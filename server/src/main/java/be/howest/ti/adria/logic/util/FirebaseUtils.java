package be.howest.ti.adria.logic.util;

import be.howest.ti.adria.logic.domain.CarInfo;
import be.howest.ti.adria.logic.domain.RideInfo;
import be.howest.ti.adria.logic.domain.User;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseUtils {

    private static final String[] fakeUsersNames = new String[] { "Stephen Bombeke", "Evarist Verstraete", "Luka Impossible", "Lowie Moeyaert" };

    public static void createDummyData(Firestore db) throws ExecutionException, InterruptedException {
        CollectionReference userReference = db.collection("users");
        if (collectionEmpty(userReference)) {
            createFakeUsers(userReference);
        }

        CollectionReference citizensReference = db.collection("citizens");
        if (collectionEmpty(citizensReference)) {
            createFakeCitizens(citizensReference);
        }
    }

    private static boolean collectionEmpty(CollectionReference collectionReference) throws ExecutionException, InterruptedException {
        return collectionReference.limit(1).get().get().isEmpty();
    }
    private static void createFakeUsers(CollectionReference userReference) {
        User jordyBadiscoRider = new User("adria-3", "Jordy", "Badisco", new CarInfo("Ford Mustang", "GT Premium Fastback", 2000, "ABC123"));
        jordyBadiscoRider.setUserId("HuuYo8FKJv2hIAoyZpDu"); //static id for testing
        userReference.document(jordyBadiscoRider.getUserId()).set(jordyBadiscoRider);
        userReference.document().set(new User("adria-69", "Jimmy", "Johnson", new CarInfo("Audi", "RS6", 2019, "1-ABC-123")));

        for (int i = 0; i < fakeUsersNames.length; i++) {
            String adriaId = String.format("adria-%d", i);
            String firstname = fakeUsersNames[i].split(" ")[0];
            String lastname = fakeUsersNames[i].split(" ")[1];
            userReference.document().set(new User(adriaId, firstname, lastname));
        }
    }

    private static void createFakeCitizens(CollectionReference citizensReference) {
        for (int i = 0; i < fakeUsersNames.length; i++) {
            citizensReference.document(String.format("adria-%d", i)).create(Map.of(
                    "firstname", fakeUsersNames[i].split(" ")[0],
                    "lastname", fakeUsersNames[i].split(" ")[1]
            ));
        }

        citizensReference.document("adria-69").create(Map.of(
                "firstname", "Jimmy",
                "lastname", "Johnson"
        ));
    }

    public static boolean isGuest(User guest, String adriaId) {
        return guest.getAdriaId().equals(adriaId);
    }

    public static boolean isDriver(RideInfo rideInfo, String userId) {
        return rideInfo.getRouteInfo().getDriverId().equals(userId);
    }
}
