## 一 核心对象

### SqlSessionFactoryBuilder

![image-20240919135931269](https://ftp.icefox.site/blog/images/202409191359604.png)

SqlSessionFactoryBuilder包含以上方法，常用的有

* build(InputStream inputStream,String environment,Properties properties)
* build(Reader reader,String environment,Properties properties)
* build(Configuration config)

这三个 `build` 方法用于构建配置对象，区别在于传入的参数类型不同。`build(InputStream inputStream, String environment, Properties properties)` 通过输入流 `InputStream` 读取配置文件，适合处理二进制或非文本数据；`build(Reader reader, String environment, Properties properties)` 则使用 `Reader` 读取字符流，适合文本配置文件。两者都要求传入 `environment`（环境名称）和 `properties`（额外的配置项）。而 `build(Configuration config)` 直接接收一个 `Configuration` 对象，适用于已经有完整配置实例的情况，无需再提供流、环境或属性，从而简化了配置构建过程。

> 在示例工程中使用的是第一种 (位于`src/main/java/site/icefox/javaeelearn/Util/DBConn.java`中)
>
> ``` java
>          String resource = "mybatis-config.xml";
>          InputStream inputStream = Resources.getResourceAsStream(resource);
>          sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
> ```

SqlSessionFactory对象是线程安全的，它一旦被创建，在整个应用程序执行期间都会存在。如果我们多次创建同一个数据库的SqlSessionFactory对象，那么该数据库的资源将很容易被耗尽。通常每一个数据库都只创建一个SqlSessionFactory对象，所以在构建SqlSessionFactory对象时，建议使用单例模式。

### SqlSessionFactory

#### openSession()方法

| **方法名称**                                              | **描述**                           |
| --------------------------------------------------------- | ---------------------------------- |
| openSession()                                             | 开启一个事务。                     |
| openSession(Boolean autoCommit)                           | 参数autoCommit可设置是否开启事务。 |
| openSession(Connection connection)                        | 参数connection可提供自定义连接。   |
| openSession(TransactionIsolationLevel level)              | 参数level可设置隔离级别。          |
| openSession(ExecutorType execType)                        | 参数execType有三个可选值。         |
| openSession(ExecutorType execType，Boolean autoCommit)    | 参数execType有三个可选值。         |
| openSession(ExecutorType execType，Connection connection) | 参数ExecutorType有三个可选值。     |

参数autoCommit可设置是否开启事务。

参数connection可提供自定义连接。

参数 execType 有三个可选值:

> * ExecutorType.SIMPLE：表示为每条语句创建一条新的预处理语句。
> * ExecutorType.REUSE：表示会复用预处理语句。
> * ExecutorType.BATCH：表示会批量执行所有更新语句。

### SqlSession

SqlSession是MyBatis框架中另一个重要的对象，它是应用程序与持久层之间执行交互操作的一个单线程对象，主要作用是执行持久化操作，类似于JDBC中的Connection。SqlSession对象包含了执行SQL操作的方法，由于其底层封装了JDBC连接，所以可以直接使用SqlSession对象来执行已映射的SQL语句。

每一个线程都应该有一个自己的SqlSession对象，并且该对象不能共享。SqlSession对象是线程不安全的，因此其使用范围最好在一次请求或一个方法中，绝不能将其放在类的静态字段、对象字段或任何类型的管理范围（如Servlet的HttpSession）中使用。SqlSession对象使用完之后，要及时的关闭，SqlSession对象通常放在finally块中关闭，代码如下所示。

``` java
SqlSession sqlSession = sqlSessionFactory.openSession();
try {
	//TODO 持久化操作
}
finally 
{
    sqlSession.close();
}
```

> 在示例工程中的例子（位于`src/main/java/site/icefox/javaeelearn/Dao/UsersDao.java`中）
>
> ``` java
>     public static UsersEntity getUsersByUid(int uid) {
>         UsersEntity result = null;
>       	//  此处使用try-with-resource语法调用工具类中的getSqlSession()方法获取SqlSession
>         try (SqlSession sqlSession = DBConn.getSqlSession()) {
>             // 持久化操作
>             UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
>             result = usersmapper.getUserByUid(uid);
>         } catch (Exception e) {
>             logger.severe(e.getMessage());
>         }
>         return result;
>     }
> ```
>
> 

## 二 核心配置文件

### 2.1 文件结构

`<configuration>`元素是整个XML配置文件的根元素，相当于MyBatis各元素的管理员。`<configuration>`有很多子元素，MyBatis的核心配置就是通过这些子元素完成的。需要注意的是，在核心配置文件中，`<configuration>`的子元素必须按照如下结构顺序进行配置，否则MyBatis在解析XML配置文件的时候会报错。（部分配置项可以省略）

``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!-- propites -->
    <properties />
    
    <!-- 1. 全局设置 -->
    <settings>
    </settings>

    <!-- 2. 类型别名 -->
    <typeAliases>
    </typeAliases>

    <!-- 3. 类型处理器 -->
    <typeHandlers>
    </typeHandlers>
    <!-- 4. 对象工厂 -->
    <objectFactory>
  	</objectFactory>
    <!-- 5. 插件 -->
    <plugins>
        <plugin>
        </plugin>
  </plugins>
     	
    <!-- 5. 环境配置 -->
    <environments default="development">
        <environment id="production">
            <transactionManager/>
            <dataSource>
            </dataSource>
        </environment>
        <!-- ...environment -->
    </environments>

    <!-- 7. 数据库供应商ID -->
    <databaseIdProvider>
    </databaseIdProvider>

    <!-- 8. 映射器配置 -->
    <mappers>
    </mappers>

</configuration>
```

### 2.2 配置节点

#### 2.2.1 properties

<properties>是一个配置属性的元素，该元素的作用是读取外部文件的配置信息，在`property`中使用`${}`来引用配置文件中的值

> 在示例工程中对应内容（在`src/main/resources/db.dev.properties`中）
>
> ``` xml
> <!--=src/main/resources/db.dev.properties -->
> # mysql驱动,版本>=80添加cj
> mysql.driver=com.mysql.cj.jdbc.Driver
> # 数据库连接地址，
> mysql.url=jdbc:mysql://localhost:3306/d_mybiteslearn?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false
> # 数据库用户名
> mysql.username=root
> # 数据库密码
> mysql.password=root
> ```
>
> 在`src/main/resources/mybatis-config.xml`中
>
> ``` properties
>     <!-- 引入外部配置文件 -->
>     <properties resource="db.dev.properties"/>
>         <!--    默认环境为dev-->
>     <environments default="dev">
>         <environment id="dev">
>             <transactionManager type="JDBC"/>
>             <dataSource type="POOLED">
>                 <property name="driver" value="${mysql.driver}"/>
>                 <property name="url" value="${mysql.url}"/>
>                 <property name="username" value="${mysql.username}"/>
>                 <property name="password" value="${mysql.password}"/>
>             </dataSource>
>         </environment>
>     </environments>

#### 2.2.2 settings

用于配置一些参数

``` xml
<settings>
    <!-- 是否开启缓存 -->
    <setting name="cacheEnabled" value="true" />
    <!-- 是否开启延迟加载,如果开启,所有关联对象都会延迟加载 -->
    <setting name="lazyLoadingEnabled" value="true" />
    <!-- 是否开启关联对象属性的延迟加载,如果开启,对任意延迟属性的调用都
    会使用带有延迟加载属性的对象向完整加载,否则每种属性都按需加载 -->
    <setting name="aggressiveLazyLoading" value="true" />
    <!-- ... -->
</settings>
```

#### 2.2.3 typeAliases

核心配置文件若要引用一个POJO实体类，需要输入POJO实体类的全限定类名，而全限定类名比较冗长，如果直接输入，很容易拼写错误。这时可以使用<typeAliases>元素为POJO实体类设置一个简短的别名，再通过MyBatis的核心配置文件与映射文件相关联。

有如下几种方法配置：

方式一：在<typeAliases>元素下，使用多个<typeAlias>元素为每一个全限定类逐个配置别名

> ``` xml
> <typeAliases>
>     <typeAlias alias="Users" type="site.icefox.javaeelearn.Entity.UsersEntity"/>
>     <typeAlias alias="Student" type="site.icefox.javaeelearn.Entity.StudentsEntity"/>
> </typeAliases>
> ```

方式二：通过自动扫描包的形式自定义别名。

> 在示例工程中如下( In `src/main/resources/mybatis-config.xml`)
> ``` xml
>    <typeAliases>
>   <package name="site.icefox.javaeelearn.Entity"/>
>    </typeAliases>
>    ```

除了可以使用<typeAliases>元素为实体类自定义别名外，MyBatis框架还为许多常见的Java类型（如数值、字符串、日期和集合等）提供了相应的默认别名。例如别名_byte映射类型byte、_long映射类型long等，别名可以在MyBatis中直接使用，但由于别名不区分大小写，所以在使用时要注意重复定义的覆盖问题。

#### 2.2.4 environments

MyBatis可以配置多套运行环境，如开发环境、测试环境、生产环境等，我们可以灵活选择不同的配置，从而将SQL映射到不同运行环境的数据库中。不同的运行环境可以通过<environments>元素来配置，但不管增加几套运行环境，都必须要明确选择出当前要用的唯一的一个运行环境。

MyBatis的运行环境信息包括事务管理器和数据源。

在MyBatis的核心配置文件中，MyBatis通过<environments>元素定义一个运行环境。<environments>元素有两个子元素。

| 元素名称             | 用途                         |
| -------------------- | ---------------------------- |
| <transactionManager> | 用于配置运行环境的事务管理器 |
| <daraSource>         | 用于配置运行环境的数据源信息 |

##### 2.2.4.1 transcationManager

<transcationManager>元素可以配置两种类型的事务管理器

| 类型    | 说明                                                         |
| ------- | ------------------------------------------------------------ |
| JDBC    | 此配置直接使用JDBC的提交和回滚设置，它依赖于从数据源得到的连接来管理事务的作用域。 |
| MANAGED | 此配置不提交或回滚一个连接，而是让容器来管理事务的整个生命周期。默认情况下，它会关闭连接，但可以将<transcationManager>元素的closeConnection属性设置为false来阻止它默认的关闭行为 |

##### 2.2.4.2 daraSource

对于数据源的配置，MyBatis提供了UNPOOLED、POOLED和JNDI三种数据源类型。

* UNPOOLED
  表示数据源为无连接池类型。配置此数据源类型后，程序在每次被请求时会打开和关闭数据库连接。UNPOOLED适用于对性能要求不高的简单应用程。UNPOOLED类型的数据源需要配置5种属性。 

| **属性**                         | **说明**                                                     |
| -------------------------------- | ------------------------------------------------------------ |
| driver                           | JDBC驱动的Java类的完全限定名（并不是JDBC驱动中可能包含的数据源类） |
| url                              | 数据库的URL地址                                              |
| username                         | 登录数据库的用户名                                           |
| password                         | 登录数据库的密码                                             |
| defaultTransactionIsolationLevel | 默认的连接事务隔离级别                                       |

* POOLED
  表示数据源为连接池类型。POOLED数据源利用“池”的概念将JDBC连接对象组织起来，节省了在创建新的连接对象时需要初始化和认证的时间。POOLED数据源使得并发Web应用可以快速的响应请求，是当前比较流行的数据源配置类型。 

| **属性**                      | **说明**                                                     |
| ----------------------------- | ------------------------------------------------------------ |
| poolMaximumActiveConnections  | 在任意时间可以存在的活动连接数量，默认值：10。               |
| poolMaximumIdleConnections    | 任意时间可能存在的空闲连接数。                               |
| poolMaximumCheckoutTime       | 在被强制返回之前，池中连接被检出时间，默认值：20000 毫秒。   |
| poolTimeToWait                | 如果获取连接花费的时间较长，它会给连接池打印状态日志并重新尝试获取一个连接，默认值：20000毫秒。 |
| poolPingQuery                 | 发送到数据库的侦测查询，用来检验连接是否处在正常工作秩序中。默认是“NO PING QUERY SET”。 |
| poolPingEnabled               | 是否启用侦测查询。若开启，必须使用一个可执行的SQL语句设置poolPingQuery属性，默认值：false。 |
| poolPingConnectionsNotUsedFor | 配置poolPingQuery的使用频度。                                |

* JNDI
  是一种用于从应用服务器或容器中获取数据库连接的机制。JNDI 数据源通常用于在 Java EE 环境下配置数据源，方便在应用程序中进行数据库连接的管理和配置，而无需在 MyBatis 中手动配置连接信息。 

| **属性**        | **说明**                                                     |
| --------------- | ------------------------------------------------------------ |
| initial_context | 该属性主要用于在InitialContext中寻找上下文（即initialContext.lookup(initial_context)）。该属性为可选属性，在忽略时，data_source属性会直接从InitialContext中寻找。 |
| data_source     | 该属性表示引用数据源对象位置的上下文路径。如果提供了initial_context配置，那么程序会在其返回的上下文中进行查找；如果没有提供，则直接在InitialContext中查找。 |

> 在示例工程中(In `src/main/resources/mybatis-config.xml`)
>
> ``` xml
>  <!--    默认环境为dev-->
>  <environments default="dev">
>      <environment id="dev">
>          <!-- 设置使用JDBC事务管理 -->
>          <transactionManager type="JDBC"/>
>          <!-- 配置数据源 -->
>          <dataSource type="POOLED">
>              <property name="driver" value="${mysql.driver}"/>
>              <property name="url" value="${mysql.url}"/>
>              <property name="username" value="${mysql.username}"/>
>              <property name="password" value="${mysql.password}"/>
>          </dataSource>
>      </environment>
>  </environments>
> ```
>

#### 2.2.5 mappers

用于引入MyBatis映射文件。映射文件包含了POJO对象和数据表之间的映射信息，MyBatis通过核心配置文件中的<mappers>元素找到映射文件并解析其中的映射信息。

* `resource`：适用于类路径下的 XML 映射文件。
* `url`：适用于外部文件系统或网络路径的 XML 文件。
* `class`：适用于直接使用 Mapper 接口。
* `package`：适用于批量扫描 Mapper 接口，简化配置。

示例代码

``` xml
<mappers>
    <mapper resource="mappers/UserMapper.xml"/>
    <mapper url="file:///path/to/mappers/UserMapper.xml"/>
    <mapper class="com.example.mapper.UserMapper"/>
    <package name="com.example.mapper"/>
</mappers>
```

> 在示例工程中（In `src/main/resources/mybatis-config.xml`)
>
> ``` xml
>     <mappers>
>         <package name="site.icefox.javaeelearn.Mapper"/>
>     </mappers>
> ```

