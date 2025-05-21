package tests.order;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTest;

import static constants.CommonConstants.COMMON_SCHEMA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrderGetByIdNegativeTest extends BaseTest {

    @Tag("negative")
    @Test
    @DisplayName("Get order by non-existing id")
    public void getOrderByNonExistingId() {
        storeApiClient.getOrderById(Integer.MAX_VALUE)
                .then().log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(matchesJsonSchemaInClasspath(COMMON_SCHEMA));
    }

    @Tag("negative")
    @ParameterizedTest(name = "id = {0}")
    @DisplayName("Get order by invalid id")
    @ValueSource(ints = {-1, 0, 11})
    public void getOrderByInvalidId(Integer id) {
        storeApiClient.getOrderById(id)
                .then().log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(matchesJsonSchemaInClasspath(COMMON_SCHEMA));
    }
}
