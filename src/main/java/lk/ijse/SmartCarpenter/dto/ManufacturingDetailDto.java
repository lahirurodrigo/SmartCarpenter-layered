package lk.ijse.SmartCarpenter.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManufacturingDetailDto {
    private String code;
    private String empId;
    private double labourCost;
    private LocalDate date;
    private int qty;

}
