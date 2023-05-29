package kg.eldar.dms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Salary {
    Long staffId;
    String staff;
    Double sumSalary;
    Integer purchaseCount;
    Integer productionCount;
    Integer saleCount;
    Integer sumCount;
    Double bonus;
    Integer year;
    String month;
    Double salary;
    String isIssued;
}