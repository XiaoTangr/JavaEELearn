数据库用于处理持久化业务产生的数据，应用程序在运行过程中经常要操作数据库。一般情况下，数据库的操作由持久层来实现。作为扩展性较强的一站式开发框架，Spring也提供了持久层Spring JDBC功能，Spring JDBC可以管理数据库连接资源，简化传统JDBC的操作，进而提升程序数据库操作的效率。本章将对Spring JDBC相关知识进行详细讲解。 

## 一 Spring JDBC

### 1.1 JDBC Template概述

#### 1.1.1 JDBCTemplate作用

针对数据库操作，Spring框架提供了JdbcTemplate类，JdbcTemplate是一个模板类，Spring JDBC中的更高层次的抽象类均在JdbcTemplate模板类的基础上创建。

JdbcTemplate类提供了操作数据库的基本方法，包括添加、删除、查询和更新。在操作数据库时，JdbcTemplate类简化了传统JDBC中的复杂步骤，这可以让开发人员将更多精力投入到业务逻辑中

#### 1.1.2 抽象类JdbcAccessor的属性

​    JdbcTemplate类继承自抽象类JdbcAccessor，同时实现了JdbcTemplate接口。抽象类JdbcAccessor提供了一些访问数据库时使用的公共属性，具体如下：

* DataSource：DataSource主要功能是获取数据库连接。在具体的数据操作中，它还提供对数据库连接的缓冲池和分布式事务的支持。
* SQLExceptionTranslator：SQLExceptionTranslator是一个接口，它负责对SQLException异常进行转译工作。

### 1.2 Spring JDBC的配置

#### 1.2.1 Spring JDBC中的4个包说明

| 包名                 | 说明                                                         |
| -------------------- | ------------------------------------------------------------ |
| core(核心包)         | 包含了JDBC的核心功能,包括JdbcTemplate类、SimpleJdbcInsert类、SimpleJdbcCall类以及NamedParameterJdbcTemplate类 |
| dataSource(数据源包) | 包含访问数据源的实用工具类,它有多种数据源的实现,可以在Java EE容器外部测试JDBC代码 |
| object(对象包)       | 以面向对象的方式访问数据库,它可以执行查询、修改和更新操作并将返回结果作为业务对象，并且在数据表的列和业务对象的属性之间映射查询结果 |
| support(支持包)      | 包含了core和object包的支持类，如提供异常转换功能的SQLException类 |

## 二 JDBCTemplate的增删改查操作

> 前置操作
>
> 1. 创建数据库
>
> 	``` sql
> 	create database spring;
> 	```
>
> 2. 在pom文件添加依赖
>
>    ``` xml
>            <dependency>
>                <groupId>mysql</groupId>
>                <artifactId>mysql-connector-java</artifactId>
>                <version>8.0.28</version>
>            </dependency>
>    ```
>
>    

### 2.1 excute()方法

无特殊说明。

### 2.2 update()方法

JdbcTemplate类中常用的update()方法:

* int update(String sql) 
  该方法是最简单的update()方法重载形式,它直接执行传入的SQL语句,并返回受影响的行数

* int update(PreparedStatementCreator psc) 
  该方法执行参数psc返回的语句,然后返回受影响的行数

* int update(String sql, PreparedStatementSetter pss) 
  该方法通过参数pss设置SQL语句中的参数,并返回受影响的行数

* int update(String sql,Object… args)
  该方法可以为SQL语句设置多个参数,这些参数保存在参数args中,使用Object…设置SQL语句中的参数,要求参数不能为NULL,并返回受影响的行数

### 2.3 query()方法

JdbcTemplate类中常用的查询方法:

* List query(String sql, RowMapper rowMapper) 
  执行String类型参数提供的SQL语句,并通过参数rowMapper返回一个List类型的结果

* List query(String sql, PreparedStatementSetter pss,   RowMapper rowMapper) | 根据String类
  参数提供的SQL语句创建PreparedStatement对象,通过参数rowMapper将结果返回到List中
  
* List query(String sql, Object[] args, RowMapper rowMapper)
  使用Object[]的值来设置SQL语句中的参数值,rowMapper是个回调方法,直接返回List类型的数据

