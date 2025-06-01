package be.howest.ti.adria.logic.domain;

public class Citizen {
    private String firstname;
    private String lastname;
    private String adriaId;

    public Citizen() {
    }

    public Citizen(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Citizen setAdriaId(String adriaId) {
        this.adriaId = adriaId;
        return this;
    }

    public Citizen setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public Citizen setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
