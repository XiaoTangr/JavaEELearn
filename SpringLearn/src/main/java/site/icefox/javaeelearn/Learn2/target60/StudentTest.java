package site.icefox.javaeelearn.Learn2.target60;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("Learn2/target60/applicationStudent.xml");
        Student student = (Student) applicationContext.getBean("student");
        System.out.println(student);
        //销毁Spring容器中的所有Bean
        AbstractApplicationContext ac = (AbstractApplicationContext)
                applicationContext;
        ac.registerShutdownHook();
    }
}
