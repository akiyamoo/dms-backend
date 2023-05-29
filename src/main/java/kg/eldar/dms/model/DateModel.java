package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateModel {
    Integer year;
    String month;
    Long staffId;
    Double sumSalary;
}
