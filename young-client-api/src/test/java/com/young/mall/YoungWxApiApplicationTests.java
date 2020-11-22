package com.young.mall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class YoungWxApiApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        Class<? extends DataSource> aClass = dataSource.getClass();
        System.out.println("连接池" + aClass);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}
