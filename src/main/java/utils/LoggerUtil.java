package utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void logResponse(String method, Response response) {
        logger.info("HTTP Method: " + method);
        logger.info("Response Code: " + response.getStatusCode());
        logger.info("Response Body: " + response.asString());
    }
}
