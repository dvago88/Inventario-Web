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
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Daniel on 20/08/2017
 */

@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setConfigLocation(config);
        sessionFactoryBean.setPackagesToScan(env.getProperty("inventario.entity.package"));
        sessionFactoryBean.setDataSource(dataSource());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        URI dbUri = null;//Cuidado con este null...
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException ex) {
            System.out.println("Algo salió mal con la URI");
        }
        ds.setDriverClassName(env.getProperty("inventario.ds.driver"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
//        database "d8g6soi7mdj5s9:5432/d8g6soi7mdj5s9" does not exist
//      jdbc:postgresql://<host>:<port>/<dbname>?user=<username>&password=<password>
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
        ds.setUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
}
