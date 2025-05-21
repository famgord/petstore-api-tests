package factory;

import dto.pet.CategoryDto;
import dto.pet.PetDto;
import dto.pet.TagDto;
import enums.PetStatus;

import java.util.List;
import java.util.function.Function;

public class PetFactory {

    public static PetDto valid() {
        return PetDto.builder()
                .id(2)
                .category(validCategory())
                .name("doggie")
                .photoUrls(List.of("https://example.com/dog.jpg"))
                .tags(List.of(validTag()))
                .status(PetStatus.available)
                .build();
    }

    public static PetDto with(Function<PetDto.PetDtoBuilder, PetDto.PetDtoBuilder> modifier) {
        var builder = valid().toBuilder();
        return modifier.apply(builder).build();
    }

    private static CategoryDto validCategory() {
        return CategoryDto.builder()
                .id(1)
                .name("Dogs")
                .build();
    }

    private static TagDto validTag() {
        return TagDto.builder()
                .id(100)
                .name("Grom")
                .build();
    }
}