## 三 映射文件

### 3.1 namespace

* 用于区分不同的mapper，全局唯一。
* 绑定DAO接口，即面向接口编程。当namespace绑定某一接口之后，可以不用写该接口的实现类，MyBatis会通过接口的全限定名查找到对应的mapper配置来执行SQL语句，因此namespace的命名必须跟接口同名。

MyBatis通过<mapper>元素的namespace属性值和子元素的id联合区分不同的Mapper.xml文件。接口中的方法与映射文件中SQL语句id应一一对应。因此：**<mapper>元素的子元素的id可以相同**

### 3.2 常用节点

| **属性**  | **说明**                                                    |
| --------- | ----------------------------------------------------------- |
| mapper    | 映射文件的根元素，该元素只有一个namespace属性（命名空间）。 |
| cache     | 配置给定命名空间的缓存。                                    |
| cache-ref | 从其他命名空间引用缓存配置。                                |
| resultMap | 描述数据库结果集和对象的对应关系。                          |
| sql       | 可以重用的SQL块，也可以被其他语句使用。                     |
| insert    | 用于映射插入语句。                                          |
| delete    | 用于映射删除语句。                                          |
| update    | 用于映射更新语句。                                          |
| select    | 用于映射查询语句。                                          |

#### 3.2.1 select

