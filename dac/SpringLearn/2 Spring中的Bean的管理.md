## 一 Spring IoC容器

### 1.1 BeanFactory接口

* 常用方法

  | 方法名称                                         | **描述**                                       |
  | ------------------------------------------------ | ---------------------------------------------- |
  | getBean（String name）                           | 根据参数名称获取Bean                           |
  | getBean（String name,Class`<T>` type）             | 根据参数名称、参数类型获取Bean                 |
  | `<T>`T getBean（Class`<T>` requiredType）            | 根据参数类型获取Bean                           |
  | Object getBean（String name,Object... args）     | 根据参数名称获取Bean                           |
  | isTypeMatch  （String name,Resolvable Typetype） | 判断是否有与参数名称、参数类型匹配的Bean       |
  | Class `<?>`getType（String name）                  | 根据参数名称获取类型                           |
  | String[] getAliases（String name）               | 根据实例的名字获取实例的别名数组               |
  | boolean containsBean（String name）              | 根据Bean的名称判断Spring容器是否含有指定的Bean |

* 接口实例的语法格式

  Spring提供了几个BeanFactory接口的实现类，其中最常用的是XmlBeanFactory，它可以读取XML文件并根据XML文件中的配置信息生成BeanFactory接口的实例，BeanFactory接口的实例用于管理Bean。XmlBeanFactory类读取XML文件生成BeanFactory接口实例的具体语法格式如下:

  ``` java
  BeanFactory beanFactory=new XmlBeanFactory (new FileSystemResource("./config.xml"));
  ```

### 1.2 ApplicationContext接口

* 特点

  * ApplicationContext接口建立在BeanFactory接口的基础之上，它丰富了BeanFactory接口的特性，例如，添加了对国际化、资源访问、事件传播等方面的支持。
  * ApplicationContext接口可以为单例的Bean实行预初始化，并根据`<property>`元素执行setter方法，单例的Bean可以直接使用，提升了程序获取Bean实例的性能。

* 常用实现类

  | **类名称**                         | **描述**                                                     |
  | ---------------------------------- | ------------------------------------------------------------ |
  | ClassPathXmlApplicationContext     | 从类路径加载配置文件,实例化ApplicationContext接口            |
  | FileSystemXmlApplicationContext    | 从文件系统加载配置文件,实例化ApplicationContext接口          |
  | AnnotationConfigApplicationContext | 从注解中加载配置文件,实例化ApplicationContext接口            |
  | WebApplicationContext              | 在Web应用中使用,从相对于Web根目录的路径中加载配置文件,实例化ApplicationContext接口 |
  | ConfigurableWebApplicationContext  | 扩展了WebApplicationContext类,它可以通过读取XML配置文件的方式实例化WebApplicationContext类 |

## 二 Bean配置

### 2.1 Spring容器所支持的配置文件格式

Spring容器支持XML和Properties两种格式的配置文件，在实际开发中，最常用的是XML格式的配置文件。XML是标准的数据传输和存储格式，方便查看和操作数据。在Spring中，XML配置文件的根元素是`<beans>`，`<beans>`元素包含`<bean>`子元素，每个`<bean>`子元素可以定义一个Bean，通过`<bean>`元素将Bean注册到Spring容器中。

### 2.2 `<bean>`元素的常用属性

| **属性** | **描述**                                                     |
| -------- | ------------------------------------------------------------ |
| id       | id属性是`<bean>`元素的唯一标识符,Spring容器对Bean的配置和管理通过id属性完成,装配Bean时也需要根据id值获取对象。 |
| name     | name属性可以为Bean指定多个名称,每个名称之间用逗号或分号隔开。 |
| class    | class属性可以指定Bean的具体实现类,其属性值为对象所属类的全路径。 |
| scope    | scope属性用于设定Bean实例的作用范围,其属性值有：singleton（单例）、prototype（原型）、request、session和global session。 |

### 2.3 `<bean>`元素的常用子元素

