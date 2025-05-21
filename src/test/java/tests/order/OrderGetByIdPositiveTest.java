package tests.order;

import dto.OrderDto;
import dto.pet.PetDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.BaseTest;

import java.util.stream.Stream;

import static constants.CommonConstants.ORDER_SCHEMA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderGetByIdPositiveTest extends BaseTest {

    private static PetDto petDto;

    @BeforeAll
    public static void createPet() {
        petDto = PET_HELPER.createPet();
    }

    @AfterAll
    public static void deletePet() {
        PET_HELPER.deletePetById(petDto.getId());
    }

    @Tag("positive")
    @ParameterizedTest(name = "id = {0}")
    @DisplayName("Get order by id")
    @MethodSource("orderProvider")
    public void getOrderById(Integer id, OrderDto order) {
        var actualOrder = storeApiClient.getOrderById(order.getId())
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(ORDER_SCHEMA))
                .extract().body().as(OrderDto.class);


        assertEquals(order, actualOrder);
    }

    private static Stream<Arguments> orderProvider() {
        return Stream.of(
                Arguments.arguments(1, ORDER_HELPER.createAndVerifyOrder(1, petDto.getId())),
                Arguments.arguments(5, ORDER_HELPER.createAndVerifyOrder(5, petDto.getId())),
                Arguments.arguments(10, ORDER_HELPER.createAndVerifyOrder(10, petDto.getId()))
        );
    }
}
