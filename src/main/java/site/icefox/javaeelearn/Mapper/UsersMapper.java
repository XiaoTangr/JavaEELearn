package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.UsersEntity;

import java.util.List;

public interface UsersMapper {
    UsersEntity getUserByUid(int uid);
    List<UsersEntity> getUsers();
    int addUser(UsersEntity user);
    int updateUser(UsersEntity user);
    int deleteUser(Integer uid);

}
