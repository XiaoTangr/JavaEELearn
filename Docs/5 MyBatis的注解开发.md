

## 一 前言

前面的章节介绍了MyBatis的基本用法、关联映射、动态SQL和缓存机制等知识，所有的配置都是基于XML文件完成的，但在实际开发中，大量的XML配置文件的编写是非常繁琐的，为此，MyBatis提供了更加简便的基于注解的配置方式。本章将对MyBatis的注解开发进行详细讲解。 

本章节较为简单，对应代码在文章末尾查看。

## 二 **基于注解的单表增删改查**

### 2.1 CUDR

| 注解    | 用途 |
| ------- | ---- |
| @Select | 查   |
| @Insert | 增   |
| @Update | 改   |
| @Delete | 删   |

### 2.2 @Param注解

@Param注解用于处理参数

### 三 基于注解的关联查询

> `<mappers>`元素引入XML文件顺序
>
> 由于mybatis-config.xml文件中的扫描方式是从上往下扫描，所以`<mappers>`元素下引入IdCardMapper和PersonMapper接口的位置，必须在引入IdCardMapper.xml和PersonMapper.xml文件位置前面，否则程序将会首先读取到引入的IdCardMapper.xml和PersonMapper.xml文件，程序将会报错。
>
> 或者使用包扫描方式引入。

### 3.1 一对一查询

在MyBatis中使用@One注解实现一对一关联查询

### 3.2 一对多查询

在MyBatis中使用@Many注解实现一对一关联查询

### 3.3 一对多查询

在MyBatis中实现多对多关联查询

## 四 代码

### 4.1 SQL

``` sql
USE d_mybiteslearn;

# select
CREATE TABLE tb_worker
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(32),
    age       INT,
    sex       VARCHAR(8),
    worker_id INT UNIQUE
);
INSERT INTO tb_worker(name, age, sex, worker_id)
VALUES ('张三', 32, '女', 1001),
       ('李四', 28, '男', 1002),
       ('王五', 35, '女', 1003),
       ('赵六', 40, '男', 1004);
```

### 4.2 `src/main/java/site/icefox/javaeelearn/Dao`

#### 4.2.1 `PersonDao.java`

``` java
package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.PersonEntity;
import site.icefox.javaeelearn.Entity.WorkerEntity;
import site.icefox.javaeelearn.Mapper.PersonMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class PersonDao {
    public static PersonEntity findPersonById(Integer id) {
        PersonEntity person = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            person = mapper.findPersonById(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }
    //    2.1 一对一查询
    public static PersonEntity selectPersonById(int id) {
        PersonEntity person = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            person = mapper.selectPersonById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }
}
```

#### 4.2.2 `UserDao.java`

``` java
package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.UserEntity;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UserMapper;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;

public class UserDao {
    //    2.2 一对多查询
    public static UserEntity getUsersByUid(int uid) {
        UserEntity result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.getUserByUid(uid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List<UserEntity> getAllUsers() {
        List<UserEntity> result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.getUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int addUser(UserEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.addUser(user);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int updateUser(UserEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.updateUser(user);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int deleteUser(Integer uid) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.deleteUser(uid);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

//    一对多查询
    public static UsersEntity selectUserById(int id) {

        UsersEntity result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.selectUsersById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;


    }
}

```

#### 4.2.3 `OrdersDao.java`

``` java

package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.OrdersEntity;
import site.icefox.javaeelearn.Mapper.OrdersMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class OrdersDao {
    public static OrdersEntity findOrdersWithPorduct(int id) {
        OrdersEntity result = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            OrdersMapper ordersMapper = session.getMapper(OrdersMapper.class);
            result = ordersMapper.findOrdersWithPorduct(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

//    5.3 多对多查询
    public static OrdersEntity selectOrdersById(int id) {
        OrdersEntity result = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            OrdersMapper ordersMapper = session.getMapper(OrdersMapper.class);
            result = ordersMapper.selectOrdersById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
```

### 4.3 `WorkerEntity.java`

```java
package site.icefox.javaeelearn.Entity;

import lombok.Data;

@Data
public class WorkerEntity {

    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private int worker_id;
}
```

### 4.4 `src/main/java/site/icefox/javaeelearn/Mapper`

#### 4.4.1 `IdCardMapper.java`

```java
package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.IdCardEntity;

public interface IdCardMapper {
    IdCardEntity findCodeById(Integer id);


    //    二 基于注解的关联查询
    //    2.1一对一查询
    @Select("select * from tb_idcard where id=#{id}")
    IdCardEntity selectIdCardById(int id);

}
```

#### 4.4.2 `OrdersMapper.java`

```java
package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.OrdersEntity;

import java.util.List;

public interface OrdersMapper {
    OrdersEntity findOrdersWithPorduct(int id);

    //    二 基于注解的关联查询
    //    2.2一对多查询
    @Select("select * from tb_orders where user_id=#{id} ")
    @Results({@Result(id = true, column = "id", property = "id"),
            @Result(column = "number", property = "number")})
    List<OrdersEntity> selectOrdersByUserId(int user_id);

    //   2.3 多对多
    @Select("select * from tb_orders where id=#{id} ")
    @Results({@Result(id = true, column = "id", property = "id"),
            @Result(column = "number", property = "number"),
            @Result(column = "id", property = "productList", many = @Many(select = "site.icefox.javaeelearn.Mapper.ProductMapper.selectProductByOrdersId"))})
    OrdersEntity selectOrdersById(int id);

}
```

