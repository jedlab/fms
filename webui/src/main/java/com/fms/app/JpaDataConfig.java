package com.fms.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

import com.fms.app.dao.UserDao;
import com.fms.app.domain.UserEntity;

/**
 *
 * @author Omid Pourhadi
 *
 */
@Configuration
@EntityScan(basePackageClasses={ UserEntity.class })
@EnableJpaRepositories(basePackageClasses={ UserDao.class }, queryLookupStrategy=Key.USE_DECLARED_QUERY)
//@EnableJpaAuditing
public class JpaDataConfig
{

//    @Bean
//    public AuditorAware<UserEntity> auditAware()
//    {
//        return new SpringSecurityAuditorAware();
//    }

}
