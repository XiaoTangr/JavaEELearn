package site.icefox.javaeelearn.Learn3.target22;

public class CglibTest {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        UserDao userDao = new UserDao(); // 创建目标对象
        UserDao userDaoProxy = (UserDao) cglibProxy.createProxy(userDao); // 获取代理对象

        // 执行方法
        userDaoProxy.addUser();
        userDaoProxy.deleteUser();
    }
}


