package site.icefox.javaeelearn.learn1.target34.service.impl;

import lombok.Data;
import site.icefox.javaeelearn.learn1.target34.dao.UserDao;
import site.icefox.javaeelearn.learn1.target34.service.UserService;

@Data
public class UserServiceImpl implements UserService {
    UserDao userDao;

//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }

    @Override
    public boolean login(String name, String password) {
        return userDao.login(name, password);
    }
}
