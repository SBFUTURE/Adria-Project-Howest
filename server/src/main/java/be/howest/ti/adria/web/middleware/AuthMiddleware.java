package be.howest.ti.adria.web.middleware;

import be.howest.ti.adria.logic.data.FirebaseRepository;
import be.howest.ti.adria.web.bridge.Request;
import be.howest.ti.adria.web.bridge.Response;
import io.vertx.ext.web.RoutingContext;
import java.util.logging.Logger;

public class AuthMiddleware {

    private static final Logger LOGGER = Logger.getLogger(AuthMiddleware.class.getName());

    public static void authorization(RoutingContext ctx) {
        String adriaId = Request.getAdriaIdFromRequest(ctx);
        if (adriaId == null) {
            Response.sendFailure(ctx, 403, "Forbidden");
            return;
        }

        if (!FirebaseRepository.getInstance().adriaIdExists("citizens", adriaId)) {
            Response.sendFailure(ctx, 403, "Forbidden");
        }

        ctx.next();
    }
}
