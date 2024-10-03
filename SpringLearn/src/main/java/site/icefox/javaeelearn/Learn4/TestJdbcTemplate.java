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