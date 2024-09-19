import org.junit.Test;
import site.icefox.javaeelearn.Dao.UsersDao;
import site.icefox.javaeelearn.Entity.UsersEntity;


public class FirstProjectTest {
    static UsersEntity user;
    static {
        user = new UsersEntity();
        user.setUid(1099);
        user.setUname("testUser");
        user.setUage(99);
        user.setUsex(1);
    }
    @Test
    public void queryOneUserByUidTest() {
        System.out.println(UsersDao.getUsersByUid(user.getUid()));
    }

    @Test
    public void queryUserListTest() {
        System.out.println(UsersDao.getAllUsers());
    }

    @Test
    public void addUserTest() {
        System.out.println(UsersDao.addUser(user));
    }
    @Test
    public void updateUserTest() {
        user.setUname("updateUser");
        System.out.println(UsersDao.updateUser(user));
    }
    @Test
    public void deleteUserTest() {
        System.out.println(UsersDao.deleteUser(user.getUid()));
    }
}
