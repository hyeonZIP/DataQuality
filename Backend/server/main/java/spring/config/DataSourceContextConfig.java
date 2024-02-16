package spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DB Connection 자원 관련 Bean 설정 Class
 * DBCP: HikariCP
 * JDBC: MariaDB or PostgreSQL
 *
 * @author 하석형
 * @since 2023.08.21
 */
@Configuration
public class DataSourceContextConfig {

    /**
     * HikariCP(DBCP) Config(설정 세팅) 객체 Bean 설정
     *
     * @author 하석형
     * @since 2023.08.21
     */
    @Bean(name = "mainHikariConfig")
    public HikariConfig getMainHikariConfig () {
        HikariConfig hikariConfig = new HikariConfig();
        /*MariaDB*/
//        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/test");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("1234");

        /* PostgreSQL*/
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://210.180.118.83/data_quality?currentSchema=dq");
        hikariConfig.setUsername("takensoft");
        hikariConfig.setPassword("tts64103165!@");
        return hikariConfig;
    }

    /**
     * HikariCP(DBCP) 객체 Bean 설정
     *
     * @author 하석형
     * @since 2023.08.21
     */
    @Bean(name = "mainHikariDataSource")
    public HikariDataSource getMainHikariDataSource () {
        HikariDataSource hikariDataSource = new HikariDataSource(getMainHikariConfig());
        return hikariDataSource;
    }
}