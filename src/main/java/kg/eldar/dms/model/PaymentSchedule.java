package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSchedule {
    Long id;
    Long creditId;
    String paymentDate;
    String loanDate;
    Double paymentAmount;
    Double penaltyAmount;
    String isPaid;
}
