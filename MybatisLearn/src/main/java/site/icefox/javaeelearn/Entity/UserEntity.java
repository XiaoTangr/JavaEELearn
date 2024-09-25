package site.icefox.javaeelearn.Entity;

import lombok.Data;

// Lombok 是一个 Java 库，它通过使用注解来帮助消除 Java  boilerplate 代码。
// 你可以在实体类上使用@Data注解，就不需要自己写getter 、 setter 以及 toString 方法了
@Data
public class UserEntity {
    private Integer uid;
    private String uname;
    private Integer uage;
    private Integer usex;


    //  getter 、 setter 以及 toString 方法
    //  如果不使用@Data注解，请取消注释下面的代码
//    public Integer uid() {
//        return uid;
//    }
//
//    public void setUid(Integer uid) {
//        this.uid = uid;
//    }
//
//    public String uname() {
//        return uname;
//    }
//
//    public void setUname(String uname) {
//        this.uname = uname;
//    }
//
//    public Integer uage() {
//        return uage;
//    }
//
//    public void setUage(Integer uage) {
//        this.uage = uage;
//    }
//
//    public Integer usex() {
//        return usex;
//    }
//
//    public void setUsex(Integer usex) {
//        this.usex = usex;
//    }
//
//    @Override
//    public String toString() {
//        return "UsersEntity{" +
//                "uid=" + uid +
//                ", uname='" + uname + '\'' +
//                ", uage=" + uage +
//                ", usex=" + usex +
//                '}';
//    }
}
