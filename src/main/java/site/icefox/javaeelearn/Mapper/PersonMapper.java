package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.PersonEntity;

public interface PersonMapper {
    PersonEntity findPersonById(int id);

    //    二 基于注解的关联查询
    //    2.1一对一查询
    @Select("select * from tb_person where id=#{id}")
    @Results({@Result(column = "card_id", property = "card",
            one = @One(select = "site.icefox.javaeelearn.Mapper.IdCardMapper.selectIdCardById"))})
    PersonEntity selectPersonById(int id);

}
