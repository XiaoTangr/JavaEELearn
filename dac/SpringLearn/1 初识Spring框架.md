## 一 Spring介绍

### 1.1 概述

* Spring框架的核心技术

Spring是由Rod Johnson组织和开发的一个分层的Java SE/EE一站式（full-stack）轻量级开源框架。它最为核心的理念是IoC（控制反转）和AOP（面向切面编程），其中，IoC是Spring的基础，它支撑着Spring对JavaBean的管理功能；AOP是Spring 的重要特性，AOP是通过预编译方式和运行期间动态代理实现程序功能，也就是说可以在不修改源代码的情况下，给程序统一添加功能。

* 在表现层、业务逻辑层和持久层的作用

1. 在表现层它提供了Spring MVC框架，并且Spring还可以与Struts框架整合。
2. 在业务逻辑层可以管理事务、记录日志等。
3. 在持久层可以整合MyBatis、Hibernate、JdbcTemplate等技术。

### 1.2 Spring框架的优点

Spring作为Java EE的一个全方位应用程序框架，为开发企业级应用提供了一个健壮、高效的解决方案。它不仅可以应用于Java应用的开发，也可以应用于服务器端开发。Spring之所以得到如此广泛应用，是因为Spring框架具有以下几个优点。

1. 非侵入式设计

   Spring是一种非侵入式（non-invasive）框架，所谓非侵入式是指Spring框架的API不会在业务逻辑上出现，也就是说业务逻辑应该是纯净的，不能出现与业务逻辑无关的代码。由于业务逻辑中没有Spring的API，所以业务逻辑代码也可以从Spring框架快速地移植到其他框架。

2. 降低耦合性

   Spring就是一个大工厂，可以将所有对象的创建和依赖关系的维护工作都交给Spring容器管理，大大降低了组件之间的耦合性。

3. 支持AOP编程

   Spring提供了对AOP的支持，AOP可以将一些通用的任务进行集中处理，如安全、事务和日志等，以减少通过传统OOP方法带来的代码冗余和繁杂。

4. 支持声明式事务

   在Spring中，可以直接通过Spring配置文件管理数据库事务，省去了手动编程的繁琐，提高了开发效率。

5. 方便程序的测试

   Spring提供了对Junit的支持，开发人员可以通过Junit进行单元测试。

6. 方便集成框架

   Spring提供了一个广阔的基础平台，其内部提供了对各种框架的直接支持，如Struts、Hibernate、MyBatis、Quartz等，这些优秀框架可以与Spring无缝集成。

7. 降低Java EE API的使用难度

   Spring对Java EE开发中的一些API（如JDBC、JavaMail等）都进行了封装，大大降低了这些API的使用难度。

### 1.3 Spring 5的体系结构

* 体系结构图

