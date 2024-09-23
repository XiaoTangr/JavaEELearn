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
