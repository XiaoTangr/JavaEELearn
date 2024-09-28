package site.icefox.javaeelearn.Learn2.target50;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.icefox.javaeelearn.Learn2.target50.controller.UserController;

public class AnnotationTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("learn2/target50/applicationContext.xml");
        UserController usercontroller = (UserController) applicationContext.getBean("userController");
        usercontroller.save();
    }
}

