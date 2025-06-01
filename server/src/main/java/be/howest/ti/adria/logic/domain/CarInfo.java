package be.howest.ti.adria.logic.domain;

public class CarInfo {

    private String brand;
    private String model;
    private int buildYear;
    private String licensePlate;

    public CarInfo(){
    }

    public CarInfo(String carBrand, String carModel, int buildYear, String carLicensePlate) {
        this.brand = carBrand;
        this.model = carModel;
        this.buildYear = buildYear;
        this.licensePlate = carLicensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public CarInfo setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CarInfo setCarModel(String model) {
        this.model = model;
        return this;
    }

    public CarInfo setBuildYear(int buildYear) {
        this.buildYear = buildYear;
        return this;
    }

    public CarInfo setCarLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }
}
