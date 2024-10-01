package site.icefox.javaeelearn.Learn3.target40;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new
                ClassPathXmlApplicationContext("learn3/target40/applicationContext-Anno.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.delete();
        System.out.println();
        userDao.insert();
        System.out.println();
        userDao.select();
        System.out.println();
        userDao.update();
    }
}
