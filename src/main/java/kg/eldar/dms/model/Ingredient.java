package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient {

    private Long id;

    private Long productId;

    private String product;

    private Long supplyId;

    private String supply;

    private Double amount;
}
