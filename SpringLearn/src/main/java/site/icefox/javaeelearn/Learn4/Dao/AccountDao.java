package site.icefox.javaeelearn.Learn4.Dao;

import site.icefox.javaeelearn.Learn4.Entity.Account;

import java.util.List;

public interface AccountDao {

    int addAccount(Account account);

    int updateAccount(Account account);

    int deleteAccount(int id);

    Account selectAccountById(int id);

    List<Account> selectAccounts();

    void transfer(String outUser, String inUser, Double money);

}