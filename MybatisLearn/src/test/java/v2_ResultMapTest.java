import org.junit.Test;
import site.icefox.javaeelearn.Dao.StudentDao;

public class v2_ResultMapTest {
    @Test
    public void resTest() {
        System.out.println(StudentDao.findAllStudents());
    }
}
