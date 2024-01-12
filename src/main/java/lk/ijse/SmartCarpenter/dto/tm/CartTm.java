package lk.ijse.SmartCarpenter.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CartTm {
    private String code;
    private String desc;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btn;
}
