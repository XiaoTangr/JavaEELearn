package site.icefox.javaeelearn.Learn3.target21;

import lombok.Data;

@Data
public class UserDaoImpl implements UserDao {
    public void addUser() {
        System.out.println("添加用户");
    }

    public void deleteUser() {
        System.out.println("删除用户");
    }

}
