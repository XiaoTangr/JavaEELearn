package site.icefox.javaeelearn.Entity;

import lombok.Data;

import java.util.List;

@Data
public class UsersEntity {
    private Integer id;                         // 用户编号
    private String username;              // 用户姓名
    private String address;                 // 用户地址
    private List<OrdersEntity> ordersList;  // 用户关联的订单

}