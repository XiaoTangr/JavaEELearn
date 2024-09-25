package site.icefox.javaeelearn.learn1.target34;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.icefox.javaeelearn.learn1.target34.service.UserService;

public class TestSpring {
    public static void main(String[] args) {
        // 加载applicationContext.xml配置
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("learn1/target34/applicationContext.xml");
        UserService userService = (UserService) // 获取配置中的UserService实例
                applicationContext.getBean("userService");
        boolean flag = userService.login("张三", "123");
        if (flag) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
    }
}