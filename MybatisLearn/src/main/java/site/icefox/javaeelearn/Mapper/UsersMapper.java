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
