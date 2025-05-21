package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static constants.CommonConstants.PROPERTIES_FILE_NAME;
import static constants.CommonConstants.PROPERTIES_PATH;

@Slf4j
public class PropertiesUtil {

    public static String getBaseUrl() {
        var properties = PropertiesUtil.loadProperties(PROPERTIES_PATH + PROPERTIES_FILE_NAME);
        return properties.containsKey("base.url") ? properties.getProperty("base.url") : null;
    }

    public static String getBasePath() {
        var properties = PropertiesUtil.loadProperties(PROPERTIES_PATH + PROPERTIES_FILE_NAME);
        return properties.containsKey("base.path") ? properties.getProperty("base.path") : null;
    }

    private static Properties loadProperties(String filePath) {
        var properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error while loading the properties file {}.\nMessage: {}", filePath, e.getMessage());
        }
        return properties;
    }
}
