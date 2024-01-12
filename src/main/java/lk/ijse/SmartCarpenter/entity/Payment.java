package lk.ijse.SmartCarpenter.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    private String id;
    private LocalDate date;
    private String type;
    private double amount;
    private String oId;
}
