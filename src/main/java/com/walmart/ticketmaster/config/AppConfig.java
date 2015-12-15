package com.walmart.ticketmaster.config;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.dao.impl.SeatHoldDaoImpl;
import com.walmart.ticketmaster.service.SeatHoldService;
import com.walmart.ticketmaster.service.impl.SeatHoldServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Durga on 12/15/2015.
 */
@Configuration
@PropertySource("classpath:dbConfig.properties")
@EnableTransactionManagement
@Slf4j
public class AppConfig {

    @Value("${hibernate.hibernateDialect}")
    private String hibernateDialect;
    @Value("${hibernate.showSQL}")
    private String showSql;
    @Value("${hibernate.generateStatistics}")
    private String generateStatistics;
    @Value("hibernate.hbm2ddl.auto")
    private String hibernateHbm2ddl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean (name = "seatHoldDao")
    public SeatHoldDao seatHoldDao() throws IOException {
        return new SeatHoldDaoImpl();
    }


    @Bean (name = "seatHoldService")
    public SeatHoldService sampleService(){
        return new SeatHoldServiceImpl();
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.walmart.ticketmaster.domain");
        sessionFactoryBean.setAnnotatedPackages("com.walmart.ticketmaster.domain");
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    /**
     * Spring provided H2 Embedded Database. Read the dbscript and initiates the Database with the name H2-Test-DB.
     *
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("H2-Test-DB");
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                /*.addScript("classpath:dbscript/my-schema.sql")
                .addScript("classpath:dbscript/my-test-data.sql")*/.build();
        log.info("Initiating the database from dbscript.");
        return db;
    }


    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() throws Exception {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(this.sessionFactory());
        return transactionManager;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.generate_statistics", generateStatistics);
        return properties;
    }

}
