package org.yearup.data.mysql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yearup.configurations.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties") // to use test/resources/application.properties
@ContextConfiguration(classes = DatabaseConfig.class) //was (classes = TestDatabaseConfig.class) but that's a whole different testing setup
public abstract class BaseDaoTestClass
{
    @Autowired
    protected DataSource dataSource;
    //set dataSource variables for: url, username, and password in: test/resources/application.properties

    //not being used anymore
    /*
    @AfterEach
    public void rollback() throws SQLException
    {
        Connection connection = dataSource.getConnection();
        if(!connection.getAutoCommit())
        {
            dataSource.getConnection().rollback();
        }
    }
    */
}
