import org.junit.Test;
import site.icefox.javaeelearn.Dao.StudentsDao;

public class ResultMapTest{
    @Test
    public void resTest() {
        System.out.println(StudentsDao.findAllStudents());
    }
}
