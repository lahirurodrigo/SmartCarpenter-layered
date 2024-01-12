package lk.ijse.SmartCarpenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalaryDto {

    private String sId;
    private String eId;
    private int month;
    private double amount;

}
