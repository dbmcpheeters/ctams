/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "org.wuspba.ctams.data")
@EnableTransactionManagement
public class JPAConfig {

    private static final Logger LOG = LoggerFactory.getLogger(JPAConfig.class);

    private enum DBType { EMBEDDED, MYSQL }

    private DBType type = DBType.EMBEDDED;

    String testDB = "localhost:3306";
    String prodDB = "ctamtestsadmin.db.11349954.hostedresource.com";
                
    String dbURL = "localhost:3306";
    String dbName = "ctams";
    String dbUsername = "mysql";
    String dbPassword = "mysql";

    public JPAConfig() {
        InputStream in = getClass().getResourceAsStream("/config.properties");

        if(in != null) {
            Properties p = new Properties();
            try {
                p.load(in);

                String dbTypeIn = p.getProperty("ctams.db.type");
                dbURL = p.getProperty("ctams.db.url");
                dbUsername = p.getProperty("ctams.db.username");
                dbPassword = p.getProperty("ctams.db.password");
                dbName = p.getProperty("ctams.db.name");

                try {
                    type = DBType.valueOf(dbTypeIn);
                } catch(IllegalArgumentException ex) {
                    LOG.warn("Unknown database type '" + dbTypeIn + "'");
                    type = DBType.EMBEDDED;
                }

            } catch (IOException ex) {
                LOG.warn("Could not find configuration file, using defaults.", ex);
            }
        } else {
            LOG.warn("Could not find configuration file, using defaults.");
        }
    }

    @Bean
    public DataSource dataSource() {
        if(type == DBType.MYSQL) {
            LOG.info("Using MySQL database '" + dbName + "'@'" + dbURL + "'");
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://" + dbURL + "/" + dbName + "?zeroDateTimeBehavior=convertToNull");
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            return dataSource;
        } else {
            LOG.info("Using embedded Derby database");
            return new EmbeddedDatabaseBuilder().setType(DERBY).build();
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("org.wuspba.ctams.model");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        if(type == DBType.MYSQL) {
            hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        } else {
            hibernateJpaVendorAdapter.setDatabase(Database.DERBY);
        }
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}
