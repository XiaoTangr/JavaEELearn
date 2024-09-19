package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;
import java.util.logging.Logger;

public class UsersDao {

    public static UsersEntity getUsersByUid(int uid) {
        UsersEntity result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.getUserByUid(uid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List<UsersEntity> getAllUsers() {
        List<UsersEntity> result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.getUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int addUser(UsersEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.addUser(user);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int updateUser(UsersEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.updateUser(user);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int deleteUser(Integer uid) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.deleteUser(uid);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