* queryForObject(String sql, RowMapper rowMapper, Object… args) 
  将args参数绑定到SQL语句中,并通过参数rowMapper返回一个Object类型的单行记录

* queryForList(String sql,Object[] args, class\<T> elementType)
  该方法可以返回多行数据的结果,但必须返回列表,args参数是sql语句中的参数,elementType参数返回的是List数据类型 |

#### 2.4 示例代码

> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn4/`下(注意Java代码位于不同子包下)
>
> Xml代码位于`SpringLearn/src/main/resources/learn4/`下

* Dao.AccountDao.java
  ``` java
  package site.icefox.javaeelearn.Learn4.Dao;
  
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  import java.util.List;
  
  public interface AccountDao {
  
      int addAccount(Account account);
  
      int updateAccount(Account account);
  
      int deleteAccount(int id);
  
      Account selectAccountById(int id);
  
      List<Account> selectAccounts();
  }
  ```
  
* Dao.AccountDaoImpl.java
  ``` java
  package site.icefox.javaeelearn.Learn4.Dao;
  
  import lombok.Setter;
  import org.springframework.dao.DataAccessException;
  import org.springframework.jdbc.core.BeanPropertyRowMapper;
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.jdbc.core.RowMapper;
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  import java.util.List;
  
  @Setter
  public class AccountDaoImpl implements AccountDao {
  
      private JdbcTemplate jdbcTemplate;
  
      public int addAccount(Account account) {
          String sql = "INSERT INTO account (username, balance) VALUES (?,?)";
          Object Params = new Object[]{account.getUsername(), account.getBalance()};
          return this.jdbcTemplate.update(sql, Params);
      }
  
  
      public int updateAccount(Account account) {
          String sql = "UPDATE account SET username=?, balance=? WHERE id=?";
          Object Params = new Object[]{account.getUsername(), account.getBalance(), account.getId()};
          return this.jdbcTemplate.update(sql, Params);
      }
  
  
      public int deleteAccount(int id) {
          String sql = "delete from account where id =?;";
          return this.jdbcTemplate.update(sql, id);
      }
  
  
      public Account selectAccountById(int id) {
          try {
              String sql = "select * from account where id = ?";
              RowMapper<Account> rowMapper =
                      new BeanPropertyRowMapper<>(Account.class);
              return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
          } catch (DataAccessException e) {
              return null;
          }
      }
  
  
      public List<Account> selectAccounts() {
          String sql = "select * from account";
          RowMapper<Account> rowMapper =
                  new BeanPropertyRowMapper<>(Account.class);
          return this.jdbcTemplate.query(sql, rowMapper);
      }
  }
  ```
* Entity.Account.java
  ``` java
  package site.icefox.javaeelearn.Learn4.Entity;
  
  import lombok.Data;
  
  @Data
  public class Account {
      private Integer id;                       // 账户id
      private String username;    // 用户名
      private Double balance;    // 账户余额
  
      public String toString() {
          return "Account [id=" + id + ", "
                  + "username=" + username + ", balance=" + balance + "]";
      }
  }
  ```
* TestCUDR.java
  ``` java
  package site.icefox.javaeelearn.Learn4;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import site.icefox.javaeelearn.Learn4.Dao.AccountDao;
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  public class TestCUDR {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("learn4/applicationContext.xml");
          AccountDao accountDao =
                  (AccountDao) applicationContext.getBean("accountDao");
  
          Account account;
  
          //添加
          Account newaccount = new Account();
          newaccount.setUsername("张三");
          newaccount.setBalance(1.55);
          accountDao.addAccount(newaccount);
  
          //删除
          int result = accountDao.deleteAccount(99);
          if (result > 0) {
              System.out.println("删除数据成功！");
          } else {
              System.out.println("删除数据失败！");
          }
  
          //查询
          account = accountDao.selectAccountById(99);
          System.out.println(account);
          System.out.println(accountDao.selectAccounts());
  
          //更新
          account.setUsername("李四");
          account.setBalance(1000.1);
          accountDao.updateAccount(account);
          System.out.println("更新后的数据为：" + accountDao.selectAccountById(99));
  
      }
  }
  ```
* TestJdbcTemplate.java
  ``` java
  package site.icefox.javaeelearn.Learn4;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import org.springframework.jdbc.core.JdbcTemplate;
  
  //2.1 execute()方法
  public class TestJdbcTemplate {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("learn4/applicationContext.xml");
          JdbcTemplate jdTemplate =
                  (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
          jdTemplate.execute("create table account(" +
                  "id int primary key auto_increment," +
                  "username varchar(50), " + "balance double)");
          System.out.println("账户表account创建成功！");
      }
  }
  ```
* applicationContext.xml
  ``` xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="
                             http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/tx 
                             http://www.springframework.org/schema/tx/spring-tx.xsd
                             http://www.springframework.org/schema/aop 
                             https://www.springframework.org/schema/aop/spring-aop.xsd
                             ">
      <!-- 1.配置数据源 -->
      <bean id="dataSource"
            class="org.springframework.jdbc.datasource.DriverManagerDataSource">
          <!-- 1.1.数据库驱动 -->
          <property name="driverClassName"
                    value="com.mysql.cj.jdbc.Driver"/>
          <!-- 1.2.连接数据库的url -->
          <property name="url"
                    value="jdbc:mysql://localhost:3306/spring?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"/>
          <!-- 1.3.连接数据库的用户名 -->
          <property name="username" value="root"/>
          <!-- 1.4.连接数据库的密码 -->
          <property name="password" value="root"/>
      </bean>
  
      <!-- 2配置JDBC模板 -->
      <bean id="jdbcTemplate"
            class="org.springframework.jdbc.core.JdbcTemplate">
          <!-- 默认必须使用数据源 -->
          <property name="dataSource" ref="dataSource"/>
      </bean>
      
      <!--3.定义id为accountDao的Bean：将jdbcTemplate注入到AccountDao实例中 -->
      <bean name="accountDao" class="site.icefox.javaeelearn.Learn4.Dao.AccountDaoImpl">
          <property name="jdbcTemplate" ref="jdbcTemplate"/>
      </bean>
  </beans>
  ```

## 三 Spring事务管理概述

### 3.1 事务管理的核心接口

spring-tx依赖包的3个接口

* PlatformTransactionManager接口：可以根据属性管理事务。
*  TransactionDefinition接口：用于定义事务的属性
* TransactionStatus接口：用于界定事务的状态 

#### 3.1.1 PlatformTransactionManager接口

| **方法**                                | **说明**             |
| --------------------------------------- | -------------------- |
| TransactionStatus                       | 用于获取事务状态信息 |
| void commit(TransactionStatus status)   | 用于提交事务         |
| void rollback(TransactionStatus status) | 用于回滚事务         |

#### 3.1.2 TransactionDefinition接口

TransactionDefinition接口常用方法

| **方法**                     | **说明**           |
| ---------------------------- | ------------------ |
| int getPropagationBehavior() | 返回事务的传播行为 |
| int getIsolationLevel()      | 返回事务的隔离层次 |
| int getTimeout()             | 返回事务的超时属性 |
| boolean isReadOnly()         | 判断事务是否为只读 |
| String getName()             | 返回定义的事务名称 |

TransactionDefinition接口中定义了事务描述相关的常量，其中包括了事务的隔离级别、事务的传播行为、事务的超时时间和是否为只读事务。下面对这几种常量做详细讲解。


* 事务的隔离级别

  | **隔离级别**               | **说明**                                                     |
  | -------------------------- | ------------------------------------------------------------ |
  | ISOLATION_DEFAULT          | 采用当前数据库默认的事务隔离级别。                           |
  | ISOLATION_READ_UNCOMMITTED | 读未提交。允许另外一个事务读取到当前未提交的数据，隔离级别最低，可能会导致脏读、幻读或不可重复读。 |
  | ISOLATION_READ_COMMITTED   | 读已提交。被一个事务修改的数据提交后才能被另一个事务读取，可以避免脏读，无法避免幻读，而且不可重复读。 |
  | ISOLATION_ REPEATABLE_READ | 允许重复读，可以避免脏读，资源消耗上升。这是MySQL数据库的默认隔离级别。 |
  | REPEATABLE_SERIALIZABLE    | 事务串行执行，也就是按照时间顺序一一执行多个事务，不存在并发问题，最可靠，但性能与效率最低。 |

* 事务的传播行为

  事务的传播行为是指处于不同事务中的方法在相互调用时，方法执行期间，事务的维护情况。例如，当一个事务的方法B调用另一个事务的方法A时，可以规定A方法继续在B方法所属的现有事务中运行，也可以规定A方法开启一个新事务，在新事务中运行，B方法所属的现有事务先挂起，等A方法的新事务执行完毕后再恢复。

* TransactionDefinition接口中定义的7种事务传播行为

  | **传播行为**              | **说明**                                                     |
  | ------------------------- | ------------------------------------------------------------ |
  | PROPAGATION_REQUIRED      | 默认的事务传播行为。如果当前存在一个事务，则加入该事务；如果当前没有事务，则创建一个新的事务。 |
  | PROPAGATION_SUPPORTS      | 读未提交。允许如果当前存在一个事务，则加入该事务；如果当前没有事务，则以非事务方式执行。 |
  | PROPAGATION_MANDATORY     | 当前必须存在一个事务，如果没有，就抛出异常。                 |
  | PROPAGATION_REQUIRES_NEW  | 创建一个新的事务，如果当前已存在一个事务，将已存在的事务挂起。 |
  | PROPAGATION_NOT_SUPPORTED | 不支持事务，在没有事务的情况下执行，如果当前已存在一个事务，则将已存在的事务挂起。 |
  | ROPAGATION_NEVER          | 永远不支持当前事务，如果当前已存在一个事务，则抛出异常。     |
  | PROPAGATION_NESTED        | 如果当前存在事务，则在当前事务的一个子事务中执行。           |

* 事务的超时时间

  事务的超时时间是指事务执行的时间界限，超过这个时间界限，事务将会回滚。TransactionDefinition接口提供了TIMEOUT_DEFAULT常量定义事务的超时时间。

* 事务是否只读

  ​	当事务为只读时，该事务不修改任何数据，只读事务有助于提升性能，如果在只读事务中修改数据，会引发异常。

  ​	TransactionDefinition接口中除了提供事务的隔离级别、事务的传播行为、事务的超时时间和是否为只读事务的常量外，还提供了一系列方法来获取事务的属性。

#### 3.1.3 TransactionStatus接口

| **方法**                   | **说明**                               |
| -------------------------- | -------------------------------------- |
| boolean isNewTransaction() | 判断当前事务是否为新事务               |
| boolean hasSavepoint()     | 判断当前事务是否创建了一个保存点       |
| boolean isRollbackOnly()   | 判断当前事务是否被标记为rollback-only  |
| void setRollbackOnly()     | 将当前事务标记为rollback-only          |
| boolean isCompleted()      | 判断当前事务是否已经完成（提交或回滚） |
| void flush()               | 刷新底层的修改到数据库                 |

### 3.2 事务管理的方式

Spring中的事务管理分为两种方式，一种是传统的编程式事务管理，另一种是声明式事务管理。

**编程式事务管理**：通过编写代码实现的事务管理，包括定义事务的开始、正常执行后的事务提交和异常时的事务回滚。

**声明式事务管理**：通过AOP技术实现的事务管理，其主要思想是将事务管理作为一个“切面”代码单独编写，然后通过AOP技术将事务管理的“切面”代码植入到业务目标类中。

## 四 声明式事务管理

#### 4.1 基于XML方式的声明式事务

基于XML方式的声明式事务管理是通过在配置文件中配置事务规则的相关声明来实现的。在使用XML文件配置声明式事务管理时，首先要引入tx命名空间，在引入tx命名空间之后，可以使用<tx:advice>元素来配置事务管理的通知，进而通过Spring AOP实现事务管理

#### 4.1.1 \<tx:method>元素的常用属性： 

| **属性**        | **说明**                                                     |
| --------------- | ------------------------------------------------------------ |
| name            | 用于指定方法名的匹配模式，该属性为必选属性，它指定了与事务属性相关的方法名。 |
| propagation     | 用于指定事务的传播行为。                                     |
| isolation       | 用于指定事务的隔离级别。                                     |
| read-only       | 用于指定事务是否只读。                                       |
| timeout         | 用于指定事务超时时间。                                       |
| rollback-for    | 用于指定触发事务回滚的异常类。                               |
| no-rollback-for | 用于指定不触发事务回滚的异常类。                             |

> 未使用事物管理的缺陷
>
> 由XML方式实现声明式事务管理的案例可知，zhangsan的账户余额增加了100，而lisi的账户确没有任何变化，这样的情况显然是不合理的。这就是没有事务管理，系统无法保证数据的安全性与一致性，下面使用事务管理解决该问题

#### 4.1.2 示例代码

> 相关依赖
>
> ``` xml
>         <dependency>
>             <groupId>org.aspectj</groupId>
>             <artifactId>aspectjweaver</artifactId>
>             <version>1.9.6</version>
>         </dependency>
> 
>         <dependency>
>             <groupId>aopalliance</groupId>
>             <artifactId>aopalliance</artifactId>
>             <version>1.0</version>
>         </dependency>
> ```

> 注意区分新建文件还是已有文件
>
> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn4/`下(注意Java代码位于不同子包下)
>
> Xml代码位于`SpringLearn/src/main/resources/learn4/`下

