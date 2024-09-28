package site.icefox.javaeelearn.Learn2.target50.Dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import site.icefox.javaeelearn.Learn2.target50.entity.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    public void save() {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("Learn2/target50/applicationContext.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
        System.out.println("执行UserDaoImpl.save()");
    }
}
