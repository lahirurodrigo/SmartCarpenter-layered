package lk.ijse.SmartCarpenter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {

    private  String id;
    private String name;
    private String position;
    private String gender;
    private int age;

}
