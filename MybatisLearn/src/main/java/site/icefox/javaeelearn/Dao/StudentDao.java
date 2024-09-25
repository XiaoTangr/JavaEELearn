package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.StudentEntity;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;

public class StudentDao {

    public static List<StudentEntity> findAllStudents() {
        SqlSession sqlSession = DBConn.getSqlSession();
        return sqlSession.selectList("site.icefox.javaeelearn.Mapper.StudentMapper.findAllStudent");
    }
}
