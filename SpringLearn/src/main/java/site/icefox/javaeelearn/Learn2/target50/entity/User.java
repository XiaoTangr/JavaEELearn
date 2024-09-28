package site.icefox.javaeelearn.Learn2.target50.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component("user")
@Scope("singleton")
public class User {
    @Value("1")
    private int id;
    @Value("张三")
    private String name;
    @Value("123")
    private String password;
}
