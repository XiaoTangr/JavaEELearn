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