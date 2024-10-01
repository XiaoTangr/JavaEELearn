Spring的AOP模块是Spring框架体系中十分重要的内容，该模块一般适用于具有横切逻辑的场景，如访问控制、事务管理和性能监控等，本章将对Spring AOP的相关知识进行详细讲解。

## 一 Spring AOP介绍

### 1.1 AOP概述

​	AOP的全称是Aspect Oriented Programming，即面向切面编程。和OOP不同，AOP主张将程序中相同的业务逻辑进行横向隔离，并将重复的业务逻辑抽取到一个独立的模块中，以达到提高程序可重用性和开发效率的目的。

​	在传统的业务处理代码中，通常都会进行事务处理、日志记录等操作。虽然使用OOP可以通过组合或者继承的方式来达到代码的重用，但如果要实现某个功能（如日志记录），同样的代码仍然会分散到各个方法中。

> 例如，订单系统中有添加订单信息、更新订单信息和删除订单信息3个方法，这3个方法中都包含事务管理业务代码，订单系统的逻辑如图所示。
>
> ![image-20240930173948819](https://ftp.icefox.site/blog/images/202409301739498.png)

* AOP面向切面编程的优势

  ​	由订单系统可知，添加订单信息、修改订单信息、删除订单信息的方法体中都包含事务管理的业务逻辑，这就带来了一定数量的重复代码并使程序的维护成本增加。基于AOP的面向切面编程，可以为此类问题提供解决方案，AOP可以将事务管理的业务逻辑从这三个方法体中抽取到一个可重用的模块，进而降低横向业务逻辑之间的耦合，减少重复代码。AOP的使用，使开发人员在编写业务逻辑时可以专心于核心业务，而不用过多地关注其他业务逻辑的实现，不但提高了开发效率，又增强了代码的可维护性。

### 1.2 Spring AOP术语

| 术语名称      | 英文         | 说明                                                         |
| ------------- | ------------ | :----------------------------------------------------------- |
| 切面          | Aspect       | 切面是指关注点形成的类（关注点是指类中重复的代码），通常是指封装的、用于横向插入系统的功能类（如事务管理、日志记录等）。在实际开发中，该类被Spring容器识别为切面，需要在配置文件中通过\<bean>元素指定 |
| 连接点        | Joinpoint    | 连接点是程序执行过程中某个特定的节点，例如，某方法调用时或处理异常时。在Spring AOP中，一个连接点通常是一个方法的执行。 |
| 切入点        | Pointcut     | 当某个连接点满足预先指定的条件时，AOP就能够定位到这个连接点，在连接点处插入切面，该连接点也就变成了切入点。 |
| 通知/增强处理 | Advice       | 通知/增强处理就是插入的切面程序代码。可以将通知/增强处理理解为切面中的方法，它是切面的具体实现 |
| 目标对象      | Target       | 目标对象是指被插入切面的方法，即包含主业务逻辑的类对象。或者说是被一个或者多个切面所通知的对象 |
| 织入          | Weaving      | 将切面代码插入到目标对象上，从而生成代理对象的过程。织入可以在编译时，类加载时和运行时完成。在编译时进行织入就是静态代理，而在运行时进行织入则是动态代理。 |
| 代理          | Proxy        | 将通知应用到目标对象之后，程序动态创建的通知对象，就称为代理。代理类既可能是和原类具有相同接口的类，也可能就是原类的子类，可以采用调用原类相同的方式调用代理类。 |
| 引介          | Introduction | 引介是一种特殊的通知，它为目标对象添加一些属性和方法。这样，即使一个业务类原本没有实现某一个接口，通过AOP的引介功能，也可以动态地为该业务类添加接口的实现逻辑，让业务类成为这个接口的实现类。 |

## 二 Spring AOP的实现机制

### 2.1 JDK动态代理

默认情况下，Spring AOP使用JDK动态代理，JDK动态代理是通过java.lang.reflect.Proxy 类实现的，可以调用Proxy类的newProxyInstance()方法创建代理对象。JDK动态代理可以实现无侵入式的代码扩展，并且可以在不修改源代码的情况下，增强某些方法。

#### 2.1.1 示例代码

> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn3/target21`下

* UserDao.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target21;
  
  public interface UserDao {
      void addUser();
  
      void deleteUser();
  }
  ```

* UserDaoImpl.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target21;
  
  import lombok.Data;
  
  @Data
  public class UserDaoImpl implements UserDao {
      public void addUser() {
          System.out.println("添加用户");
      }
  
      public void deleteUser() {
          System.out.println("删除用户");
      }
  }
  ```

* MyProxy.java

  ``` java
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
  
  ```

* MyAspect.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target21;
  
  public class MyAspect {// 切面类：存在多个通知Advice（增强的方法）
      public static void check_Permissions(){
          System.out.println("模拟检查权限...");		}
      public static void log(){
          System.out.println("模拟记录日志...");		}
  }
  
  ```

* JDKTest.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target21;
  
  public class JDKTest {
      public static void main(String[] args) {
          MyProxy jdkProxy = new MyProxy();// 创建代理对象
          UserDao userDao = new UserDaoImpl();// 创建目标对象
          // 从代理对象中获取增强后的目标对象
          UserDao userDao1 = (UserDao) jdkProxy.createProxy(userDao);
          // 执行方法
          userDao1.addUser();
          userDao1.deleteUser();
      }
  }
  ```

#### 2.1.2 预期结果

``` cmd
模拟检查权限...
添加用户
模拟记录日志...
模拟检查权限...
删除用户
模拟记录日志...

进程已结束，退出代码为 0
```

### 2.2 CGLib动态代理

JDK动态代理存在缺陷，它只能为接口创建代理对象，当需要为类创建代理对象时，就需要使用CGLib（Code Generation Library）动态代理，CGLib动态代理不要求目标类实现接口，它采用底层的字节码技术，通过继承的方式动态创建代理对象。

#### 2.2.1 示例代码

> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn3/target22`下

* CglibProxy.java

  ``` java
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
  ```

* CglibTest.java

  ``` java
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
  ```


* MyAspect.java

  ```java
  package site.icefox.javaeelearn.Learn3.target22;
  
  public class MyAspect {// 切面类：存在多个通知Advice（增强的方法）
  
      public void check_Permissions() {
          System.out.println("模拟检查权限...");
      }
  
      public void log() {
          System.out.println("模拟记录日志...");
      }
  }
  ```

* UserDao.java

  ```java
  package site.icefox.javaeelearn.Learn3.target22;
  
  public class UserDao {
      public void addUser() {
          System.out.println("添加用户");
      }
  
      public void deleteUser() {
          System.out.println("删除用户");
      }
  }
  ```

#### 2.2.2 预期结果

``` cmd
模拟检查权限...
添加用户
模拟记录日志...
模拟检查权限...
删除用户
模拟记录日志...

进程已结束，退出代码为 0
```



## 三 基于XML的AOP实现

> 使用AOP代理对象的好处
>
> 因为Spring AOP中的代理对象由IoC容器自动生成，所以开发者无须过多关注代理对象生成的过程，只需选择连接点、创建切面、定义切点并在XML文件中添加配置信息即可。Spring提供了一系列配置Spring AOP的XML元素。

### 3.1 配置Spring AOP的XML元素

| **元素**               | **描述**                                                     |
| ---------------------- | ------------------------------------------------------------ |
| \<aop:config>          | Spring AOP配置的根元素                                       |
| \<aop:aspect>          | 配置切面                                                     |
| \<aop:advisor>         | 配置通知器                                                   |
| \<aop:pointcut>        | 配置切点                                                     |
| \<aop:before>          | 配置前置通知,在目标方法执行前实施增强,可以应用于权限管理等功能 |
| \<aop:after>           | 配置后置通知,在目标方法执行后实施增强,可以应用于关闭流、上传文件、删除临时文件等功能 |
| \<aop:around>          | 配置环绕方式,在目标方法执行前后实施增强,可以应用于日志、事务管理等功能 |
| \<aop:after-returning> | 配置返回通知,在目标方法成功执行之后调用通知                  |
| \<aop:after-throwing>  | 配置异常通知,在方法抛出异常后实施增强,可以应用于处理异常记录日志等功能 |

### 3.2 配置说明

#### 3.2.1 配置切面

在Spring的配置文件中，配置切面使用的是\<aop:aspect>元素，该元素会将一个已定义好的Spring Bean转换成切面Bean，因此，在使用\<aop:aspect>元素之前，要在配置文件中先定义一个普通的Spring Bean。Spring Bean定义完成后，通过\<aop:aspect>元素的ref属性即可引用该Bean。配置\<aop:aspect>元素时，通常会指定id和ref两个属性。

* <aop:aspect>元素的id属性和ref属性的描述：

  | 属性 | 说明                      |
  | ---- | ------------------------- |
  | id   | 用于定义该切面的唯一标识  |
  | ref  | 用于引用普通的Spring Bean |

#### 3.2.2 配置切入点

在Spring的配置文件中，切入点是通过\<aop:pointcut>元素来定义的。当\<aop:pointcut>元素作为\<aop:config>元素的子元素定义时，表示该切入点是全局的，它可被多个切面共享；当\<aop:pointcut>元素作为\<aop:aspect>元素的子元素时，表示该切入点只对当前切面有效。定义\<aop:pointcut>元素时，通常会指定id、expression属性。

* \<aop:pointcut>元素的id属性和expression属性描述：

  | 属性         | 说明                      |
  | ----        | --------------------------|
  | id          |用于指定切入点的唯一标识       |
  | expression  |用于指定切入点关联的切入点表达式|

* Spring AOP切入点表达式的基本格式

    ``` txt
    execution(
        modifiers-pattern?
        ret-type-pattern
        declaring-type-pattern?
        name-pattern(param-pattern)
        throws-pattern?
    )
    ```

* execution表达式各部分参数说明

    | 参数名称 | 说明 |
    | ---- | ---- |
    | modifiers-pattern | 表示定义的目标方法的访问修饰符，如public、private等。 |
    | ret-type-pattern | 表示定义的目标方法的返回值类型，如void、String等。 |
    | declaring-type-pattern | 表示定义的目标方法的类路径，如com.itheima.jdk.UserDaoImpl。 |
    | name-pattern | 表示具体需要被代理的目标方法，如add()方法。 |
    | param-pattern | 表示需要被代理的目标方法包含的参数，本章示例中目标方法参数都为空。 |
    | throws-pattern | 表示需要被代理的目标方法抛出的异常类型。 |

#### 3.2.3 配置通知

在Spring的配置文件中，使用\<aop:aspect>元素配置了5种常用通知，分别为前置通知、后置通知、环绕通知、返回通知和异常通知。

* \<aop:aspect>元素的常用属性

    | 属性    | 描述      |
    | ---------| --------------------------------------------------- |
    |pointcut| 该属性用于指定一个切入点表达式,Spring将在匹配该表达式的连接点时织入该通知。|
    |pointcut-ref| 该属性指定一个已经存在的切入点名称,如配置代码中的myPointCut。通常pointcut和pointcut-ref两个属性只需要使用其中一个即可。 |
    | method| 该属性指定一个方法名,指定将切面Bean中的该方法转换为增强处理。 |
    | throwing| 该属性只对\<after-throwing>元素有效,它用于指定一个形参名,异常通知方法可以通过该形参访问目标方法所抛出的异常。 |
    | returning| 该属性只对\<after-returning>元素有效,它用于指定一个形参名,后置通知方法可以通过该形参访问目标方法的返回值。 |

### 3.3 示例代码
> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn3/target30`下
>
> xml代码位于`SpringLearn/src/main/resources/learn3/target30`下

* TestXml.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target30;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestXml {
      public static void main(String[] args) {
          ApplicationContext context = new
                  ClassPathXmlApplicationContext("learn3/target30/applicationContext.xml");
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
  ```

* UserDao.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target30;
  
  public interface UserDao {
      void insert();
      void delete();
      void update();
      void select();
  }
  ```
* UserDaoImpl.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target30;
  
  public class UserDaoImpl implements UserDao {
      public void insert() {
          System.out.println("添加用户信息");
      }
  
      public void delete() {
          System.out.println("删除用户信息");
      }
  
      public void update() {
          System.out.println("更新用户信息");
      }
  
      public void select() {
          System.out.println("查询用户信息");
      }
  }
  ```
* XmlAdvice.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target30;
  
  import org.aspectj.lang.JoinPoint;
  import org.aspectj.lang.ProceedingJoinPoint;
  
  public class XmlAdvice {
      //前置通知
      public void before(JoinPoint joinPoint) {        //使用JoinPoint接口实例作为参数获得目标对象的类名和方法名
          System.out.print("这是前置通知！");
          System.out.print("目标类：" + joinPoint.getTarget());
          System.out.println("，被织入增强处理的目标方法为：" + joinPoint.getSignature().getName());
      }
  
      //返回通知
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
      public Object around(ProceedingJoinPoint point) throws Throwable {//使用ProceedingJoinPoint接口实例作为参数获得目标对象的类名和方法名
          System.out.println("这是环绕通知之前的部分！");
          //调用目标方法
          Object object = point.proceed();
          System.out.println("这是环绕通知之前的部分！");
          return object;
      }
  
      //异常通知
      public void afterException() {
          System.out.println("异常通知！");
      }
  
      //后置通知
      public void after() {
          System.out.println("这是后置通知！");
      }
  }
  ```

### 3.4 预期结果

``` cmd
这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target30.UserDaoImpl@7adf16aa，被织入增强处理的目标方法为：delete
这是环绕通知之前的部分！
删除用户信息
这是后置通知！
这是环绕通知之前的部分！
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：delete

这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target30.UserDaoImpl@7adf16aa，被织入增强处理的目标方法为：insert
这是环绕通知之前的部分！
添加用户信息
这是后置通知！
这是环绕通知之前的部分！
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：insert

这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target30.UserDaoImpl@7adf16aa，被织入增强处理的目标方法为：select
这是环绕通知之前的部分！
查询用户信息
这是后置通知！
这是环绕通知之前的部分！
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：select

这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target30.UserDaoImpl@7adf16aa，被织入增强处理的目标方法为：update
这是环绕通知之前的部分！
更新用户信息
这是后置通知！
这是环绕通知之前的部分！
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：update

进程已结束，退出代码为 0
```

## 四 基于注解的AOP实现

### 4.1 Spring AOP的注解

| 元素            | **描述**     |
| --------------- | ------------ |
| @Aspect         | 配置切面     |
| @Pointcut       | 配置切点     |
| @Before         | 配置前置通知 |
| @After          | 置后置通知   |
| @Around         | 配置环绕方式 |
| @AfterReturning | 配置返回通知 |
| @AfterThrowing  | 配置异常通知 |

### 4.2 示例代码

> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn3/target40`下
>
> xml代码位于`SpringLearn/src/main/resources/learn3/target40`下

* AnnoAdvice.java

  ``` java
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
  ```
* TestAnnotation.java

  ``` java
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
  
  ```
* UserDao.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target40;
  
  public interface UserDao {
      void insert();
  
      void delete();
  
      void update();
  
      void select();
  }
  ```
* UserDaoImpl.java

  ``` java
  package site.icefox.javaeelearn.Learn3.target40;
  
  public class UserDaoImpl implements UserDao {
      public void insert() {
          System.out.println("添加用户信息");
      }
  
      public void delete() {
          System.out.println("删除用户信息");
      }
  
      public void update() {
          System.out.println("更新用户信息");
      }
  
      public void select() {
          System.out.println("查询用户信息");
      }
  }
  ```

* applicationContext-Anno.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
      <!-- 注册Bean -->
      <bean name="userDao" class="site.icefox.javaeelearn.Learn3.target40.UserDaoImpl"/>
      <bean name="AnnoAdvice" class="site.icefox.javaeelearn.Learn3.target40.AnnoAdvice"/>
      <!-- 开启@aspectj的自动代理支持 -->
      <aop:aspectj-autoproxy/>
  </beans>
  ```

### 4.3 预期结果

``` cmd
这是环绕通知之前的部分！
这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target40.UserDaoImpl@24faea88，被织入增强处理的目标方法为：delete
删除用户信息
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：delete
这是后置通知！
这是环绕通知之前的部分！

这是环绕通知之前的部分！
这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target40.UserDaoImpl@24faea88，被织入增强处理的目标方法为：insert
添加用户信息
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：insert
这是后置通知！
这是环绕通知之前的部分！

这是环绕通知之前的部分！
这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target40.UserDaoImpl@24faea88，被织入增强处理的目标方法为：select
查询用户信息
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：select
这是后置通知！
这是环绕通知之前的部分！

这是环绕通知之前的部分！
这是前置通知！目标类：site.icefox.javaeelearn.Learn3.target40.UserDaoImpl@24faea88，被织入增强处理的目标方法为：update
更新用户信息
这是返回通知（方法不出现异常时调用）！被织入增强处理的目标方法为：update
这是后置通知！
这是环绕通知之前的部分！

进程已结束，退出代码为 0
```

## 五 结语

本章主要讲解了Spring中的AOP。首先介绍了Spring AOP，包括Spring AOP的概述和Spring AOP的术语；然后讲解了Spring AOP的实现机制，包括JDK动态代理和CGLib动态代理；接着讲解了基于XML的AOP实现，并使用案例的方式实现了基于XML文件的AOP；最后讲解了基于注解的AOP实现。通过本章的学习，读者可以对Spring AOP有基础的了解，为框架开发奠定基础。 