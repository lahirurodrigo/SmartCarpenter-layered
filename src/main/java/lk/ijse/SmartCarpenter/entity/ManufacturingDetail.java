package lk.ijse.SmartCarpenter.entity;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManufacturingDetail {
    private String code;
    private String empId;
    private double labourCost;
    private LocalDate date;
    private int qty;

}
