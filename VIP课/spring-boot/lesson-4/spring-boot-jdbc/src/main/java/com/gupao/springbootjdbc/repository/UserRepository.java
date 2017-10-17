package com.gupao.springbootjdbc.repository;

import com.gupao.springbootjdbc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Collection;
import java.util.Collections;

/**
 * {@link User 用户}的仓储(SQL、或NoSQL、或内存型)
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-15
 **/
@Repository
public class UserRepository {

    private final DataSource dataSource;

    private final DataSource masterDataSource;

    private final DataSource salveDataSource;

    private final JdbcTemplate jdbcTemplate;

    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("salveDataSource") DataSource salveDataSource,
                          JdbcTemplate jdbcTemplate,
                          PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.salveDataSource = salveDataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    private boolean jdbcSave(User user) {
        boolean success = false;

        System.out.printf("[Thread : %s ] save user :%s\n",
                Thread.currentThread().getName(), user);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name) VALUES (?);");
            preparedStatement.setString(1, user.getName());
            success = preparedStatement.executeUpdate() > 0;

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    @Transactional
    public boolean transactionalSave(User user) {
        boolean success = false;

        success = jdbcTemplate.execute("INSERT INTO users(name) VALUES (?);",
                new PreparedStatementCallback<Boolean>() {

                    @Nullable
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                        preparedStatement.setString(1, user.getName());
                        return preparedStatement.executeUpdate() > 0;
                    }
                });

        return success;
    }


    public boolean save(User user) {
        boolean success = false;

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        success = jdbcTemplate.execute("INSERT INTO users(name) VALUES (?);",
                new PreparedStatementCallback<Boolean>() {

                    @Nullable
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                        preparedStatement.setString(1, user.getName());
                        return preparedStatement.executeUpdate() > 0;
                    }
                });

        platformTransactionManager.commit(transactionStatus);

        return success;
    }

    public Collection<User> findAll() {
        return Collections.emptyList();
    }

}