* Dao.AccountDao.java(existed file)

  ``` java
  package site.icefox.javaeelearn.Learn4.Dao;
  
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  public interface AccountDao {
  	//省略的方法
      void transfer(String outUser, String inUser, Double money);
  }
  ```

* Dao.AccountDaoImpl.java(existed file)

  ``` java
  package site.icefox.javaeelearn.Learn4.Dao;
  
  import lombok.Setter;
  import org.springframework.dao.DataAccessException;
  import org.springframework.jdbc.core.BeanPropertyRowMapper;
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.jdbc.core.RowMapper;
  import org.springframework.transaction.annotation.Isolation;
  import org.springframework.transaction.annotation.Propagation;
  import org.springframework.transaction.annotation.Transactional;
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  import java.util.List;
  
  @Setter
  public class AccountDaoImpl implements AccountDao {
  	//	省略的方法
      
      /**
       * 转账
       *
       * @param outUser 汇款人
       * @param inUser  收款人
       * @param money   收款金额
       */
  //    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
      public void transfer(String outUser, String inUser, Double money) {
          // 收款时，收款用户的余额=现有余额+所汇金额
          this.jdbcTemplate.update("update account set balance = balance +? "
                  + "where username = ?", money, inUser);
          // 模拟系统运行时的突发性问题
          int i = 1 / 0;
          // 汇款时，汇款用户的余额=现有余额-所汇金额
          this.jdbcTemplate.update("update account set balance = balance-? "
                  + "where username = ?", money, outUser);
      }
  }
  ```

