package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOfSupply {

    private Long id;

    private Long supplyId;

    private String supply;

    private Double amount;

    private Double sum;

    private String createdDate;

    private Long staffId;

    private String staff;
}
