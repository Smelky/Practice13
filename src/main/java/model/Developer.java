package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Developer {
    private int id;
    private String name;
    private int age;
    private int salary;
}
