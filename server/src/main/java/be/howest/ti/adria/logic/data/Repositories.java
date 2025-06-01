package be.howest.ti.adria.logic.data;

import be.howest.ti.adria.logic.exceptions.RepositoryException;
import io.vertx.core.json.JsonObject;

public class Repositories {
    private static FirebaseRepository firebaseRepository = null;
    private Repositories() {
    }

    public static void init() {
        firebaseRepository = FirebaseRepository.getInstance();
    }

    public static FirebaseRepository getFirebaseRepository() {
        if (firebaseRepository == null)
            throw new RepositoryException("FirebaseRepository not configured.");

        return firebaseRepository;
    }

    public static void shutdown() {
        if (firebaseRepository != null)
            firebaseRepository.cleanUp();

        firebaseRepository = null;
    }
}
