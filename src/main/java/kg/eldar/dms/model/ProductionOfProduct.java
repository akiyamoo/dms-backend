package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionOfProduct {

    private Long id;

    private Long productId;

    private String product;

    private Double amount;

    private String createdDate;

    private Long staffId;

    private String staff;
}
