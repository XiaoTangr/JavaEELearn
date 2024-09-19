import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import site.icefox.javaeelearn.Dao.UsersDao;
import site.icefox.javaeelearn.Entity.UsersEntity;
import site.icefox.javaeelearn.Mapper.UsersMapper;
import site.icefox.javaeelearn.Util.DBConn;

public class FirstProjectTest {

    @Test
    public void queryOneUserByUidTest() {
        System.out.println(UsersDao.getUsersByUid(10001));
    }

    @Test
    public void queryUserListTest() {
        System.out.println(UsersDao.getAllUsers());
    }
}
