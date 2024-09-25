package site.icefox.javaeelearn.Mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import site.icefox.javaeelearn.Entity.UserEntity;
import site.icefox.javaeelearn.Entity.UsersEntity;

import java.util.List;

public interface UserMapper {
    UserEntity getUserByUid(int uid);

    List<UserEntity> getUsers();

    int addUser(UserEntity user);

    int updateUser(UserEntity user);

    int deleteUser(Integer uid);


}
