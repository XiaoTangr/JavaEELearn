package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.StudentsEntity;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;

public class StudentsDao {

    public static List<StudentsEntity> findAllStudents() {
        SqlSession sqlSession = DBConn.getSqlSession();
        return sqlSession.selectList("site.icefox.javaeelearn.Mapper.StudentsMapper.findAllStudent");
    }
}
