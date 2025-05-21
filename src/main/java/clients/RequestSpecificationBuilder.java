package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.PropertiesUtil;

public class RequestSpecificationBuilder {

    public static RequestSpecification getBaseRequestSpecification() {
        final var baseUrl = PropertiesUtil.getBaseUrl();
        final var basePath = PropertiesUtil.getBasePath();
        if (baseUrl == null) {
            throw new RuntimeException("Base url not set");
        }
        if (basePath == null) {
            throw new RuntimeException("Base path not set");
        }
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .build();
    }
}
