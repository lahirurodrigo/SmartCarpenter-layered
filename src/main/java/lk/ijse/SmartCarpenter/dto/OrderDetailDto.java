package lk.ijse.SmartCarpenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailDto {

    private String oId;
    private String code;
    private int qty;
    private double uniPrice;
}
