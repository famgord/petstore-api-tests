package helpers;

import clients.StoreApiClient;
import com.github.javafaker.Faker;
import dto.OrderDto;
import factory.OrderFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderHelper {

    private final StoreApiClient api = new StoreApiClient();
    private final Faker faker = new Faker();

    public OrderDto createAndVerifyOrder(Integer petId) {
        var id = faker.random().nextInt(1, 10);
        return getCreatedOrderDtoByPetIdAndId(petId, id);
    }

    public OrderDto createAndVerifyOrder(Integer id, Integer petId) {
        return getCreatedOrderDtoByPetIdAndId(petId, id);
    }

    public OrderDto getCreatedOrderById(Integer id) {
        log.info("Getting order by ID: {}", id);

        var actual = api.getOrderById(id)
                .as(OrderDto.class);

        log.info("Fetched order: {}", actual.getId());
        return actual;
    }

    private OrderDto getCreatedOrderDtoByPetIdAndId(Integer petId, Integer id) {
        var orderDto = OrderFactory.valid(id, petId);

        log.info("Sending order: {}", orderDto.getId());

        api.createOrder(orderDto);

        var actual = getCreatedOrderById(orderDto.getId());
        log.info("Order successfully created: {}", actual.getId());
        return actual;
    }
}
