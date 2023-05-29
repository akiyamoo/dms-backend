package kg.eldar.dms.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Product {

    private Long id;

    private String name;

    private Long measurementId;

    private String measurement;

    private Double amount;

    private Double sum;
}
