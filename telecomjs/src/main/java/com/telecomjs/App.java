package com.telecomjs;

/**
 * Hello world!
 *
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
//import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.telecomjs.mappers")
public class App  /*implements EmbeddedServletContainerCustomizer*/
{

    Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }


    //DataSource配置
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DruidDataSource dataSource() {
        DruidDataSource ds = new com.alibaba.druid.pool.DruidDataSource();
        ds.setMaxActive(100);
        ds.setMinIdle(20);
        return ds;
    }

    //tomcat 数据源
    /*@Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setMaxActive(100);
        ds.setMaxIdle(400);
        return ds;
    }*/

    //c3p0 数据源
    /*@Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public ComboPooledDataSource dataSource() {
        //@Value("${spring.datasource.maxconnections}")int maxConnections;
        ComboPooledDataSource ds = new com.mchange.v2.c3p0.ComboPooledDataSource();
        ds.setMaxPoolSize(400);
        ds.setInitialPoolSize(10);
        ds.setMinPoolSize(50);
        return ds;
    }*/

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/*Mapper.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

   //@Override
//    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        //configurableEmbeddedServletContainer.setPort(9998);
//    }


}
