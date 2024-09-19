package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;
import java.util.logging.Logger;

public class UsersDao {
    static Logger logger = Logger.getLogger(UsersDao.class.getName());

    public static UsersEntity getUsersByUid(int uid) {
        UsersEntity result = null;

        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.getUserByUid(uid);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return result;
    }

    public static List<UsersEntity> getAllUsers() {
        List<UsersEntity> result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.getUsers();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return result;
    }
}
