package site.icefox.javaeelearn.Learn3.target40;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AnnoAdvice {
    //切点
    @Pointcut("execution( * site.icefox.javaeelearn.Learn3.target40.UserDaoImpl.*(..))")
    public void poincut() {
    }

    //前置通知
    @Before("poincut()")
    public void before(JoinPoint joinPoint) {        //使用JoinPoint接口实例作为参数获得目标对象的类名和方法名
        System.out.print("这是前置通知！");
        System.out.print("目标类：" + joinPoint.getTarget());
        System.out.println("，被织入增强处理的目标方法为：" + joinPoint.getSignature().getName());
    }

    //返回通知
    @AfterReturning("poincut()")
    public void afterReturning(JoinPoint joinPoint) {//使用JoinPoint接口实例作为参数获得目标对象的类名和方法名
        System.out.print("这是返回通知（方法不出现异常时调用）！");
        System.out.println("被织入增强处理的目标方法为：" + joinPoint.getSignature().getName());
    }

    /**
     * 环绕通知
     * ProceedingJoinPoint是JoinPoint子接口，表示可以执行目标方法
     * 1.必须是Object类型的返回值
     * 2.必须接收一个参数，类型为ProceedingJoinPoint
     * 3.必须throws Throwable
     */
    @Around("poincut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {//使用ProceedingJoinPoint接口实例作为参数获得目标对象的类名和方法名
        System.out.println("这是环绕通知之前的部分！");
        //调用目标方法
        Object object = point.proceed();
        System.out.println("这是环绕通知之前的部分！");
        return object;
    }

    //异常通知
    @AfterThrowing("poincut()")
    public void afterException() {
        System.out.println("异常通知！");
    }

    //后置通知
    @After("poincut()")
    public void after() {
        System.out.println("这是后置通知！");
    }
}