| **元素**          | **描述**                                          |
| ----------------- | ------------------------------------------------ |
|`<constructor-arg>`|使用`<constructor-arg>`元素可以为Bean的属性指定值。        |
|`<property>`       |`<property>`元素的作用是调用Bean实例中的setter方法完成属性赋值,从而完成依赖注入。|
|ref              | ref是`<property>`、`<constructor-arg>`等元素的属性，可用于指定Bean工厂中某个Bean实例的引用；也可用于指定Bean工厂中某个Bean实例的引用。 |
|value            | value是`<property>`、`<constructor-arg>`等元素的属性，用于直接指定一个常量值；也可以用于直接指定一个常量值。 |
|`<list>`           |`<list>`元素是`<property>`等元素的子元素，用于指定Bean的属性类型为List或数组。|
|`<set>`            |`<set>`元素是`<property>`等元素的子元素，用于指定Bean的属性类型为set。 |
|`<map>`            |`<map>`元素是`<property>`等元素的子元素，用于指定Bean的属性类型为Map。 |
|`<entry>`          | `<entry>`元素是`<map>`元素的子元素，用于设定一个键值对。`<entry>`元素的key属性指定字符串类型的键。|

### 2.4 example

普通的Bean通常只需定义id（或者name）和class两个属性

``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--使用id属性定义bean1，对应的实现类为site.icefox.javaeelearn.Learn2.target31.Bean1-->
    <bean id="bean1" class="site.icefox.javaeelearn.Learn2.target31.Bean1">
    </bean>
    <!--使用name属性定义bean2，对应的实现类为site.icefox.javaeelearn.Learn2.target31.Bean1-->
    <bean name="bean2" class="site.icefox.javaeelearn.Learn2.target31.Bean1"/>
</beans>
```

## 三 Bean的实例化

在pom文件中导入依赖

``` xml
<!--Spring的基础包Spring-expressinon，这里只展示了一个-->
<dependency>
	<groupId>org.springframework</groupId>
    <artifactId>spring-expression</artifactId>
    <version>6.0.8</version>
</dependency>
```

### 3.1 构造方法实例化

#### 3.1.1Java

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target31/Bean1.java`

  ``` java
  package site.icefox.javaeelearn.Learn2.target31;
  
  public class Bean1 {
      public Bean1() {
          System.out.println("这是Bean1");
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target31/Bean1Test.java`

  ``` java
  package site.icefox.javaeelearn.Learn2.target31;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class Bean1Test {
      public static void main(String[] args) {
          // 加载applicationBean1.xml配置
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("/learn2/target31/applicationBean1.xml");
          // 通过容器获取配置中bean1的实例
          Bean1 bean = (Bean1) applicationContext.getBean("bean1");
          System.out.print(bean);
      }
  }
  ```

#### 3.1.2 resources

* `SpringLearn/src/main/resources/learn2/target31/applicationBean1.xml`

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd">
<bean id="bean1" class="site.icefox.javaeelearn.Learn2.target31.Bean1"/>
</beans>
```

#### 3.1.3 预期结果

``` cmd
这是Bean1
site.icefox.javaeelearn.Learn2.target31.Bean1@39a2bb97
进程已结束，退出代码为 0
```

### 3.2 静态工厂实例化

#### 3.2.1 Java

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target32/Bean2.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target32;
  
  public class Bean2 {
      public Bean2() {
          System.out.println("这是Bean2");
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target32/Bean2Test.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target32;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class Bean2Test {
      public static void main(String[] args) {
          // ApplicationContext在加载配置文件时，对Bean进行实例化
          ApplicationContext applicationContext =
                  new ClassPathXmlApplicationContext("learn2/target32/applicationBean2.xml");
          System.out.println(applicationContext.getBean("bean2"));
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target32/MyBean2Factory.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target32;
  
  public class MyBean2Factory {
      //使用MyBean2Factory类的工厂创建Bean2实例
      public static Bean2 createBean() {
          return new Bean2();
      }
  }
  ```


#### 3.2.2 resources

