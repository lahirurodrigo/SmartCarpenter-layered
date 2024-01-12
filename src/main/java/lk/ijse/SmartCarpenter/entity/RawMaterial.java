package lk.ijse.SmartCarpenter.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RawMaterial {

    private String code;
    private String category;
    private double unitPrice;
    private int qty;

}
