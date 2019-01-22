package com.mbronshteyn.gameserver.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableJpaAuditing
public class GameAuditorConfig {

    @Autowired
    SecurityUser securityUser;

    @Bean
    public SecurityAuditor createAuditorProvider() {
        return new SecurityAuditor();
    }

    @Bean
    public AuditingEntityListener createAuditingListener() {
        return new AuditingEntityListener();
    }

    public class SecurityAuditor implements AuditorAware<String> {

        @Override
        public String getCurrentAuditor() {
            return securityUser.getUser();
        }
    }
}
