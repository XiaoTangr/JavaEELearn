package site.icefox.javaeelearn.learn1.target34.dao.impl;


import site.icefox.javaeelearn.learn1.target34.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean login(String name, String password) {
        return name.equals("张三") && password.equals("123");
    }
}