#### 4.4.3 `PersonMapper`

```java
javapackage site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.PersonEntity;

public interface PersonMapper {
    PersonEntity findPersonById(int id);

    //    二 基于注解的关联查询
    //    2.1一对一查询
    @Select("select * from tb_person where id=#{id}")
    @Results({@Result(column = "card_id", property = "card",
            one = @One(select = "site.icefox.javaeelearn.Mapper.IdCardMapper.selectIdCardById"))})
    PersonEntity selectPersonById(int id);

}
```

#### 4.4.4 `ProductMapper.java`

```java
package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.ProductEntity;

import java.util.List;

public interface ProductMapper {
    ProductEntity findProductById(int id);

    //  2.3 多对多查询
    @Select("select * from tb_product where id in(select product_id from tb_ordersitem where orders_id= #{id})")
    List<ProductEntity> selectProductByOrdersId(int orders_id);

}
```

#### 4.4.5 `UsersMapper.java`

```java
package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.UsersEntity;

public interface UsersMapper {
    UsersEntity findUserWithOrders(Integer id);


//    一对多查询
    @Select("select * from tb_user where id=#{id} ")
    @Results({@Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "address", property = "address"),
            @Result(column = "id", property = "ordersList",
                    many = @Many(select = "site.icefox.javaeelearn.Mapper.OrdersMapper.selectOrdersByUserId"))})
    UsersEntity selectUsersById(int id);

}
```

#### 4.4.6 `WorkerMapper.java`

```java
package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.*;
import site.icefox.javaeelearn.Entity.WorkerEntity;

public interface WorkerMapper {
    //    一 基于注解的单表增删改查
    //   1.1 CURD
    @Select("select * from tb_worker where id = #{id}")
    WorkerEntity selectWorkerById(int id);

    @Insert("insert into tb_worker(name,age,sex,worker_id)"
            + "values(#{name},#{age},#{sex},#{worker_id})")
    int insertWorkerBySet(WorkerEntity worker);

    @Update("update tb_worker set name = #{name},age = #{age},worker_id = #{worker_id} " + "where id = #{id}")
    int updateWorkerBySet(WorkerEntity worker);

    @Delete("delete from tb_worker where id = #{id}")
    int deleteWorkerById(int id);

    //   1.2 Param注解
    @Select("select * from tb_worker where id = #{param01} and name = #{param02}")
    WorkerEntity selectWorkerByIdAndName(@Param("param01") int id, @Param("param02") String name);

}
```

### 4.5 `src/test/java/v5_AnnotationTest.java`

```java
import org.junit.Test;
import site.icefox.javaeelearn.Dao.OrdersDao;
import site.icefox.javaeelearn.Dao.PersonDao;
import site.icefox.javaeelearn.Dao.UserDao;
import site.icefox.javaeelearn.Dao.WorkerDao;
import site.icefox.javaeelearn.Entity.WorkerEntity;

public class v5_AnnotationTest {

    static WorkerEntity param = new WorkerEntity();

    static {
        param.setId(1);
        param.setName("Alice");
        param.setAge(25);
        param.setSex("female");
        param.setWorker_id(1005);
    }

    //   一 基于注解的单表增删改查
    //    1.1 CUDR
    @Test
    public void findWorkerByIdTest() {
        System.out.println(WorkerDao.selectWorkerById(param.getId()));
    }

    @Test
    public void insertWorkerBySetTest() {
        WorkerEntity temp = WorkerDao.selectWorkerById(param.getId());
        if (temp != null) {
            System.out.println("Duplicate entry  for key 'tb_worker.worker_id',please change the worker_id");
        } else {
            System.out.println(WorkerDao.insertWorkerBySet(param));
            System.out.println(WorkerDao.selectWorkerById(param.getId()));
        }
    }

    @Test
    public void updateWorkerBySetTest() {
        System.out.println(WorkerDao.updateWorkerBySet(param));
    }

    @Test
    public void deleteWorkerByIdTest() {
        if (WorkerDao.selectWorkerById(param.getId()) == null) {
            System.out.println("Worker not found, can't delete.");
        } else {
            System.out.println(WorkerDao.deleteWorkerById(param.getId()));
        }
    }

    //   1.2 Param注解测试
    @Test
    public void ParamTest() {
        System.out.println(WorkerDao.selectWorkerByIdAndName(1, "张三"));
    }


    //    二 基于注解的关联查询
    //    2.1一对一查询

    @Test
    public void selectPersonByIdTest() {
        System.out.println(PersonDao.selectPersonById(param.getId()));
    }

    //    2.2 一对多查询
    @Test
    public void selectWorkerAndPersonByIdTest() {
        System.out.println(UserDao.selectUserById(1));
    }

    //2.3 多对多查询
    @Test
    public void selectOrdersByIdTest() {
        OrdersDao.selectOrdersById(1);
    }
}
```