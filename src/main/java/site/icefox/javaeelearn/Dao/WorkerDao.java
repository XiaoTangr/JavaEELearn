package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.WorkerEntity;
import site.icefox.javaeelearn.Mapper.WorkerMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class WorkerDao {

    private static WorkerMapper wokerMapper;
    private static SqlSession sqlSession;


    public static WorkerEntity selectWorkerById(int id) {
        WorkerEntity result = null;
        try {
            sqlSession = DBConn.getSqlSession();
            wokerMapper = sqlSession.getMapper(WorkerMapper.class);
            result = wokerMapper.selectWorkerById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int insertWorkerBySet(WorkerEntity worker) {
        int result = 0;
        try {
            sqlSession = DBConn.getSqlSession();
            wokerMapper = sqlSession.getMapper(WorkerMapper.class);
            result = wokerMapper.insertWorkerBySet(worker);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int updateWorkerBySet(WorkerEntity worker) {
        int result = 0;
        try {
            sqlSession = DBConn.getSqlSession();
            wokerMapper = sqlSession.getMapper(WorkerMapper.class);
            result = wokerMapper.updateWorkerBySet(worker);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int deleteWorkerById(int id) {
        int result = 0;
        try {
            sqlSession = DBConn.getSqlSession();
            wokerMapper = sqlSession.getMapper(WorkerMapper.class);
            result = wokerMapper.deleteWorkerById(id);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static WorkerEntity selectWorkerByIdAndName(int id, String name) {
        WorkerEntity result = null;
        try {
            sqlSession = DBConn.getSqlSession();
            wokerMapper = sqlSession.getMapper(WorkerMapper.class);
            result = wokerMapper.selectWorkerByIdAndName(id, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;

    }
}