`<select>`元素用来映射查询语句，它可以从数据库中查询数据并返回。

常用属性如下

| **属性**      | **说明**                                                     |
| ------------- | ------------------------------------------------------------ |
| id            | 表示命名空间中<select>元素的唯一标识，通过该标识可以调用这条查询语句。 |
| parameterType | 它是一个可选属性，用于指定SQL语句所需参数类的全限定名或者别名，其默认值是unset。 |
| resultType    | 用于指定执行这条SQL语句返回的全限定类名或别名。              |
| resultMap     | 表示外部resultMap的命名引用。resultMap和resultType不能同时使用。 |
| flushCache    | 用于指定是否需要MyBatis清空本地缓存和二级缓存。              |
| useCache      | 用于控制二级缓存的开启和关闭。                               |
| timeout       | 用于设置超时时间，单位为秒。                                 |
| fetchSize     | 获取记录的总条数设定，默认值是unset。                        |
| statementType | 用于设置MyBatis预处理类。                                    |
| resultSetType | 表示结果集的类型，它的默认值是unset。                        |

> 在示例工程中 (In `src/main/resources/site/icefox/javaeelearn/Mapper/UsersMapper.xml`)
>
> ``` xml
>     <!--select查询语句-->
>     <select id="getUserByUid" resultType="UsersEntity">
>         select * from d_mybiteslearn.t_users where uid = #{uid}
>     </select>
> ```
>
> 

