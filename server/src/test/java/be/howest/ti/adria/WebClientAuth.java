package be.howest.ti.adria;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

public class WebClientAuth {
    private WebClient webClient;
    private String defaultUser;
    private String tempUser;

    private boolean isTemp=false;

    public WebClientAuth() {
        this(Vertx.vertx());
    }

    public WebClientAuth(Vertx vertx) {
        this.webClient = WebClient.create(vertx);
        this.defaultUser = TestHelper.getAdriaId();
    }

    public WebClientAuth setAdriaId(String id) {
        this.defaultUser=id;
        return this;
    }

    public WebClientAuth setTempAdriaId(String id) {
        this.tempUser = id;
        this.isTemp=true;
        return this;
    }

    public HttpRequest<Buffer> get(int port, String host, String path) {
        return addAuthHeader(this.webClient.get(port, host, path));
    }

    public HttpRequest<Buffer> post(int port, String host, String path) {
        return addAuthHeader(this.webClient.post(port, host, path));
    }

    public HttpRequest<Buffer> addAuthHeader(HttpRequest<Buffer> request, String user) {
        return request.putHeader("Authorization", user);
    }

    public HttpRequest<Buffer> addAuthHeader(HttpRequest<Buffer> request) {
        if (!isTemp) return addAuthHeader(request, this.defaultUser);
        this.isTemp=false;
        return addAuthHeader(request, this.defaultUser);
    }
}
