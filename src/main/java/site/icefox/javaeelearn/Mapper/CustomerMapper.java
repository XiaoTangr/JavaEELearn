package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.CustomerEntity;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    List<CustomerEntity> findCustomerByNameAndJobs(CustomerEntity param);

    List<CustomerEntity> findCustomerByNameOrJobs(CustomerEntity param);

    Integer updateCustomerBySet(CustomerEntity param);

    List<CustomerEntity> findByArrayTest(Integer[] roleIds);

    List<CustomerEntity> findByList(List<Integer> ids);

    List<CustomerEntity> findByMap(Map<String, Object> conditionMap);
}
