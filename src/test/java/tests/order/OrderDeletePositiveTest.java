package tests.order;

import dto.OrderDto;
import dto.pet.PetDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import tests.BaseTest;

import static constants.CommonConstants.COMMON_SCHEMA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDeletePositiveTest extends BaseTest {

    private static PetDto petDto;
    private static OrderDto orderDto;

    @BeforeAll
    public static void createPet() {
        petDto = PET_HELPER.createPet();
        orderDto = ORDER_HELPER.createAndVerifyOrder(petDto.getId());
    }

    @AfterAll
    public static void deletePet() {
        PET_HELPER.deletePetById(petDto.getId());
    }

    @Tag("positive")
    @Test
    @DisplayName("Delete an order")
    public void deleteOrder() {
        storeApiClient.deleteOrder(orderDto.getId())
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(COMMON_SCHEMA));

        var response = storeApiClient.getOrderById(orderDto.getId());

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
    }
}