* TransactionTest.java

  ``` java
  package site.icefox.javaeelearn.Learn4;
  
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import site.icefox.javaeelearn.Learn4.Dao.AccountDao;
  
  public class TransactionTest {
      public static void main(String[] args) {
          ApplicationContext applicationContext =
                  new ClassPathXmlApplicationContext("learn4/applicationContext.xml");
          // 获取AccountDao实例
          AccountDao accountDao =
                  (AccountDao) applicationContext.getBean("accountDao");
          // 调用实例中的转账方法
          accountDao.transfer("lisi", "zhangsan", 100.0);
          // 输出提示信息
          System.out.println("转账成功！");
      }
  }
  ```

* applicationContext.xml(existed file)

  ``` xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!-- 引入命名空间，注意aop和tx的引入-->
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="
                             http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/tx 
                             http://www.springframework.org/schema/tx/spring-tx.xsd
                             http://www.springframework.org/schema/aop 
                             https://www.springframework.org/schema/aop/spring-aop.xsd
                             ">
     
  	<!-- 省略的bean配置 -->
      <!-- 4.事务管理器，依赖于数据源 -->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      <!-- 5.编写通知，需要编写对切入点和具体执行事务细节-->
      <tx:advice id="txAdvice" transaction-manager="transactionManager">
          <tx:attributes>
              <tx:method name="*" propagation="REQUIRED"
                         isolation="DEFAULT" read-only="false"/>
          </tx:attributes>
      </tx:advice>
      <!-- 6.编写aop，使用AspectJ的表达式，让spring自动对目标生成代理-->
      <aop:config>
          <aop:pointcut expression="execution(* site.icefox.javaeelearn.Learn4.target20.*.*(..))"
                        id="txPointCut"/>
          <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
      </aop:config>
      <tx:annotation-driven transaction-manager="transactionManager"/>
  </beans>
  ```

