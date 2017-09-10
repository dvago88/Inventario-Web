package com.danielvargas.InventarioWeb.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/**
 * Created by Daniel on 20/08/2017
 */

@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        Resource config=new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactoryBean=new LocalSessionFactoryBean();
        sessionFactoryBean.setConfigLocation(config);
        sessionFactoryBean.setPackagesToScan(env.getProperty("inventario.entity.package"));
        sessionFactoryBean.setDataSource(dataSource());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds=new BasicDataSource();
        ds.setDriverClassName(env.getProperty("inventario.ds.driver"));
        ds.setUrl(env.getProperty("inventario.ds.url"));
        ds.setUsername(env.getProperty("inventario.ds.username"));
        ds.setPassword(env.getProperty("inventario.ds.password"));
        return ds;
    }
}
