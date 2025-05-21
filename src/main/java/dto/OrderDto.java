package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public class OrderDto {

    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private OrderStatus status;
    private Boolean complete;
}
