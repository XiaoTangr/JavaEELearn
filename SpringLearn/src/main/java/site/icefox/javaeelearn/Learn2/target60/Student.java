package site.icefox.javaeelearn.Learn2.target60;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component("student")
public class Student {
    @Value("1")
    private String id;
    @Value("张三")
    private String name;

    @PostConstruct
    public void init() {
        System.out.println("Bean的初始化完成，调用init()方法");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bean销毁前调用destroy()方法");
    }
}

