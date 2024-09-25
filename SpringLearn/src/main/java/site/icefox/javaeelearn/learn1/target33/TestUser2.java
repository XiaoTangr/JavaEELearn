package site.icefox.javaeelearn.learn1.target33;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.icefox.javaeelearn.learn1.target33.Entity.User2;

public class TestUser2 {
    public static void main(String[] args) throws Exception {
        //加载applicationContext.xml配置
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("learn1/target33/applicationContext-User2.xml");
        //获取配置中的User2实例
        User2 user2 = applicationContext.getBean("user2", User2.class);
        System.out.println(user2);
    }
}