#### 3.2.2 insert

* <insert>元素用于映射插入语句，在执行完<insert>元素中定义的SQL语句后，会返回插入记录的数量。

> 在示例工程中 (In `src/main/resources/site/icefox/javaeelearn/Mapper/UsersMapper.xml`)
>
> ```xml
>     <!-- 插入操作 -->
>     <insert id="addUser" parameterType="UsersEntity" keyProperty="uid" useGeneratedKeys="true">
>         insert into t_users (uid, uname, uage, usex) value (#uid, #uname, #uage, #usex)
>     </insert>
> ```

* 数据库获取主键值的方式

  * 使用支持主键自动增长的数据库获取主键值

    如果使用的数据库支持主键自动增长（如MySQL和SQL Server），那么可以通过keyProperty属性指定POJO类的某个属性接收主键返回值（通常会设置到id属性上），然后将useGeneratedKeys的属性值设置为true。

    ``` xml
     <!-- 插入操作 -->
     <insert id="addUser" parameterType="UsersEntity" keyProperty="uid" useGeneratedKeys="true">
         insert into t_users (uid, uname, uage, usex) value (#uid, #uname, #uage, #usex)
     </insert>
    ```

    

  * 使用不支持主键自动增长的数据库获取主键值

    使用MyBatis提供的<selectKey>元素来自定义主键。 在上述<selectKey>元素的属性中，order属性可以被设置为BEFORE或AFTER。如果设置为BEFORE，那么它会首先执行<selectKey>元素中的配置来设置主键，然后执行插入语句；如果设置为AFTER，那么它先执行插入语句，然后执行<selectKey>元素中的配置内容。

