package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.UserEntity;

import java.util.List;

public interface UserMapper {
    UserEntity getUserByUid(int uid);
    List<UserEntity> getUsers();
    int addUser(UserEntity user);
    int updateUser(UserEntity user);
    int deleteUser(Integer uid);

}
