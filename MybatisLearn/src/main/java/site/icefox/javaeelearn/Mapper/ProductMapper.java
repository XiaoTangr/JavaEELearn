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
