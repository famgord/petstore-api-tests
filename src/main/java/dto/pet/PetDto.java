package dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import enums.PetStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public class PetDto {

    private Integer id;
    private CategoryDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagDto> tags;
    private PetStatus status;
}
