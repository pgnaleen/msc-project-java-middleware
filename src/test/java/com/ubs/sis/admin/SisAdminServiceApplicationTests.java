package com.ubs.sis.admin;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ConditionalOnProperty(value = "keycloak.enabled", matchIfMissing = true)
@SpringBootTest
@EnableJpaRepositories
@ComponentScan(basePackages = {"com.ubs.*"})
class SisAdminServiceApplicationTests {

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

}
