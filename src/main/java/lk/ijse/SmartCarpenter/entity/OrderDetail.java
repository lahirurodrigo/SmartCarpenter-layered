package lk.ijse.SmartCarpenter.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {

    private String oId;
    private String code;
    private int qty;
    private double uniPrice;
}
