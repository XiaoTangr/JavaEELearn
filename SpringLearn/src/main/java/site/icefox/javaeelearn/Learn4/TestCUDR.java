package site.icefox.javaeelearn.Learn4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import site.icefox.javaeelearn.Learn4.Dao.AccountDao;
import site.icefox.javaeelearn.Learn4.Entity.Account;

public class TestCUDR {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("learn4/applicationContext.xml");
        AccountDao accountDao =
                (AccountDao) applicationContext.getBean("accountDao");

        Account account;

        //添加
        Account newaccount = new Account();
        newaccount.setUsername("张三");
        newaccount.setBalance(1.55);
        accountDao.addAccount(newaccount);

        //删除
        int result = accountDao.deleteAccount(99);
        if (result > 0) {
            System.out.println("删除数据成功！");
        } else {
            System.out.println("删除数据失败！");
        }

        //查询
        account = accountDao.selectAccountById(99);
        System.out.println(account);
        System.out.println(accountDao.selectAccounts());

        //更新
        account.setUsername("李四");
        account.setBalance(1000.1);
        accountDao.updateAccount(account);
        System.out.println("更新后的数据为：" + accountDao.selectAccountById(99));

    }
}