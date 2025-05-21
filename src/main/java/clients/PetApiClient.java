package clients;

import dto.pet.PetDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetApiClient {

    private final RequestSpecification requestSpecification = RequestSpecificationBuilder.getBaseRequestSpecification();

    public Response createPet(PetDto pet) {
        return given().spec(requestSpecification)
                .body(pet)
                .when().log().all()
                .post(BackendEndpoints.Pet.PET);
    }

    public Response getPetById(Integer id) {
        return given().spec(requestSpecification)
                .pathParam("id", id)
                .when().log().all()
                .get(BackendEndpoints.Pet.PET_ID);
    }

    public Response deletePet(Integer id) {
        return given().spec(requestSpecification)
                .pathParam("id", id)
                .when().log().all()
                .delete(BackendEndpoints.Pet.PET_ID);
    }
}
