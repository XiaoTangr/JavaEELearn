## 一 前言

在上一章节，主要讲了Mybites中对数据库基本的CRUD，那么，本章节将重点介绍动态SQL的用法。

动态SQL是MyBatis的强大特性之一，MyBatis采用了功能强大的基于OGNL（Object Graph Navigation Language）的表达式来完成动态SQL。在MyBatis的映射文件中，开发人员可通过动态SQL元素灵活组装SQL语句，这在很大程度上避免了单一SQL语句的反复堆砌，提高了SQL语句的复用性。

动态SQL主要用到以下几个元素：

| **元素**                      | **说明**                                                  |
| ----------------------------- | --------------------------------------------------------- |
| `<if>`                          | 判断语句，用于单条件判断                                  |
| `<choose>`(`<when>`、`<otherwise>`) | 相当于Java中的switch...case...default语句，用于多条件判断 |
| `<where>`                       | 简化SQL语句中where的条件判断                              |
| `<trim>`                        | 可以灵活地去除多余的关键字                                |
| `<set>`                         | 用于SQL语句的动态更新                                     |
| `<foreach>`                     | 循环语句，常用于in语句等列举条件中                        |

## 二 条件查询

### 2.1 `<if>`元素

`<if>`元素是最常用的判断元素，它类似于Java中的if语句，主要用于实现某些简单的条件判断。在实际应用中，我们可能会通过某个条件查询某个数据。

### 2.2 `<choose>`、`<when>`、`<otherwise>`元素

在使用`<if>`元素时，只要test属性中的表达式为true，就会执行元素中的条件语句，但是在实际应用中，有时只需要从多个选项中选择一个去执行。MyBatis提供了`<choose>`、`<when>`、`<otherwise>`元素进行处理，这三个元素往往组合在一起使用，作用相当于Java语言中的if…else if…else。

### 2.3  `<where>`、`<trim>`元素

在映射文件中，编写的SQL后面加入了“where 1=1”的条件的话，既保证了where后面的条件成立，又避免了where后面第一个词是and或者or之类的关键字，这在运行时会报SQL语法错误，针对这种情况，可以使用MyBatis提供的`<where>`元素和`<trim>`元素进行处理。

#### 2.3.1 `<where>`元素

`<where>`元素会自动判断由组合条件拼装的SQL语句，只有`<where>`元素内的某一个或多个条件成立时，才会在拼接SQL中加入where关键字，否则将不会添加；即使where之后的内容有多余的“AND”或“OR”，`<where>`元素也会自动将他们去除。

#### 2.3.2 `<trim>`元素

`<trim>`元素用于删除多余的关键字，它可以直接实现`<where>`元素的功能。`<trim>`元素包含4个属性。

| **属性**        | **说明**                        |
| --------------- | ------------------------------- |
| prefix          | 指定给SQL语句增加的前缀         |
| prefixOverrides | 指定SQL语句中要去掉的前缀字符串 |
| suffix          | 指定给SQL语句增加的后缀         |
| suffixOverrides | 指定SQL语句中要去掉的后缀字符串 |

`<trim>`元素的作用是去除一些多余的前缀字符串，它的prefix属性代表的是语句的前缀（where），而prefixOverrides属性代表的是需要去除的前缀字符串（SQL中的“AND”或“OR”）。 

## 三 更新操作 `<set>`元素

 **`<set>`元素使用场景**

MyBatis提供了`<set>`元素。`<set>`元素主要用于更新操作，它可以在动态SQL语句前输出一个SET关键字，并将SQL语句中最后一个多余的逗号去除。`<set>`元素与`<if>`元素结合可以只更新需要更新的字段。 

> 在映射文件中使用`<set>`元素和`<if>`元素组合进行update语句动态SQL组装时，如果`<set>`元素内包含的内容都为空，则会出现SQL语法错误。因此，在使用`<set>`元素进行字段信息更新时，要确保传入的更新字段不能都为空。
>
> 除了使用`<set>`元素外，还可以通过`<trim>`元素来实现更新操作。其中， `<trim>`元素的prefix属性指定要添加的`<trim>`元素所包含内容的前缀为set，suffixOverrides属性指定去除的`<trim>`元素所包含内容的后缀为逗号 。

## 四 复杂查询操作 `<foreach>`元素

`<foreach>`元素中的属性

