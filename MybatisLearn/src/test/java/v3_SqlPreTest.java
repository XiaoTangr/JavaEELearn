import org.junit.Test;
import site.icefox.javaeelearn.Dao.CustomerDao;
import site.icefox.javaeelearn.Entity.CustomerEntity;

import java.util.*;

public class v3_SqlPreTest {
    @Test
    public void findCustomerByNameAndJobsTest() {
        CustomerEntity param = new CustomerEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomerDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findCustomerByNameOrJobsTest() {
        CustomerEntity param = new CustomerEntity();
        param.setUsername("张三");
        param.setJobs("teacher");
        System.out.println(CustomerDao.findCustomerByNameOrJobs(param));
    }

    @Test
    public void updateCustomerBySetTest() {
        CustomerEntity param = new CustomerEntity();
        param.setId(10001); // 假设 uid=1
        param.setUsername("eee");
        param.setJobs("teacher");
        param.setPhone("1008611");
        System.out.println(CustomerDao.updateCustomerBySet(param));
        System.out.println(CustomerDao.findCustomerByNameAndJobs(param));
    }

    @Test
    public void findByArrayTest() {
        Integer[] roleIds = {10001, 10002};
        System.out.println(CustomerDao.findByArrayTest(roleIds));
    }

    @Test
    public void findByListTest() {
        List<Integer> ids = Arrays.asList(10001, 10002);
        System.out.println(CustomerDao.findByList(ids));
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

        System.out.println(CustomerDao.findByMap(conditionMap));
    }
}
