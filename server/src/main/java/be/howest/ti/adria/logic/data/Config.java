package be.howest.ti.adria.logic.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private final Properties config = new Properties();
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private final static Config instance = new Config();

    private Config() {
        try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
            config.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read config.properties file", e);
        }
    }

    public static Config getInstance() {
        return instance;
    }

    public String getKey(String key) {
        return config.getProperty(key);
    }
}
