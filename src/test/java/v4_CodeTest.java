import org.junit.Test;
import site.icefox.javaeelearn.Dao.OrdersDao;
import site.icefox.javaeelearn.Dao.PersonDao;
import site.icefox.javaeelearn.Dao.UserDao;


public class v4_CodeTest {
    //   2 一对一查询
    @Test
    public void findPersonByIdTest() {
        System.out.println(PersonDao.findPersonById(1));
    }

    //   3 一对多
    @Test
    public void findUserWithOrdersTest() {
        System.out.println(UserDao.findUserWithOrders(1));
    }

    //    4 多对多
    @Test
    public void findOrdersTest() {
        System.out.println(OrdersDao.findOrdersWithPorduct(1));
    }
}
