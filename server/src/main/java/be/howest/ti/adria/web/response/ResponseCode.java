package be.howest.ti.adria.web.response;

public enum ResponseCode {
    SUCCESS(200),
    BAD_REQUEST(400),
    CREATED(201),
    MODIFIED(204),
    SERVER_ERROR(500);
    private final int statusCode;

    ResponseCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
