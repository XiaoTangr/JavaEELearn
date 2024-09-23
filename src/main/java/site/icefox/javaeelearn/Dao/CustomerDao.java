package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.CustomerEntity;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.List;
import java.util.Map;

public class CustomerDao {
    public static List<CustomerEntity> findCustomerByNameAndJobs(CustomerEntity param) {
        List<CustomerEntity> result = null;
        SqlSession session = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomerMapper.findCustomerByNameAndJobs", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.close();
        }
        return result;
    }

    public static List<CustomerEntity> findCustomerByNameOrJobs(CustomerEntity param) {
        SqlSession session = null;
        List<CustomerEntity> result = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomerMapper." + "findCustomerByNameOrJobs", param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public static Integer updateCustomerBySet(CustomerEntity param) {
        int result = 0;

        try (SqlSession session = DBConn.getSqlSession()) {
            result = session.update("site.icefox.javaeelearn.Mapper.CustomerMapper." + "updateCustomerBySet", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static List<CustomerEntity> findByArrayTest(Integer[] roleIds) {
        List<CustomerEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomerMapper." +
                    "findByArray", roleIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomerEntity> findByList(List<Integer> ids) {

        List<CustomerEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomerMapper." +
                    "findByList", ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomerEntity> findByMap(Map<String, Object> conditionMap) {
        List<CustomerEntity> customers = null;

        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomerMapper." +
                    "findByMap", conditionMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

}
