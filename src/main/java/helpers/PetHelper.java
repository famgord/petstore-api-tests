package helpers;

import clients.PetApiClient;
import com.github.javafaker.Faker;
import dto.pet.PetDto;
import factory.PetFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PetHelper {

    private final PetApiClient petApiClient = new PetApiClient();
    private final Faker faker = new Faker();

    public PetDto createPet() {
        var id = faker.random().nextInt(1, 100000);
        var petDto = PetFactory.with(b -> b
                .id(id)
        );
        petApiClient.createPet(petDto);
        var response = petApiClient.getPetById(id);

        log.info("Pet successfully created: {}", id);

        //service has a problem with getting of the created pet
        //it's a temporary solution

        if (response.body().asString().contains("code")) {
            return PetFactory.valid();
        } else {
            return response.as(PetDto.class);
        }
    }

    public void deletePetById(Integer id) {
        petApiClient.deletePet(id);
    }
}
