package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.CustomersEntity;

import java.util.List;
import java.util.Map;

public interface CustomersMapper {
    List<CustomersEntity> findCustomerByNameAndJobs(CustomersEntity param);

    List<CustomersEntity> findCustomerByNameOrJobs(CustomersEntity param);

    Integer updateCustomerBySet(CustomersEntity param);

    List<CustomersEntity> findByArrayTest(Integer[] roleIds);

    List<CustomersEntity> findByList(List<Integer> ids);

    List<CustomersEntity> findByMap(Map<String, Object> conditionMap);
}
