package site.icefox.javaeelearn.Dao;

import org.apache.ibatis.session.SqlSession;
import site.icefox.javaeelearn.Entity.OrdersEntity;
import site.icefox.javaeelearn.Mapper.OrdersMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class OrdersDao {
    public static OrdersEntity findOrdersWithPorduct(int id) {
        OrdersEntity result = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            OrdersMapper ordersMapper = session.getMapper(OrdersMapper.class);
            result = ordersMapper.findOrdersWithPorduct(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

//    5.3 多对多查询
    public static OrdersEntity selectOrdersById(int id) {
        OrdersEntity result = null;
        try (SqlSession session = DBConn.getSqlSession()) {
            OrdersMapper ordersMapper = session.getMapper(OrdersMapper.class);
            result = ordersMapper.selectOrdersById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
