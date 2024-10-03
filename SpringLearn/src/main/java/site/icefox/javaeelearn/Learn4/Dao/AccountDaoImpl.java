package site.icefox.javaeelearn.Learn4.Dao;

import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.icefox.javaeelearn.Learn4.Entity.Account;

import java.util.List;

@Setter
public class AccountDaoImpl implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public int addAccount(Account account) {
        String sql = "INSERT INTO account (username, balance) VALUES (?,?)";
        Object Params = new Object[]{account.getUsername(), account.getBalance()};
        return this.jdbcTemplate.update(sql, Params);
    }


    public int updateAccount(Account account) {
        String sql = "UPDATE account SET username=?, balance=? WHERE id=?";
        Object Params = new Object[]{account.getUsername(), account.getBalance(), account.getId()};
        return this.jdbcTemplate.update(sql, Params);
    }


    public int deleteAccount(int id) {
        String sql = "delete from account where id =?;";
        return this.jdbcTemplate.update(sql, id);
    }


    public Account selectAccountById(int id) {
        try {
            String sql = "select * from account where id = ?";
            RowMapper<Account> rowMapper =
                    new BeanPropertyRowMapper<>(Account.class);
            return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }


    public List<Account> selectAccounts() {
        String sql = "select * from account";
        RowMapper<Account> rowMapper =
                new BeanPropertyRowMapper<>(Account.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }


    /**
     * 转账
     *
     * @param outUser 汇款人
     * @param inUser  收款人
     * @param money   收款金额
     */
    @Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.DEFAULT, readOnly = false)
    public void transfer(String outUser, String inUser, Double money) {
        // 收款时，收款用户的余额=现有余额+所汇金额
        this.jdbcTemplate.update("update account set balance = balance +? "
                + "where username = ?", money, inUser);
        // 模拟系统运行时的突发性问题
        int i = 1 / 0;
        // 汇款时，汇款用户的余额=现有余额-所汇金额
        this.jdbcTemplate.update("update account set balance = balance-? "
                + "where username = ?", money, outUser);
    }
}