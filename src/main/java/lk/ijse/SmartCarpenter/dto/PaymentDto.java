package lk.ijse.SmartCarpenter.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {
    private String id;
    private LocalDate date;
    private String type;
    private double amount;
    private String oId;
}
