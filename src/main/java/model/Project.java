package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class Project {
    private int id;
    private String name;
    private int cost;
    private Date date;
    private int numOfMembers;
}