package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.IdCardEntity;

public interface IdCardMapper {
    IdCardEntity findCodeById(Integer id);


    //    二 基于注解的关联查询
    //    2.1一对一查询
    @Select("select * from tb_idcard where id=#{id}")
    IdCardEntity selectIdCardById(int id);

}