#### 3.2.3 update

<update>元素用于映射更新语句，它可以更新数据库中的数据。在执行完元素中定义的SQL语句后，会返回更新的记录数量。

> 在示例工程中(In `src/main/resources/site/icefox/javaeelearn/Mapper/UsersMapper.xml`)
>
> ``` xml
>     <!-- 修改操作 -->
>     <update id="updateUser" parameterType="UsersEntity">
>         update t_users
>         set uname= #{uname},
>             uage = #{uage},
>             usex = #{usex}
>         where uid = #{uid}
>     </update>
> ```

#### 3.2.4 delete

<delete>元素用于映射删除语句，在执行完<delete>元素中的SQL语句之后，会返回删除的记录数量。

> 在示例工程中(In `src/main/resources/site/icefox/javaeelearn/Mapper/UsersMapper.xml`)
>
> ``` xml
>        <!-- 删除操作 -->
>        <delete id="deleteUser" parameterType="Integer">
>            delete from t_users where uid=#{uid}
>        </delete>
>    ```

| **属性**      | **说明**                                                     |
| ------------- | ------------------------------------------------------------ |
| id            | 表示命名空间中<select>元素的唯一标识，通过该标识可以调用这条语句。 |
| parameterType | 它是一个可选属性，用于指定SQL语句所需参数类的全限定名或者别名，其默认值是unset。 |
| flushCache    | 用于指定是否需要MyBatis清空本地缓存和二级缓存。              |
| timeout       | 用于设置超时时间，单位为秒。                                 |
| statementType | 用于设置MyBatis预处理类。                                    |

