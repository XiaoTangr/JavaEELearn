package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class UsersDao {
    public static UsersEntity findUserWithOrders(Integer id) {
        UsersEntity result = null;

        try (SqlSession session = DBConn.getSqlSession()) {
            UsersMapper usersmapper = session.getMapper(UsersMapper.class);

            result = usersmapper.findUserWithOrders(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