* `SpringLearn/src/main/resources/learn2/target32/applicationBean2.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
    <bean id="bean2"
          class="site.icefox.javaeelearn.Learn2.target32.MyBean2Factory"
          factory-method="createBean"/>
</beans>
```

#### 3.2.3 预期结果

``` cmd
这是Bean2
site.icefox.javaeelearn.Learn2.target32.Bean2@4b79ac84
进程已结束，退出代码为 0
```

### 3.3 实例工厂实例化

#### 3.3.1 Java

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target33/Bean3.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target33;
  
  public class Bean3 {
      public Bean3() {
          System.out.println("这是Bean3");
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target33/Bean3Test.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target33;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class Bean3Test {
      public static void main(String[] args) {
          // ApplicationContext在加载配置文件时，对Bean进行实例化
          ApplicationContext applicationContext =
                  new ClassPathXmlApplicationContext("learn2/target33/applicationBean3.xml");
          System.out.println(applicationContext.getBean("bean3"));
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target33/MyBean3Factory.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target33;
  
  public class MyBean3Factory {
      public MyBean3Factory() {
          System.out.println("bean3工厂实例化中");
      }
  
      public Bean3 createBean() { //创建Bean3实例的方法
          return new Bean3();
      }
  }
  ```

#### 3.3.2 resource

* `SpringLearn/src/main/resources/learn2/target33/applicationBean3.xml`

  ```xml
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans-4.3.xsd">
      <!-- 配置工厂 -->
      <bean id="myBean3Factory" class="site.icefox.javaeelearn.Learn2.target33.MyBean3Factory"/>
      <!-- 使用factory-bean属性指向配置的实例工厂-->
      <bean id="bean3" factory-bean="myBean3Factory" factory-method="createBean"/>
  </beans>
  ```

#### 3.3.3 预期结果

``` cmd
bean3工厂实例化中
这是Bean3
site.icefox.javaeelearn.Learn2.target33.Bean3@6dab9b6d
进程已结束，退出代码为 0
```

## 四 Bean的作用域

### 4.1 Spring支持的5种作用域

| **作用域名城** | **描述**                                                     |
| -------------- | ------------------------------------------------------------ |
| singleton      | 单例模式。在单例模式下，Spring 容器中只会存在一个共享的Bean实例，所有对Bean的请求，只要请求的id（或name）与Bean的定义相匹配，会返回Bean的同一个实例。 |
| prototype      | 原型模式，每次从容器中请求Bean时，都会产生一个新的实例。     |
| request        | 每一个HTTP请求都会有自己的Bean实例，该作用域只能在基于Web的Spring ApplicationContext中使用。 |
| session        | 每一个HTTPsession请求都会有自己的Bean实例，该作用域只能在基于Web的Spring ApplicationContext中使用。 |
| global session | 限定一个Bean的作用域为Web应用（HTTPsession）的生命周期，只有在Web应用中使用Spring时，该作用域才有效。 |

### 4.2 测试代码

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target40/Bean1.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target40;
  
  public class Bean1 {
      public Bean1() {
          System.out.println("这是Bean1");
      }
  }
  ```

* `SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target40/scopeTest.java`

  ```java
  package site.icefox.javaeelearn.Learn2.target40;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  
  public class scopeTest {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("/learn2/target40/applicationBean1.xml");
          Bean1 bean1_1 = (Bean1) applicationContext.getBean("bean1");
          Bean1 bean1_2 = (Bean1) applicationContext.getBean("bean1");
          System.out.print(bean1_1 == bean1_2);
      }
  }
  ```

* `SpringLearn/src/main/resources/learn2/target40/applicationBean1.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans.xsd">
  <!--    <bean id="bean1" class="site.icefox.javaeelearn.Learn2.target40.Bean1" scope="singleton"/>-->
      <bean id="bean1" class="site.icefox.javaeelearn.Learn2.target40.Bean1" scope="prototype"/>
  </beans>
  ```

### 4.3 预期结果

``` cmd
这是Bean1
这是Bean1
false
进程已结束，退出代码为 0
```

## 五 Bean的装配方式

### 5.1 基于XML的装配

在基于XML的装配就是读取XML配置文件中的信息完成依赖注入，Spring容器提供了两种基于XML的装配方式，属性setter方法注入和构造方法注入。下面分别对这两种装配方式进行介绍。

#### 5.1.1 属性setter方法注入

属性setter方法注入要求一个Bean必须满足以下两点要求。

1. Bean类必须提供一个默认的无参构造方法。
2. Bean类必须为需要注入的属性提供对应的setter方法。

#### 5.1.2 构造方法注入 

使用构造方法注入时，在配置文件里，需要使用`<bean>`元素的子元素`<constructor-arg>`来定义构造方法的参数，例如，可以使用其value属性（或子元素）来设置该参数的值

### 5.2 基于注解的装配

在Spring中，使用XML配置文件可以实现Bean的装配工作，但在实际开发中如果Bean的数量较多，会导致XML配置文件过于臃肿，给后期维护和升级带来一定的困难。为解决此问题，Spring提供了注解，通过注解也可以实现Bean的装配。

### 5.2.1 Spring的常用注解

| **注解**       | **描述**                                                     |
| -------------- | ------------------------------------------------------------ |
| @Component     | 指定一个普通的Bean，可以作用在任何层次。                     |
| @Controller    | 指定一个控制器组件Bean，用于将控制层的类标识为Spring中的Bean，功能上等同于@Component。 |
| @Service       | 指定一个业务逻辑组件Bean，用于将业务逻辑层的类标识为Spring中的Bean，功能上等同于@Component。 |
| @Repository    | 指定一个数据访问组件Bean，用于将数据访问层的类标识为Spring 中的Bean，功能上等同于@Component。 |
| @Scope         | 指定Bean实例的作用域。                                       |
| @Value         | 指定Bean实例的注入值。                                       |
| @Autowired     | 指定要自动装配的对象。                                       |
| @Resource      | 指定要注入的对象。                                           |
| @Qualifier     | 指定要自动装配的对象名称，通常与@Autowired联合使用。         |
| @PostConstruct | 指定Bean实例完成初始化后调用的方法。                         |
| @PreDestroy    | 指定Bean实例销毁前调用的方法。                               |

#### 5.2.2 示例代码

见 [配套代码](https://github.com/XiaoTangr/JavaEELearn) ，位置为`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target50`

## 六 Bean的生命周期

### 6.1 Bean在不同作用域内的生命周期

Bean的生命周期是指Bean实例被创建、初始化和销毁的过程。在Bean的两种作用域singleton和prototype中，Spring容器对Bean的生命周期的管理是不同的。在singleton作用域中，Spring容器可以管理Bean的生命周期，控制着Bean的创建、初始化和销毁。在prototype作用域中， Spring容器只负责创建Bean实例，不会管理其生命周期

### 6.2 Bean生命周期的两个时间节点 

在Bean的生命周期中，有两个时间节点尤为重要，这两个时间节点分别是Bean实例初始化后和Bean实例销毁前，在这两个时间节点通常需要完成一些指定操作。因此，常常需要对这两个节点进行监控。

### 6.3 监控时间节点的方式 

监控两个节点的方式有两种，一种是使用XML配置文件，一种是使用注解。

### 6.4 示例代码

见 [配套代码](https://github.com/XiaoTangr/JavaEELearn) ，位置为`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn2/target60`

## 七 结语

本章主要讲解了Spring对Bean的管理。首先介绍了Spring IoC容器，包括BeanFactory接口和ApplicationContext接口；其次讲解了Bean的两种配置方式，包括属性setter方法注入和构造方法注入；接着讲解了Bean 的3种实例化方法，包括构造方法实例化、静态工厂实例化和实例工厂实例化；然后讲解了Bean的作用域，包括singleton作用域和prototype作用域；接着讲解了Bean的3种装配方式，包括基于XML的装配、基于注解的装配和自动装配，最后讲解了Bean的生命周期。通过本章的学习，读者可以对Spring中Bean的管理有基本的了解，为以后框架开发奠定基础。 
