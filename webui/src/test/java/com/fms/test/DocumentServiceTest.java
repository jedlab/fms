package com.fms.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fms.app.dao.UserDao;
import com.fms.app.domain.DocumentEntity;
import com.fms.app.domain.DocumentEntity.Permission;
import com.fms.app.domain.DocumentEntity.Type;
import com.fms.app.domain.FolderEntity;
import com.fms.app.domain.UserEntity;
import com.fms.app.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
//@TestPropertySource(properties = { })
@Slf4j
public class DocumentServiceTest {

	
	@Autowired
	DocumentService documentService;
	
	@Test
	@Transactional
	@Commit
	@Ignore
	public void testdocumentInsertion()
	{
		Assert.notNull(documentService);
		FolderEntity entity = new FolderEntity();
		entity.setName("my folder");
		//
		
		UserEntity ue = new UserEntity();
		ue.setId(7L);
		entity.setOwner(ue);
		//
		entity.setPermission(Permission.OWNER);
		entity.setType(Type.HOME);
		entity.setPublicDocument(true);
		entity.setUuid(UUID.randomUUID().toString());
		documentService.insert(entity);
	}
	
	
	
	
	@TestConfiguration
	@EntityScan(basePackageClasses={ UserEntity.class })
	@EnableJpaRepositories(basePackageClasses={ UserDao.class }, queryLookupStrategy=Key.USE_DECLARED_QUERY)
	@ComponentScan(basePackageClasses = DocumentService.class)
    static class Config
    {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver()
        {
            return new PropertySourcesPlaceholderConfigurer();
        }
        
        
        @Bean
        public DataSource dataSource()
        {
            DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/fmsdb", "fms",
                    "fms");
            return ds;
        }

        @Bean
        public PlatformTransactionManager transactionManager(
                 EntityManagerFactory entityManagerFactory)
        {
            return new JpaTransactionManager(entityManagerFactory);
        }
        
        @Bean
        PasswordEncoder passwordEncoder()
        {
        	return new BCryptPasswordEncoder();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                JpaProperties props)
        {
            HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
            LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
            factoryBean.setJpaPropertyMap(props.getProperties());
            factoryBean.setPackagesToScan(DocumentEntity.class.getPackage().getName());
            factoryBean.setPersistenceUnitName("persistenceUnit");
            return factoryBean;
        }

        @Bean("props")
        public JpaProperties jpaProperties()
        {
            JpaProperties prop = new JpaProperties();
            prop.setShowSql(true);
            prop.setDatabase(Database.POSTGRESQL);
            Map<String, String> m = new HashMap<>();
            m.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            m.put("hibernate.jdbc.lob.non_contextual_creation", "true");
            m.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
            prop.setProperties(m);
            return prop;
        }
        
      
    }
	
}