![img](https://ftp.icefox.site/blog/images/202409251816417.png)

* 组成模块

  Spring 5框架主要有七大模块，每个大模块由多个或1个小模块组成，如Spring的核心容器模块（Core Container）是由Beans模块、Core模块、Context模块和SpEL模块组成。下面结合String 5的体系结构图对Spring体系结构中的主要模块进行简单介绍。 

  * 核心容器模块在Spring的功能体系中起着支撑性作用，是其他模块的基石。核心容器层主要由Beans模块、Core模块、Contex模块和SpEL模块组成。
    1. Beans模块。它提供了BeanFactory类，是工厂模式的经典实现，Beans模块的主要作用是创建和管理Bean对象。
    2. Core模块。它提供了Spring框架的基本组成部分，包括IoC和DI功能。
    3. Context模块。它构建于Beans模块和Core模块的基础之上，它可以通过ApplicationContext接口提供上下文信息。
    4. SpEL模块。它是Spring 3.0后新增的模块，提供了对SpEL表达式语言（Spring Expression Language）的支持，SpEL表达式语言是一个在程序运行时支持操作对象图的表达式语言。
  * 数据访问及集成模块用于访问和操作数据库中的数据，它主要包含JDBC模块、ORM模块、OXM模块、JMS模块和Transactions模块。
    1. JDBC模块。它提供了一个JDBC的抽象层，消除了冗长的JDBC编码并能够解析数据库供应商特有的错误代码。
    2. ORM模块。它为主流的对象关系映射API提供了集成层，用于集成主流的对象关系映射框架。
    3. OXM模块。它提供了对XML映射的抽象层的支持，如JAXB、Castor等。
    4. JMS模块。它主要用于传递消息，包含消息的生产和消费。自4.1版本后，JMS模块支持与Spring-message模块的集成。
    5. Transactions模块。它的主要功能是事务管理。
  *  Web模块的实现基于APPlicationContext基础之上，它提供了Web应用的各种工具类，包括了Web模块、Servlet模块、WebSocket模块和Portlet模块。
    1. Web模块。它提供了针对Web开发的集成特性，如大部分文件上传功能等。此外，Web模块还包含一个HTTP客户端和Spring远程处理支持的Web相关部分。
    2. Servlet模块。它提供了Spring的模型、视图、控制器以及Web应用程序的REST Web服务实现。
    3. WebSocket模块。它是Spring 4.0以后新增的模块，它提供了WebSocket 和SockJS的实现，以及对STOMP的支持。
    4. Portlet模块。它类似Servlet模块的功能，提供了Portlet环境下的MVC实现。
  * Spring框架的其他模块还有AOP模块、Aspects模块、Instrumentation模块以及Test模块。
    1. AOP模块。它提供了对面向切面编程的支持，程序可以定义方法拦截器和切入点，将代码按照功能进行分离，以降低程序的耦合性。
    2. Aspects模块。它提供了与AspectJ集成的支持。
    3. Instrumentation模块。它提供了对类工具的支持，并且实现了类加载器，该模块可以在特定的应用服务器中使用。
    4. Messaging模块。它是Spring 4.0以后新增的模块，它提供了对消息传递体系结构和协议的支持。
    5. Test模块。它提供了对程序单元测试和集成测试的支持。

### 1.4 Spring 5的新特性

* 更新JDK基线

  因为Spring 5代码库运行于JDK 8之上，所以Spring 5对JDK的最低要求是JDK 8，这可以促进Spring的使用者积极运用Java 8新特性。

* 修订核心框架

  1. 基于JDK 8的反射增强，通过Spring 5提供的方法可以更加高效的对类或类的参数进行访问。
  2. 核心的Spring接口提供了基于JDK 8的默认方法构建的选择性声明。
  3. 用@Nullable和@NotNull注解来表明可为空的参数以及返回值，可以在编译时处理空值而不是在运行时抛出NullPointerExceptions异常。

* 更新核心容器

  Spring 5支持候选组件索引作为类路径扫描的替代方案。从索引读取实体类，会使加载组件索引开销更低，因此，Spring程序的启动时间将会缩减。

* 支持响应式编程

  响应式编程是另外一种编程风格，它专注于构建对事件做出响应的应用程序。Spring 5包含响应流和Reactor（ReactiveStream的Java实现），响应流和Reactor支撑了Spring自身的功能及相关API。

* 支持函数式Web框架

  Spring 5提供了一个函数式Web框架。该框架使用函数式编程风格来定义端点，它引入了两个基本组件: HandlerFunction和RouterFunction。HandlerFunction 表示处理接收到的请求并生成响应函数；RouterFunction替代了@RequestMapping注解，用于将接收到的请求转发到处理函数。

* 支持Kotlin

  Spring 5提供了对Kotlin语言的支持。Kotlin是一种支持函数式编程风格的面向对象语言，它运行在JVM之上，可以让代码更具有表现力、简洁性和可读性。有了对Kotlin的支持，开发人员可以进行深度的函数式Spring编程，这拓宽了Spring的应用领域。

* 提升测试功能

  Spring 5完全支持Junit 5 Jupiter，因此可以使用Junit 5编写测试代码。除此之外，Spring 5还提供了在Spring TestContext Framework中进行并行测试的扩展。针对响应式编程模型，Spring 5引入了支持Spring webFlux的WebTestClient集成测试。

## 二 入门程序

### 2.1 创建工程

使用idea创建一个空的Maven工程，在pom.xml写入以下内容后更新Maven

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>site.icefox.javaeelearn</groupId>
    <artifactId>SpringLearn</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <!--Spring的基础包Spring-expressinon-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>6.0.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.0.8</version>
        </dependency>
        <!--lombok的依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.34</version>
            <scope>provided</scope>
        </dependency>
        <!--log4j2的依赖-->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.19.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j2-impl</artifactId>
            <version>2.19.0</version>
        </dependency>
    </dependencies>
</project>
```

### 2.2 入门程序

#### 2.2.1 HelloSpring.java

`SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target2/HelloSpring.java`

```java
package site.icefox.javaeelearn.learn1.target2;

public class HelloSpring {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void show() {
        System.out.println(userName + ":欢迎来到Spring");
    }
}
```

### 2.2.2 TestHelloSpring.java

`SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target2/TestHelloSpring.java`

```java
package site.icefox.javaeelearn.learn1.target2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TestHelloSpring {
    public static void main(String[] args) {
        //获取Logger对象,Logger工厂方法返回一个Logger对象
        Logger logger = LoggerFactory.getLogger(TestHelloSpring.class);
        logger.info("我是一条日志信息");
        // 初始化spring容器，加载applicationContext.xml配置
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("learn1/target2/applicationContext.xml");
        // 通过容器获取配置中helloSpring的实例
        HelloSpring helloSpring =
                (HelloSpring) applicationContext.getBean("helloSpring");
        helloSpring.show();// 调用方法
    }
}
```

#### 2.2.3 applicationContext.xml

`SpringLearn/src/main/resources/learn1/target2/applicationContext.xml`

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
>
    <bean id="helloSpring" class="site.icefox.javaeelearn.learn1.target2.HelloSpring">
        <!--为userName属性赋值-->
        <property name="userName" value="张">
        </property>
    </bean>
</beans>
```

#### 2.2.4 测试

现在。你可以在`TestHelloSpring.java`文件的行号前面看到一个绿色小三角，点击它尝试运行。

你应该可以看到类似如下的输出

``` cmd
2024-09-25 18:34:14 327 [main] INFO site.icefox.javaeelearn.learn1.target2.TestHelloSpring - 我是一条日志信息
2024-09-25 18:34:14 358 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@4a8355dd
2024-09-25 18:34:14 451 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 1 bean definitions from class path resource [learn1/target2/applicationContext.xml]
2024-09-25 18:34:14 468 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'helloSpring'
张:欢迎来到Spring

进程已结束，退出代码为 0
```

## 三 控制反转与依赖注入

### 3.1 控制反转的概念

#### 3.1.1 传统面向对象程序设计原则

控制反转（Inversion of Control，缩写为IoC）是面向对象编程中的一个设计原则，用来降低程序代码之间的耦合度。在传统面向对象编程中，获取对象的方式是用new关键字主动创建一个对象，也就是说应用程序掌握着对象的控制权。

![image-20240925184023032](https://ftp.icefox.site/blog/images/202409251840423.png)

#### 3.1.2 IoC设计原则

IoC控制反转机制指的是对象由Ioc容器统一管理，当程序需要使用对象时，可以直接从IoC容器中获取。这样对象的控制权就从应用程序转移到了IoC容器。IoC设计原则如图，它是借助于IoC容器实现具有依赖关系对象之间的解耦，各个对象类封装之后，通过IoC容器来关联这些对象类。

![image-20240925184100812](https://ftp.icefox.site/blog/images/202409251841095.png)

### 3.2 依赖注入的概念

依赖注入（Dependency Inject，缩写DI）就是由IoC容器在运行期间动态地将某种依赖资源注入对象之中。例如，将对象B注入（赋值）给对象A的成员变量。依赖注入的基本思想是：明确地定义组件接口，独立开发各个组件，然后根据组件的依赖关系组装运行

依赖注入（DI）和控制反转（IoC）是从不同角度来描述了同一件事情。依赖注入是从应用程序的角度描述，即应用程序依赖IoC容器创建并注入它所需要的外部资源；而控制反转是从IoC容器的角度描述，即IoC容器控制应用程序，由IoC容器反向地向应用程序注入应用程序所需要的外部资源。这里所说的外部资源可以是外部实例对象，也可以是外部文件对象等

### 3.3 依赖注入的类型

依赖注入的作用就是在使用Spring框架创建对象时，动态的将其所依赖的对象注入到Bean组件中。依赖注入通常有两种实现方式，一种是构造方法注入，另一种是属性setter方法注入。

#### 3.3.1 解释

* 构造方法注入

  是指Spring容器调用构造方法注入被依赖的实例，构造方法可以是有参的或者是无参的。Spring在读取配置信息后，会通过反射方式调用实例的构造方法，如果是有参构造方法，可以在构造方法中传入所需的参数值，最后创建类对象。

* setter属性注入

  属性setter方法注入是Spring最主流的注入方法，这种注入方法简单、直观，它是在被注入的类中声明一个setter方法，通过setter方法的参数注入对应的值。

#### 3.3.2 相关代码

* `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target33/Entity/User1.java`

  ``` java
  package site.icefox.javaeelearn.learn1.target33.Entity;
  
  public class User1 {
      private int id;
      private String name;
      private String password;
  
      public User1(int id, String name, String password) {
          this.id = id;
          this.name = name;
          this.password = password;
      }
  
      public String toString() {
          return "id=" + id + ",name=" + name + ",password=" + password;
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target33/Entity/User2.java`

  ```java
  package site.icefox.javaeelearn.learn1.target33.Entity;
  
  import lombok.Data;
  
  @Data
  public class User2 {
      private int id;
      private String name;
      private String password;
  
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target33/TestUser1.java`

  ```java
  package site.icefox.javaeelearn.learn1.target33;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import site.icefox.javaeelearn.learn1.target33.Entity.User1;
  
  public class TestUser1 {
      public static void main(String[] args)throws Exception{
          //加载applicationContext.xml配置
          ApplicationContext applicationContext=new
                  ClassPathXmlApplicationContext("learn1/target33/applicationContext-User.xml");
          //获取配置中的User1实例
          User1 user1=applicationContext.getBean("user1", User1.class);
          System.out.println(user1);
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target33/TestUser2.java`

  ```java
  package site.icefox.javaeelearn.learn1.target33;
  
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import site.icefox.javaeelearn.learn1.target33.Entity.User2;
  
  public class TestUser2 {
      public static void main(String[] args) throws Exception {
          //加载applicationContext.xml配置
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("learn1/target33/applicationContext-User2.xml");
          //获取配置中的User2实例
          User2 user2 = applicationContext.getBean("user2", User2.class);
          System.out.println(user2);
      }
  }
  ```

* `SpringLearn/src/main/resources/learn1/target33/applicationContext-User.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
  >
      <bean id="user1" class="site.icefox.javaeelearn.learn1.target33.Entity.User1">
          <constructor-arg name="id" value="1">
          </constructor-arg>
          <constructor-arg name="name" value="张三">
          </constructor-arg>
          <constructor-arg name="password" value="123"></constructor-arg>
      </bean>
  </beans>
  ```

* `SpringLearn/src/main/resources/learn1/target33/applicationContext-User2.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
  >
      <bean id="user2" class="site.icefox.javaeelearn.learn1.target33.Entity.User2">
          <property name="id" value="2"></property>
          <property name="name" value="李四"></property>
          <property name="password" value="456">
          </property>
      </bean>
  </beans>
  ```

#### 3.3.3 测试结果

分别执行`TestUser1.main()`和`TestUser2.nain()`，应该可以获得如下结果：

* 构造方法注入

``` cmd
site.icefox.javaeelearn.learn1.target33.TestUser1
2024-09-26 10:35:19 890 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@4d5d943d
2024-09-26 10:35:20 078 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 1 bean definitions from class path resource [learn1/target33/applicationContext-User.xml]
2024-09-26 10:35:20 149 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'user1'
2024-09-26 10:35:20 174 [main] WARN org.springframework.core.LocalVariableTableParameterNameDiscoverer - Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: site.icefox.javaeelearn.learn1.target33.Entity.User1
id=1,name=张三,password=123

进程已结束，退出代码为 0
```

* setter方法注入

``` cmd
2024-09-26 10:36:27 536 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@248e319b
2024-09-26 10:36:27 646 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 1 bean definitions from class path resource [learn1/target33/applicationContext-User2.xml]
2024-09-26 10:36:27 715 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'user2'
User2(id=2, name=李四, password=456)
```

## 四 依赖注入的应用

### 4.1 相关代码

#### 4.1.1 `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target34/dao/UserDao.java`

```java
package site.icefox.javaeelearn.learn1.target34.dao;

public interface UserDao {
    boolean login(String name, String password);
}
```

#### 4.1.2 `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target34/dao/impl/UserDaoImpl.java`

```java
package site.icefox.javaeelearn.learn1.target34.dao.impl;


import site.icefox.javaeelearn.learn1.target34.dao.UserDao;

public class UserDaoImpl implements UserDao {


    @Override
    public boolean login(String name, String password) {
        if (name.equals("张三") && password.equals("123")) {
            return true;
        }
        return false;
    }
}
```

#### 4.1.3 `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target34/service/UserService.java`

```java
package site.icefox.javaeelearn.learn1.target34.service;

public interface UserService {
     boolean login(String name,String password);
}
```

#### 4.1.4 `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target34/service/impl/UserServiceImpl.java`

```java
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
```

#### 4.1.5 `SpringLearn/src/main/java/site/icefox/javaeelearn/learn1/target34/TestSpring.java`

```java
package site.icefox.javaeelearn.learn1.target34;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.icefox.javaeelearn.learn1.target34.service.UserService;

public class TestSpring {
    public static void main(String[] args) {
        // 加载applicationContext.xml配置
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("learn1/target34/applicationContext.xml");
        UserService userService = (UserService) // 获取配置中的UserService实例
                applicationContext.getBean("userService");
        boolean flag = userService.login("张三", "123");
        if (flag) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
    }
}
```

#### 4.1.6 `SpringLearn/src/main/resources/learn1/target34/applicationContext.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
>
    <bean id="userDao" class="site.icefox.javaeelearn.learn1.target34.dao.impl.UserDaoImpl">

    </bean>

    <bean id="userService" class="site.icefox.javaeelearn.learn1.target34.service.impl.UserServiceImpl">
        <property name="userDao" ref="userDao"/>
    </bean>
</beans>
```

### 4.2 测试结果

输出理应如下：

``` cmd
2024-09-26 10:47:58 430 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@248e319b
2024-09-26 10:47:58 524 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 2 bean definitions from class path resource [learn1/target34/applicationContext.xml]
2024-09-26 10:47:58 593 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userDao'
2024-09-26 10:47:58 600 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userService'
登录成功

进程已结束，退出代码为 0
```

## 五 小结

本章主要讲解了Spring框架的基础知识，首先介绍了Spring框架基础知识，包括了Spring概述、Spring框架的优点、Spring框架的体系结构、Spring 5的新特性和Spring下载及目录结构；然后编写了Spring的入门程序；最后讲解了控制反转与依赖注入，包括控制反转的概念、依赖注入的概念、依赖注入的类型和依赖注入的应用。
