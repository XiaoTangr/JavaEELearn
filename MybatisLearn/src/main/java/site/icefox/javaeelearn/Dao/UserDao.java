package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.UserEntity;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UserMapper;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;

public class UserDao {
    //    2.2 一对多查询
    public static UserEntity getUsersByUid(int uid) {
        UserEntity result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.getUserByUid(uid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List<UserEntity> getAllUsers() {
        List<UserEntity> result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.getUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int addUser(UserEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.addUser(user);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int updateUser(UserEntity user) {
        int result = -1;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
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
            UserMapper usersmapper = sqlSession.getMapper(UserMapper.class);
            result = usersmapper.deleteUser(uid);  // 获取受影响的行数
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

//    一对多查询
    public static UsersEntity selectUserById(int id) {

        UsersEntity result = null;
        try (SqlSession sqlSession = DBConn.getSqlSession()) {
            UsersMapper usersmapper = sqlSession.getMapper(UsersMapper.class);
            result = usersmapper.selectUsersById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;


    }
}
