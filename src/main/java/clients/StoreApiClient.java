package clients;

import dto.OrderDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StoreApiClient {

    private final RequestSpecification requestSpecification = RequestSpecificationBuilder.getBaseRequestSpecification();

    public Response createOrder(OrderDto order) {
        return given().spec(requestSpecification)
                .body(order)
                .when().log().all()
                .post(BackendEndpoints.Store.ORDER);
    }

    public Response getOrderById(Integer id) {
        return given().spec(requestSpecification)
                .pathParam("id", id)
                .when().log().all()
                .get(BackendEndpoints.Store.ORDER_ID);
    }

    public Response deleteOrder(Integer id) {
        return given().spec(requestSpecification)
                .pathParam("id", id)
                .when().log().all()
                .delete(BackendEndpoints.Store.ORDER_ID);
    }
}