| **属性**   | **说明**                                                     |
| ---------- | ------------------------------------------------------------ |
|item       | 表示集合中每一个元素进行迭代时的别名。该属性为必选。         |
| index      | 在List和数组中，index是元素的序号，在Map中，index是元素的key。该属性可选。 |
| open       | 表示foreach语句代码的开始符号，一般和close=“)”合用。常用在in条件语句中。该属性可选。 |
| separator  | 表示元素之间的分隔符，例如，在条件语句中，separator=“,”会自动在元素中间用“,”隔开，避免手动输入逗号导致SQL错误，错误示例如in(1,2,)。该属性可选。 |
| close      | 表示foreach语句代码的关闭符号，一般和open="("合用。常用在in条件语句中。该属性可选。 |
| collection | 用于指定遍历参数的类型。注意，该属性必须指定，不同情况下，该属性的值是不一样的。 |

1. `<collection>`属性的取值

   在遍历参数时，`<collection>`属性的值是必须指定的。不同情况下，该属性的取值也是不一样的，主要有以下三种情况。

   * List类型：若入参为单参数且参数类型是一个List，collection属性值为list。
   * 数组类型：若入参为单参数且参数类型是一个数组，collection属性值为array。
   * Map类型：若传入参数为多参数，就需要把参数封装为一个Map进行处理，collection属性值为Map。若传入参数为多参数，就需要把参数封装为一个Map进行处理，collection属性值为Map。

## 五 相关代码

### 5.1 SQL

``` sql
USE d_mybiteslearn;
CREATE TABLE t_customers (
                            id int(32) PRIMARY KEY AUTO_INCREMENT,
                            username varchar(50),
                            jobs varchar(50),
                            phone varchar(16));
INSERT INTO t_customers VALUES ('10001', '张三', 'teacher', '18320001111');
INSERT INTO t_customers VALUES ('10002', '王五', 'teacher', '18320002222');
INSERT INTO t_customers VALUES ('10003', '刘九', 'worker', '18320003333');

```

### 5.2 `src/main/java/site/icefox/javaeelearn/Dao/CustomersDao.java`

``` java
package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.CustomersEntity;
//import site.icefox.javaeelearn.Mapper.CustomersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDao {
    public static List<CustomersEntity> findCustomerByNameAndJobs(CustomersEntity param) {
        List<CustomersEntity> result = null;
        SqlSession session = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper.findCustomerByNameAndJobs", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.close();
        }
        return result;
    }

    public static List<CustomersEntity> findCustomerByNameOrJobs(CustomersEntity param) {
        SqlSession session = null;
        List<CustomersEntity> result = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." + "findCustomerByNameOrJobs", param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public static Integer updateCustomerBySet(CustomersEntity param) {
        int result = 0;

        try (SqlSession session = DBConn.getSqlSession()) {
            result = session.update("site.icefox.javaeelearn.Mapper.CustomersMapper." + "updateCustomerBySet", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static List<CustomersEntity> findByArrayTest(Integer[] roleIds) {
        List<CustomersEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByArray", roleIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomersEntity> findByList(List<Integer> ids) {

        List<CustomersEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByList", ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomersEntity> findByMap(Map<String, Object> conditionMap) {
        List<CustomersEntity> customers = null;

        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByMap", conditionMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

}

```

### 5.3 `src/main/java/site/icefox/javaeelearn/Entity/CustomersEntity.java`

``` java
package site.icefox.javaeelearn.Entity;

import lombok.Data;

@Data
public class CustomersEntity {
    private Integer id;
    private String username;
    private String jobs;
    private String phone;
}
```

### 5.4 `src/test/java/SqlPreTest.java`

``` java
import org.junit.Test;
import site.icefox.javaeelearn.Dao.CustomersDao;
import site.icefox.javaeelearn.Entity.CustomersEntity;

import java.util.*;

public class SqlPreTest {
    @Test
    public void findCustomerByNameAndJobsTest() {
        CustomersEntity param = new CustomersEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomersDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findCustomerByNameOrJobsTest() {
        CustomersEntity param = new CustomersEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomersDao.findCustomerByNameOrJobs(param));
    }

    @Test
    public void updateCustomerBySetTest() {
        CustomersEntity param = new CustomersEntity();
        param.setId(10001); // 假设 uid=1
        param.setUsername("eee");
        param.setJobs("teacher");
        param.setPhone("1008611");
        System.out.println(CustomersDao.updateCustomerBySet(param));
        System.out.println(CustomersDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findByArrayTest() {
        Integer[] roleIds = {10001, 10002};
        System.out.println(CustomersDao.findByArrayTest(roleIds));
    }

    @Test
    public void findByListTest() {
        List<Integer> ids = Arrays.asList(10001, 10002);
        System.out.println(CustomersDao.findByList(ids));
    }

    @Test
    public void deleteByIdsTest() {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(10001);
        ids.add(10002);
        ids.add(10003);
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("id", ids);
        conditionMap.put("jobs", "teacher");

        System.out.println(CustomersDao.findByMap(conditionMap));
    }
}
```



