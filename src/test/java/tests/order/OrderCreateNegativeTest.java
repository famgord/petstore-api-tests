package tests.order;

import dto.OrderDto;
import factory.OrderFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTest;

import static constants.CommonConstants.COMMON_SCHEMA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrderCreateNegativeTest extends BaseTest {

    private static OrderDto orderDto;

    @BeforeAll
    public static void createPet() {
        orderDto = OrderFactory.valid();
    }

    @Tag("negative")
    @Test
    @DisplayName("Create an order with non-existing pet id")
    public void createOrderWithNonExistingPetId() {
        orderDto.setPetId(Integer.MAX_VALUE);
        storeApiClient.createOrder(orderDto)
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath(COMMON_SCHEMA));
    }

    @Tag("negative")
    @ParameterizedTest(name = "{0}")
    @DisplayName("Create an order with invalid id")
    @ValueSource(ints = {-1, 0, 11})
    public void createOrderWithInvalidId(Integer id) {
        orderDto.setId(id);
        storeApiClient.createOrder(orderDto)
                .then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath(COMMON_SCHEMA));
    }
}
