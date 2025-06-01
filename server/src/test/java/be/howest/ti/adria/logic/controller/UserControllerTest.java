package be.howest.ti.adria.logic.controller;

import be.howest.ti.adria.logic.domain.CarInfo;
import be.howest.ti.adria.web.response.UserInfoResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void authUser_existingAdriaId_shouldNotThrowException() {

        UserController userController = new UserController();

        assertDoesNotThrow(() -> userController.authUser("adria-0"));
    }

    @Test
    void authUser_nonExistingCitizenAdriaId_shouldThrowException() {
        UserController userController = new UserController();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userController.authUser("nonExistingAdriaId"));

        assertEquals("Adria ID does not exist", exception.getMessage());
    }

    @Test
    void getUsers_shouldReturnListOfUserInfoResponse() {

        UserController userController = new UserController();

        // Act
        List<UserInfoResponse> result = userController.getUsers();

        // Assert
        assertTrue(result.size() > 0);
    }

    @Test
    void addCarInfo_existingUser_shouldNotThrowException() {

        UserController userController = new UserController();

        CarInfo carInfo = createCarInfo();

        assertDoesNotThrow(() -> userController.addCarInfo("adria-3", carInfo));
    }

    @Test
    void addCarInfo_nonExistingUser_shouldThrowException() {
        UserController userController = new UserController();
        CarInfo carInfo = createCarInfo();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userController.addCarInfo("nonExistingAdriaId", carInfo));

        assertEquals("User does not exist", exception.getMessage());
    }

    private CarInfo createCarInfo() {
        CarInfo carInfo = new CarInfo();
        carInfo.setBrand("mustang");
        carInfo.setCarModel("The best");
        carInfo.setCarLicensePlate("ABC123");
        carInfo.setBuildYear(2001);
        return carInfo;
    }
}