### 5.5 `src/main/java/site/icefox/javaeelearn/Mapper/CustomersMapper.java`

``` java
package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.CustomersEntity;

import java.util.List;
import java.util.Map;

public interface CustomersMapper {
    List<CustomersEntity> findCustomerByNameAndJobs(CustomersEntity param);

    List<CustomersEntity> findCustomerByNameOrJobs(CustomersEntity param);

    Integer updateCustomerBySet(CustomersEntity param);

    List<CustomersEntity> findByArrayTest(Integer[] roleIds);

    List<CustomersEntity> findByList(List<Integer> ids);

    List<CustomersEntity> findByMap(Map<String, Object> conditionMap);
}
```

### 5.5 `src/main/resources/site/icefox/javaeelearn/Mapper/CustomersMapper.xml`

``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace=绑定一个对应的Dao/Mapper接口-->
<mapper namespace="site.icefox.javaeelearn.Mapper.CustomersMapper">

    <!--    条件查询 -->
    <select id="findCustomerByNameAndJobs" parameterType="CustomersEntity" resultType="CustomersEntity">
        <!--        使用if的示例-->
        <!--        select * from t_customers where 1 = 1-->
        <!--        <if test="username != null and username !=''">-->
        <!--            and username like concat('%',#{username}, '%')-->
        <!--        </if>-->
        <!--        <if test=" jobs != null and jobs != ''">-->
        <!--            and jobs= #{jobs}-->
        <!--        </if>-->


        <!--        使用<where>规避where 1= 1-->
        <!--        select * from t_customers-->
        <!--        <where>-->
        <!--            <if test="username !=null and username !=''">-->
        <!--                and username like concat('%',#{username}, '%')-->
        <!--            </if>-->
        <!--            <if test="jobs !=null and jobs !=''">-->
        <!--                and jobs= #{jobs}-->
        <!--            </if>-->
        <!--        </where>-->

        <!--        使用 trim 处理 -->
        select * from t_customers
        <trim prefix="where" prefixOverrides="and">
            <if test="username !=null and username !=''">
                and username like concat('%',#{username}, '%')
            </if>
            <if test="jobs !=null and jobs !=''">
                and jobs= #{jobs}
            </if>
        </trim>

    </select>
    <!--    where元素-->
    <select id="findCustomerByNameOrJobs" parameterType="CustomersEntity" resultType="CustomersEntity">
        select * from t_customers where 1 = 1
        <choose>
            <when test="username !=null and username !=''">
                and username like concat('%',#{username}, '%')
            </when>
            <when test="jobs !=null and jobs !=''">
                and jobs= #{jobs}
            </when>
            <otherwise>and phone is not null</otherwise>
        </choose>
    </select>

    <!--    更新操作 set元素-->
    <update id="updateCustomerBySet" parameterType="CustomersEntity">
        update t_customers
        <set>
            <if test="username != null and username !=''">
                username=#{username},
            </if>
            <if test="jobs !=null and jobs !=''">jobs=#{jobs},</if>
            <if test="phone !=null and phone !=''">phone=#{phone},</if>
        </set>
        where id=#{id}
    </update>

    <!-- 复杂查询 元素迭代数组-->
    <select id="findByArray" parameterType="java.util.Arrays" resultType="CustomersEntity">
        select * from t_customers where id in
        <foreach item="id" index="index" collection="array"
                 open="(" separator="," close=")">#{id}
        </foreach>
    </select>

    <!--  复杂查询 元素迭代List -->
    <select id="findByList" parameterType="java.util.Arrays"
            resultType="CustomersEntity">
        select * from t_customers where id in
        <foreach item="id" index="index" collection="list"
                 open="(" separator="," close=")">
            #{id}
        </foreach>
    </select>

    <!-- 复杂查询 元素迭代Map  -->
    <select id="findByMap" parameterType="java.util.Map" resultType="CustomersEntity">
        select * from t_customers where jobs=#{jobs} and id in
        <foreach
                item="roleMap"
                index="index"
                collection="id"
                open="(" separator="," close=")">
            #{roleMap}
        </foreach>
    </select>

</mapper>
```

