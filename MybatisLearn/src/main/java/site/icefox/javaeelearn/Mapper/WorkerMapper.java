package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.*;
import site.icefox.javaeelearn.Entity.WorkerEntity;

public interface WorkerMapper {
    //    一 基于注解的单表增删改查
    //   1.1 CURD
    @Select("select * from tb_worker where id = #{id}")
    WorkerEntity selectWorkerById(int id);

    @Insert("insert into tb_worker(name,age,sex,worker_id)"
            + "values(#{name},#{age},#{sex},#{worker_id})")
    int insertWorkerBySet(WorkerEntity worker);

    @Update("update tb_worker set name = #{name},age = #{age},worker_id = #{worker_id} " + "where id = #{id}")
    int updateWorkerBySet(WorkerEntity worker);

    @Delete("delete from tb_worker where id = #{id}")
    int deleteWorkerById(int id);

    //   1.2 Param注解
    @Select("select * from tb_worker where id = #{param01} and name = #{param02}")
    WorkerEntity selectWorkerByIdAndName(@Param("param01") int id, @Param("param02") String name);

}
