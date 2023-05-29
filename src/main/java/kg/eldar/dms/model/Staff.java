package kg.eldar.dms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Staff {

    private Long id;

    private String fio;

    private Long positionId;

    private String position;

    private Double salary;

    private String address;

    private String phoneNumber;
}
