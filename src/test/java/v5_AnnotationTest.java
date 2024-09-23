import org.junit.Test;
import site.icefox.javaeelearn.Dao.OrdersDao;
import site.icefox.javaeelearn.Dao.PersonDao;
import site.icefox.javaeelearn.Dao.UserDao;
import site.icefox.javaeelearn.Dao.WorkerDao;
import site.icefox.javaeelearn.Entity.WorkerEntity;

public class v5_AnnotationTest {

    static WorkerEntity param = new WorkerEntity();

    static {
        param.setId(1);
        param.setName("Alice");
        param.setAge(25);
        param.setSex("female");
        param.setWorker_id(1005);
    }

    //   一 基于注解的单表增删改查
    //    1.1 CUDR
    @Test
    public void findWorkerByIdTest() {
        System.out.println(WorkerDao.selectWorkerById(param.getId()));
    }

    @Test
    public void insertWorkerBySetTest() {
        WorkerEntity temp = WorkerDao.selectWorkerById(param.getId());
        if (temp != null) {
            System.out.println("Duplicate entry  for key 'tb_worker.worker_id',please change the worker_id");
        } else {
            System.out.println(WorkerDao.insertWorkerBySet(param));
            System.out.println(WorkerDao.selectWorkerById(param.getId()));
        }
    }

    @Test
    public void updateWorkerBySetTest() {
        System.out.println(WorkerDao.updateWorkerBySet(param));
    }

    @Test
    public void deleteWorkerByIdTest() {
        if (WorkerDao.selectWorkerById(param.getId()) == null) {
            System.out.println("Worker not found, can't delete.");
        } else {
            System.out.println(WorkerDao.deleteWorkerById(param.getId()));
        }
    }

    //   1.2 Param注解测试
    @Test
    public void ParamTest() {
        System.out.println(WorkerDao.selectWorkerByIdAndName(1, "张三"));
    }


    //    二 基于注解的关联查询
    //    2.1一对一查询

    @Test
    public void selectPersonByIdTest() {
        System.out.println(PersonDao.selectPersonById(param.getId()));
    }

    //    2.2 一对多查询
    @Test
    public void selectWorkerAndPersonByIdTest() {
        System.out.println(UserDao.selectUserById(1));
    }

    //2.3 多对多查询
    @Test
    public void selectOrdersByIdTest() {
        OrdersDao.selectOrdersById(1);
    }
}
