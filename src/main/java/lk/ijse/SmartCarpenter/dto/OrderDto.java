package lk.ijse.SmartCarpenter.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
    private String id;
    private LocalDate placeDate;
    private LocalDate dueDate;
    private int duration;
    private String cusId;

}
