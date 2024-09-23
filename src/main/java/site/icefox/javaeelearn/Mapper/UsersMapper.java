package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.UsersEntity;

public interface UsersMapper {
    UsersEntity findUserWithOrders(Integer id);
}
