package factory;

import dto.OrderDto;
import enums.OrderStatus;

import java.util.function.Function;
import com.github.javafaker.Faker;

public class OrderFactory {


    private static final Faker faker = new Faker();

    private static int randomId() {
        return faker.number().numberBetween(1, 10); // выбирай нужный диапазон
    }

    public static OrderDto valid() {
        return OrderDto.builder()
                .id(randomId())
                .petId(123)
                .quantity(1)
                .shipDate("2025-06-01T12:00:00.000+0000")
                .status(OrderStatus.placed)
                .complete(true)
                .build();
    }

    public static OrderDto valid(Integer petId) {
        return OrderDto.builder()
                .id(randomId())
                .petId(petId)
                .quantity(1)
                .shipDate("2025-06-01T12:00:00.000+0000")
                .status(OrderStatus.placed)
                .complete(true)
                .build();
    }

    public static OrderDto valid(Integer id, Integer petId) {
        return OrderDto.builder()
                .id(id)
                .petId(petId)
                .quantity(1)
                .shipDate("2025-06-01T12:00:00.000+0000")
                .status(OrderStatus.placed)
                .complete(true)
                .build();
    }

    public static OrderDto valid(Integer petId, OrderStatus status) {
        return OrderDto.builder()
                .id(randomId())
                .petId(petId)
                .quantity(1)
                .shipDate("2025-06-01T12:00:00.000+0000")
                .status(status)
                .complete(true)
                .build();
    }


    public static OrderDto with(Function<OrderDto.OrderDtoBuilder, OrderDto.OrderDtoBuilder> modifier) {
        OrderDto.OrderDtoBuilder builder = valid().toBuilder();
        return modifier.apply(builder).build();
    }
}
