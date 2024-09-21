package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.CustomersEntity;
//import site.icefox.javaeelearn.Mapper.CustomersMapper;
import site.icefox.javaeelearn.Util.DBConn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDao {
    public static List<CustomersEntity> findCustomerByNameAndJobs(CustomersEntity param) {
        List<CustomersEntity> result = null;
        SqlSession session = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper.findCustomerByNameAndJobs", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.close();
        }
        return result;
    }

    public static List<CustomersEntity> findCustomerByNameOrJobs(CustomersEntity param) {
        SqlSession session = null;
        List<CustomersEntity> result = null;
        try {
            session = DBConn.getSqlSession();
            result = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." + "findCustomerByNameOrJobs", param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public static Integer updateCustomerBySet(CustomersEntity param) {
        int result = 0;

        try (SqlSession session = DBConn.getSqlSession()) {
            result = session.update("site.icefox.javaeelearn.Mapper.CustomersMapper." + "updateCustomerBySet", param);
            session.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static List<CustomersEntity> findByArrayTest(Integer[] roleIds) {
        List<CustomersEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByArray", roleIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomersEntity> findByList(List<Integer> ids) {

        List<CustomersEntity> customers = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByList", ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public static List<CustomersEntity> findByMap(Map<String, Object> conditionMap) {
        List<CustomersEntity> customers = null;

        try (SqlSession session = DBConn.getSqlSession()) {
            customers = session.selectList("site.icefox.javaeelearn.Mapper.CustomersMapper." +
                    "findByMap", conditionMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

}
