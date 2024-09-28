package site.icefox.javaeelearn.Learn2.target50.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.icefox.javaeelearn.Learn2.target50.Dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService {
    //使用@Resource注解注入UserDao
    @Resource(name = "userDao")
    private UserDao userDao;

    public void save() {
        this.userDao.save();
        System.out.println("执行UserServiceImpl.save()");
    }
}