#### 3.2.5 Sql

在一个映射文件中，通常需要定义多条SQL语句，这些SQL语句的组成可能有一部分是相同的（如多条select语句中都查询相同的id、username字段），如果每一个SQL语句都重写一遍相同的部分，势必会增加代码量。针对此问题，可以在映射文件中使用MyBatis所提供的<sql>元素，将这些SQL语句中相同的组成部分抽取出来，然后在需要的地方引用。

<sql>元素的作用是定义可重用的SQL代码片段，它可以被包含在其他语句中。<sql>元素可以被静态地（在加载参数时）参数化，<sql>元素不同的属性值通过包含的对象发生变化。 

``` xml
<!--定义要查询的表 -->
<sql id="someinclude">from <include refid="${include_target}" /></sql>
<!--定义查询列 -->
<sql id="userColumns">uid,uname,uage</sql>
<!--根据客户id查询客户信息 -->
<select id="getUserByUid" parameterType="Integer"  resultType="UsersEntity">
    select 
    <include refid="userColumns"/>
    <include refid="someinclude">
        <property name="include_target" value="users" />
        
    </include>
    where uid = #{uid}
</select> 
```



#### 3.2.6 resultMap

<resultMap>元素表示结果映射集，是MyBatis中最重要也是功能最强大的元素。<resultMap>元素主要作用是定义映射规则、更新级联以及定义类型转化器等。

默认情况下，MyBatis程序在运行时会自动将查询到的数据与需要返回的对象的属性进行匹配赋值（数据表中的列名与对象的属性名称完全一致才能匹配成功并赋值）。然而实际开发时，数据表中的列和需要返回的对象的属性可能不会完全一致，这种情况下MyBatis不会自动赋值，这时就需要使用<resultMap>元素进行结果集映射。

##### 3.2.6.1 测试代码

书写如下代码用于测试

`src/main/resources/site/icefox/javaeelearn/Mapper/StudentsMapper.xml`

``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace=绑定一个对应的Dao/Mapper接口-->
<mapper namespace="site.icefox.javaeelearn.Mapper.StudentsMapper">
    <resultMap type="StudentsEntity" id="studentMap">
        <id property="id" column="sid"/>
        <result property="name" column="sname"/>
        <result property="age" column="sage"/>
    </resultMap>
    <select id="findAllStudent" resultMap="studentMap">
        select *
        from t_students;
    </select>

</mapper>

```

`src/main/java/site/icefox/javaeelearn/Dao/StudentsDao.java`

``` java
package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.StudentsEntity;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;

public class StudentsDao {

    public static List<StudentsEntity> findAllStudents() {
        SqlSession sqlSession = DBConn.getSqlSession();
        return sqlSession.selectList("site.icefox.javaeelearn.Mapper.StudentsMapper.findAllStudent");
    }
}
```

`src/main/avajjava/site/icefox/javaeelearn/Entity/StudentsEntity.java`

``` java
package site.icefox.javaeelearn.Entity;

import lombok.Data;

@Data
public class StudentsEntity {
   private Integer id;
   private String name;
   private Integer age;
}

```

`src/main/java/site/icefox/javaeelearn/Mapper/StudentsMapper.java`

``` java
package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.StudentsEntity;

import java.util.List;

public interface StudentsMapper {
    List<StudentsEntity> findAllStudents();
}

```

`src/test/java/ResultMapTest.java`

``` java
import org.junit.Test;
import site.icefox.javaeelearn.Dao.StudentsDao;

public class ResultMapTest{
    @Test
    public void resTest() {
        System.out.println(StudentsDao.findAllStudents());
    }
}

```

##### 3.2.6.2 测试结果

``` cmd
[StudentsEntity(id=1, name=张三, age=25), StudentsEntity(id=2, name=李四, age=21), StudentsEntity(id=3, name=王五, age=20)]
```

