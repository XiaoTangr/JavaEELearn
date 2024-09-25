import org.junit.Test;
import site.icefox.javaeelearn.Dao.UserDao;
import site.icefox.javaeelearn.Entity.UserEntity;


public class v1_FirstProjectTest {
    static UserEntity user;
    static {
        user = new UserEntity();
        user.setUid(1099);
        user.setUname("testUser");
        user.setUage(99);
        user.setUsex(1);
    }
    @Test
    public void queryOneUserByUidTest() {
        System.out.println(UserDao.getUsersByUid(user.getUid()));
    }

    @Test
    public void queryUserListTest() {
        System.out.println(UserDao.getAllUsers());
    }

    @Test
    public void addUserTest() {
        System.out.println(UserDao.addUser(user));
    }
    @Test
    public void updateUserTest() {
        user.setUname("updateUser");
        System.out.println(UserDao.updateUser(user));
    }
    @Test
    public void deleteUserTest() {
        System.out.println(UserDao.deleteUser(user.getUid()));
    }
}
