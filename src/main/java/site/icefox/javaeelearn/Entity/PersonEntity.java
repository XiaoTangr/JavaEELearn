package site.icefox.javaeelearn.Entity;

import lombok.Data;

@Data
public class PersonEntity {
    private int id;
    private String name;
    private int age;
    private String sex;
    private IdCardEntity card;  // Association with IdCardEntity
}
