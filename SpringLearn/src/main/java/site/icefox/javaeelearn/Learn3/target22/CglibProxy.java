package site.icefox.javaeelearn.Learn3.target22;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    public Object createProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //创建一个切面
        MyAspect myAspect = new MyAspect();
        //前增强
        myAspect.check_Permissions();
        Object obj = methodProxy.invokeSuper(proxy, args);
        //后增强
        myAspect.log();
        return obj;
    }
}
