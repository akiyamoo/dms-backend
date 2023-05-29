package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleOfProduct {

    private Long id;

    private Long productId;

    private String product;

    private Double amount;

    private Double sum;

    private String createdDate;

    private Long staffId;

    private String staff;
}
