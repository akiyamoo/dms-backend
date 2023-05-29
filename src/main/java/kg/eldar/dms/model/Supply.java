package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supply {

    private Long id;

    private String name;

    private Long measurementId;

    private String measurement;

    private Double amount;

    private Double sum;
}
