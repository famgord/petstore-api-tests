package tests.order;

import dto.OrderDto;
import dto.pet.PetDto;
import enums.OrderStatus;
import factory.OrderFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTest;

import java.util.stream.Stream;

import static constants.CommonConstants.ORDER_SCHEMA;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCreatePositiveTest extends BaseTest {

    private static PetDto petDto;
    private static OrderDto orderDto;

    @BeforeAll
    public static void createPet() {
        petDto = PET_HELPER.createPet();
        orderDto = OrderFactory.valid(petDto.getId());
    }

    @AfterAll
    public static void deletePet() {
        PET_HELPER.deletePetById(petDto.getId());
    }

    @Tag("positive")
    @ParameterizedTest(name = "id = {0}")
    @DisplayName("Create an order")
    @ValueSource(ints = {1, 5, 10})
    public void createOrder(Integer id) {
        orderDto.setId(id);
        storeApiClient.createOrder(orderDto)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(ORDER_SCHEMA));

        var actualOrder = ORDER_HELPER.getCreatedOrderById(orderDto.getId());

        assertEquals(orderDto, actualOrder);
    }

    @Tag("positive")
    @Test
    @DisplayName("Create an order with empty body")
    public void createOrderWithEmptyBody() {
        var order = OrderDto.builder()
                .build();
        storeApiClient.createOrder(order)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(ORDER_SCHEMA));
    }

    @Tag("positive")
    @ParameterizedTest
    @DisplayName("Create an order without fields")
    @MethodSource("orderProvider")
    public void createOrderWithoutFields(OrderDto expectedOrder) {
        storeApiClient.createOrder(expectedOrder)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(ORDER_SCHEMA));

        var actualOrder = ORDER_HELPER.getCreatedOrderById(expectedOrder.getId());
        compareAndSetFields(expectedOrder);

        assertEquals(expectedOrder, actualOrder);
    }

    @Tag("positive")
    @ParameterizedTest(name = "status = {0}, id = {1}")
    @DisplayName("Create order with different status")
    @MethodSource("orderDataProvider")
    public void createOrderWithDifferentStatus(OrderStatus status, Integer id) {
        OrderDto updatedOrder = orderDto.toBuilder()
                .id(id)
                .status(status)
                .build();

        storeApiClient.createOrder(updatedOrder)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(ORDER_SCHEMA));

        var actualOrder = ORDER_HELPER.getCreatedOrderById(orderDto.getId());

        assertEquals(orderDto, actualOrder);
    }

    private void compareAndSetFields(OrderDto expectedOrder) {
        if (expectedOrder.getPetId() == null) expectedOrder.setPetId(0);
        if (expectedOrder.getQuantity() == null) expectedOrder.setQuantity(0);
        if (expectedOrder.getComplete() == null) expectedOrder.setComplete(false);
    }

    private static Stream<Arguments> orderDataProvider() {
        return Stream.of(OrderStatus.values())
                .map(status -> {
                    OrderDto order = OrderFactory.valid(petDto.getId(), status);
                    return Arguments.of(order.getStatus(), order.getId());
                });
    }

    private static Stream<Arguments> orderProvider() {
        return Stream.of(
                Arguments.arguments(OrderFactory.with(b -> b.complete(null))),
                Arguments.arguments(OrderFactory.with(b -> b.petId(null))),
                Arguments.arguments(OrderFactory.with(b -> b.quantity(null))),
                Arguments.arguments(OrderFactory.with(b -> b.shipDate(null)))
        );
    }
}
