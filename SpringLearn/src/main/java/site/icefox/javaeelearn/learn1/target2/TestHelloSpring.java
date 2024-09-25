package site.icefox.javaeelearn.learn1.target2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestHelloSpring {
    public static void main(String[] args) {
        //获取Logger对象,Logger工厂方法返回一个Logger对象
        Logger logger = LoggerFactory.getLogger(TestHelloSpring.class);
        logger.info("我是一条日志信息");
        // 初始化spring容器，加载applicationContext.xml配置
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("learn1/target2/applicationContext.xml");
        // 通过容器获取配置中helloSpring的实例
        HelloSpring helloSpring =
                (HelloSpring) applicationContext.getBean("helloSpring");
        helloSpring.show();// 调用方法
    }
}