#### 4.2 基于注解方式的声明式事务

#### 4.2.1 @Transactional的属性

| **属性**               | **说明**                                 |
| ---------------------- | ---------------------------------------- |
| value                  | 用于指定使用的事务管理器                 |
| propagation            | 用于指定事务的传播行为                   |
| isolation              | 用于指定事务的隔离级别                   |
| timeout                | 用于指定事务的超时时间                   |
| readonly               | 用于指定事务是否为只读                   |
| rollbackFor            | 用于指定导致事务回滚的异常类数组         |
| rollbackForClassName   | 用于指定导致事务回滚的异常类名称数组     |
| noRollbackFor          | 用于指定不会导致事务回滚的异常类数组     |
| noRollbackForClassName | 用于指定不会导致事务回滚的异常类名称数组 |

#### 4.2.2 示例代码

> 注意区分新建文件还是已有文件
>
> Java代码位于`SpringLearn/src/main/java/site/icefox/javaeelearn/Learn4/`下(注意Java代码位于不同子包下)
>
> Xml代码位于`SpringLearn/src/main/resources/learn4/`下

* Dao.AccountDaoImpl.java(existed file)

  ``` java
  package site.icefox.javaeelearn.Learn4.Dao;
  
  import lombok.Setter;
  import org.springframework.dao.DataAccessException;
  import org.springframework.jdbc.core.BeanPropertyRowMapper;
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.jdbc.core.RowMapper;
  import org.springframework.transaction.annotation.Isolation;
  import org.springframework.transaction.annotation.Propagation;
  import org.springframework.transaction.annotation.Transactional;
  import site.icefox.javaeelearn.Learn4.Entity.Account;
  
  import java.util.List;
  
  @Setter
  public class AccountDaoImpl implements AccountDao {
  	//	省略的方法
      
      /**
       * 转账
       *
       * @param outUser 汇款人
       * @param inUser  收款人
       * @param money   收款金额
       */
      @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
      public void transfer(String outUser, String inUser, Double money) {
          // 收款时，收款用户的余额=现有余额+所汇金额
          this.jdbcTemplate.update("update account set balance = balance +? "
                  + "where username = ?", money, inUser);
          // 模拟系统运行时的突发性问题
          int i = 1 / 0;
          // 汇款时，汇款用户的余额=现有余额-所汇金额
          this.jdbcTemplate.update("update account set balance = balance-? "
                  + "where username = ?", money, outUser);
      }
  }
  ```

* AnnotationTest.java

  ``` java
  public class AnnotationTest {
      public static void main(String[] args) {
          ApplicationContext applicationContext = new
                  ClassPathXmlApplicationContext("learn4/applicationContext-annotation.xml");
          // 获取AccountDao实例
          AccountDao accountDao =
                  (AccountDao) applicationContext.getBean("accountDao");
          // 调用实例中的转账方法
          accountDao.transfer("lisi", "zhangsan", 100.0);
          // 输出提示信息
          System.out.println("转账成功！");
      }
  }
  ```

* applicationContext-annotation.xml

  ``` xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="
                             http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/tx 
                             http://www.springframework.org/schema/tx/spring-tx.xsd
                             http://www.springframework.org/schema/aop 
                             https://www.springframework.org/schema/aop/spring-aop.xsd
                             ">
      <!-- 省略的bean配置 -->
  
      <!-- 4.事务管理器，依赖于数据源 -->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  
      <!-- 5.注册事务管理器驱动 -->
      <tx:annotation-driven transaction-manager="transactionManager"/>
  </beans>
  ```

  