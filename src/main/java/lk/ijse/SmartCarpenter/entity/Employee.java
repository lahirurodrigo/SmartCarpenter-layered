package lk.ijse.SmartCarpenter.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private  String id;
    private String name;
    private String position;
    private String gender;
    private int age;

}
