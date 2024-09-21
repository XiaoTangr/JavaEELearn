import org.junit.Test;
import site.icefox.javaeelearn.Dao.CustomersDao;
import site.icefox.javaeelearn.Entity.CustomersEntity;

import java.util.*;

public class SqlPreTest {
    @Test
    public void findCustomerByNameAndJobsTest() {
        CustomersEntity param = new CustomersEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomersDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findCustomerByNameOrJobsTest() {
        CustomersEntity param = new CustomersEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomersDao.findCustomerByNameOrJobs(param));
    }

    @Test
    public void updateCustomerBySetTest() {
        CustomersEntity param = new CustomersEntity();
        param.setId(10001); // 假设 uid=1
        param.setUsername("eee");
        param.setJobs("teacher");
        param.setPhone("1008611");
        System.out.println(CustomersDao.updateCustomerBySet(param));
        System.out.println(CustomersDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findByArrayTest() {
        Integer[] roleIds = {10001, 10002};
        System.out.println(CustomersDao.findByArrayTest(roleIds));
    }

    @Test
    public void findByListTest() {
        List<Integer> ids = Arrays.asList(10001, 10002);
        System.out.println(CustomersDao.findByList(ids));
    }

    @Test
    public void deleteByIdsTest() {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(10001);
        ids.add(10002);
        ids.add(10003);
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("id", ids);
        conditionMap.put("jobs", "teacher");

        System.out.println(CustomersDao.findByMap(conditionMap));
    }
}
