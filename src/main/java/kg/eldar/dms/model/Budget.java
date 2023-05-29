package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Budget {

    private Long id;

    private Double sumOfBudget;

    private Double percent;

    private Double bonus;
}
