package be.howest.ti.adria.logic.data;

import be.howest.ti.adria.logic.domain.*;
import be.howest.ti.adria.logic.domain.Date;
import be.howest.ti.adria.logic.exceptions.ClassConvertionException;
import be.howest.ti.adria.logic.exceptions.NoRidesFoundException;
import be.howest.ti.adria.logic.exceptions.RepositoryException;
import be.howest.ti.adria.logic.exceptions.UserNotFoundException;
import be.howest.ti.adria.logic.util.FirebaseUtils;
import be.howest.ti.adria.web.response.UserInfoResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseRepository {

    private static Firestore db;
    private static final FirebaseRepository instance = new FirebaseRepository();
//    private static final Logger LOGGER = Logger.getLogger(FirebaseRepository.class.getName());

    private FirebaseRepository() {
        try {
            InputStream serviceAccount = getClass().getResourceAsStream("/service_account.json");

            FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            db = firestoreOptions.getService();
            FirebaseUtils.createDummyData(db);
        } catch (IOException | InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.SEVERE, "Could not connect to firebase!", e);
            throw new RepositoryException("Could not connect to firebase!");
        }
    }

    public static FirebaseRepository getInstance() {
        return instance;
    }

    public void updateDocument(String collectionPath, String documentId, String fieldName, Object data) {
        db.collection(collectionPath).document(documentId).update(Map.of(
                fieldName, data
        ));
    }

    public boolean adriaIdExists(String collectionPath, String adriaId) {
        try {
            if (collectionPath.equals("users")) {
                return !db.collection(collectionPath).whereEqualTo("adriaId", adriaId).get().get().isEmpty();
            } else if (collectionPath.equals("citizens")) {
                return db.collection(collectionPath).document(adriaId).get().get().exists();
            }
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not check if adriaId exists", e);
            throw new RepositoryException("Could not check if adriaId exists");
        }

        return false;
    }

    public void createUser(String adriaId, String firstname, String lastname) {
        db.collection("users").document().set(new User(adriaId, firstname, lastname));
    }

    public Optional<Citizen> getCitizen(String adriaId) {
        try {
            DocumentSnapshot citizenSnapshot = db.collection("citizens").document(adriaId).get().get();
            if (!citizenSnapshot.exists()) {
                return Optional.empty();
            }

            return Optional.ofNullable(citizenSnapshot.toObject(Citizen.class));
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not retrieve citizen from adriaId", e);
        }

        return Optional.empty();
    }

    public List<User> getUsers() {
        List<User> res = new LinkedList<>();
        try {
            QuerySnapshot usersSnapshot = db.collection("users").get().get();
            List<QueryDocumentSnapshot> users = usersSnapshot.getDocuments();
            for (QueryDocumentSnapshot user : users) {
               User parsedUser = user.toObject(User.class);
               parsedUser.setUserId(user.getId());
               res.add(parsedUser);
           }
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not retrieve users from Firestore");
        }

        return res;
    }

    public Optional<User> getUser(String adriaId) {
        Optional<User> res = Optional.empty();

        try {
            QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("adriaId", adriaId).get().get();
            List<QueryDocumentSnapshot> r = querySnapshot.getDocuments();
            if (!r.isEmpty()) {
                User user = r.get(0).toObject(User.class);
                user.setUserId(r.get(0).getId());
                res = Optional.of(user);
            }
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not retrieve user from Firestore with adriaId", e);
        }

        return res;
    }

    public Optional<User> getUserByUserId(String userId) {
        Optional<User> res;

        try {
            DocumentSnapshot documentSnapshot = db.collection("users").document(userId).get().get();
            if (!documentSnapshot.exists()) {
                throw new UserNotFoundException(String.format("Could not find user with userId: %s", userId));
            }

            User user = documentSnapshot.toObject(User.class);
            if (user == null) throw new ClassConvertionException("Something went wrong when fetching the user!");
            user.setUserId(documentSnapshot.getId());

            res = Optional.of(user);

        } catch (InterruptedException e) {
//            LOGGER.log(Level.SEVERE, "Looking up user by userId got interrupted", e);
            throw new UserNotFoundException("Could not find user!");
        } catch (ExecutionException e) {
//            LOGGER.log(Level.SEVERE, "Could not send query to Firebase", e);
            throw new UserNotFoundException("Could not find user!");
        }


        return res;
    }

    public boolean licensePlateExists(String licensePlate) {
        try {
            QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("carInfo.carLicensePlate", licensePlate).get().get();
            return !querySnapshot.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not check if license plate exists", e);
            return false;
        }
    }

    public boolean addReview(Review review, String userId) {
        try {
            DocumentSnapshot documentSnapshot = db.collection("users").document(userId).get().get();
            List<Review> reviews = (List<Review>) documentSnapshot.get("reviews");
            if (reviews == null) {
                reviews = new LinkedList<>();
                reviews.add(review);
            } else {
                reviews.add(review);
            }

            db.collection("users").document(userId).update(Map.of(
                    "reviews", reviews
            ));
            return true;
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not add review to user", e);
            return false;
        }
    }
    public RideInfo createRide(RideInfo rideInfo) {
        try {
            DocumentReference documentReference = db.collection("rides").document();
            rideInfo.setRideId(documentReference.get().get().getId());
            documentReference.set(rideInfo);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return rideInfo;

    }

    public Optional<RideInfo> getRide(String driverId, Location startPoint, Location endPoint, Date date) {
        try {
            QuerySnapshot querySnapshot = db.collection("rides")
                    .whereEqualTo("routeInfo.driverId", driverId)
                    .whereEqualTo("routeInfo.sourceLocation", startPoint)
                    .whereEqualTo("routeInfo.destinationLocation", endPoint)
                    .whereEqualTo("routeInfo.date", date.toString()).get().get();

            if (querySnapshot.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(querySnapshot.getDocuments().get(0).toObject(RideInfo.class));
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    public void updateRide(String driverId, Location startPoint, Location endPoint, Date date, RideInfo newRideInfo) {
        try {
            QuerySnapshot querySnapshot = db.collection("rides")
                    .whereEqualTo("routeInfo.driverId", driverId)
                    .whereEqualTo("routeInfo.sourceLocation", startPoint)
                    .whereEqualTo("routeInfo.destinationLocation", endPoint)
                    .whereEqualTo("routeInfo.date", date.toString()).get().get();

            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                db.collection("rides").document(documentSnapshot.getId()).set(newRideInfo);
            }

        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not update ride", e);
        }
    }

    public void updateRide(String rideId, RideInfo newRideInfo) {
        db.collection("rides").document(rideId).set(newRideInfo);
    }

    public Map<String, RideInfo> getRides() {
        try {
            Map<String, RideInfo> res = new HashMap<>();
            QuerySnapshot querySnapshot = db.collection("rides").get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                RideInfo rideInfo = document.toObject(RideInfo.class);
                String rideId = document.getId();
                rideInfo.setRideId(rideId);
                res.put(rideId, rideInfo);
            }

            return res;
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not get rides from database", e);
            throw new RuntimeException("Could not retrieve rides");
        }
    }

    public Map<String, RideInfo> getRides(String adriaId) {
        Optional<User> user = getUser(adriaId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist!");
        }

        try {
            Map<String, RideInfo> res = new HashMap<>();
            List<RideInfo> rides = db.collection("rides").get().get().toObjects(RideInfo.class);

            for (RideInfo ride : rides) {
                List<User> guests = ride.getGuests();
                for (User guest : guests) {
                    if (FirebaseUtils.isGuest(guest, adriaId)) res.put(ride.getRideId(), ride);
                }

                if (FirebaseUtils.isDriver(ride, user.get().getUserId())) res.put(ride.getRideId(), ride);
            }

            return res;
        } catch (InterruptedException e) {
//            LOGGER.log(Level.SEVERE, "Retrieving rides got interrupted", e);
            throw new NoRidesFoundException("Could not retrieve rides!");
        } catch (ExecutionException e) {
//            LOGGER.log(Level.SEVERE, "Could not execute query to Firebase", e);
            throw new NoRidesFoundException("Could not retrieve rides!");
        }
    }

    public Optional<RideInfo> getRide(String rideId) {
        Optional<RideInfo> res = Optional.empty();

        try {
            DocumentSnapshot documentSnapshot = db.collection("rides").document(rideId).get().get();
            if (!documentSnapshot.exists()) {
                return res;
            }

            res = Optional.ofNullable(documentSnapshot.toObject(RideInfo.class));
        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.log(Level.WARNING, "Could not lookup ride", e);
        }

        return res;
    }

    public List<Review> getReviews(String adriaId) {
        List<Review> reviews = new LinkedList<>();

        Optional<User> user = getUser(adriaId);
        if (user.isPresent()) {
            reviews = user.get().getReviews();
        }

        return reviews;
    }

    public void cleanUp() {
        // TODO: Implement cleaning up on closing firestore
    }

}
