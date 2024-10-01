package site.icefox.javaeelearn.Learn3.target21;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler {
    private UserDao userDao;

    public Object createProxy(UserDao userDao) {
        this.userDao = userDao;
        ClassLoader classLoader = MyProxy.class.getClassLoader(); // 1.类加载器
        Class[] classes = userDao.getClass().getInterfaces(); // 2.被代理对象实现的所有接口
        return Proxy.newProxyInstance(classLoader, classes, this); // 3.返回代理对象
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MyAspect.check_Permissions();
        Object res = method.invoke(userDao, args);
        MyAspect.log();
        return res;
    }
}
