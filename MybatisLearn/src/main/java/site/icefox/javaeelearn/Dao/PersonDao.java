package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.PersonEntity;
import site.icefox.javaeelearn.Entity.WorkerEntity;
import site.icefox.javaeelearn.Mapper.PersonMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class PersonDao {
    public static PersonEntity findPersonById(Integer id) {
        PersonEntity person = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            person = mapper.findPersonById(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }
    //    2.1 一对一查询
    public static PersonEntity selectPersonById(int id) {
        PersonEntity person = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            PersonMapper mapper = session.getMapper(PersonMapper.class);
            person = mapper.selectPersonById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }


}
