package lk.ijse.SmartCarpenter.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private String id;
    private LocalDate placeDate;
    private LocalDate dueDate;
    private int duration;
    private String cusId;

}
