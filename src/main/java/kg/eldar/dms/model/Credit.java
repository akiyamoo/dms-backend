package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {
    Long id;
    String target;
    Double sumLoan;
    String loanDate;
    Double percentLoan;
    Double percentPenalty;
    Double sumRemaining;
    Integer countYear;
    String isPaid;
}
