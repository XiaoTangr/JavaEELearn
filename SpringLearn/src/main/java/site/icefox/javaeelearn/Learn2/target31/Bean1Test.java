package site.icefox.javaeelearn.Learn2.target31;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bean1Test {
    public static void main(String[] args) {
        // 加载applicationBean1.xml配置
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("/learn2/target31/applicationBean1.xml");
        // 通过容器获取配置中bean1的实例
        Bean1 bean = (Bean1) applicationContext.getBean("bean1");
        System.out.print(bean);
    }
}
