package lk.ijse.SmartCarpenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RawMaterialDto {

    private String code;
    private String category;
    private double unitPrice;
    private int qty;

}
