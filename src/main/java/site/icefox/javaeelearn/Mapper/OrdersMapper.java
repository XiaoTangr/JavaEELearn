package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.OrdersEntity;

public interface OrdersMapper {
    OrdersEntity findOrdersWithPorduct(int id);
}
