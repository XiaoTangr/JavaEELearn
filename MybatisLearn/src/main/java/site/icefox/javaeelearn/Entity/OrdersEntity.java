package site.icefox.javaeelearn.Entity;

import lombok.Data;

import java.util.List;

@Data
public class OrdersEntity {
    private Integer id;                //订单id
    private String number;            //订单编号

    private List<ProductEntity> productList;

}