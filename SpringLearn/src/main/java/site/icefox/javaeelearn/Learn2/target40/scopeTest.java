package site.icefox.javaeelearn.Learn2.target40;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class scopeTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("/learn2/target40/applicationBean1.xml");
        Bean1 bean1_1 = (Bean1) applicationContext.getBean("bean1");
        Bean1 bean1_2 = (Bean1) applicationContext.getBean("bean1");
        System.out.print(bean1_1 == bean1_2);
    }
}
