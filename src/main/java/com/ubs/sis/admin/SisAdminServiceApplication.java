package com.ubs.sis.admin;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ubs.*"})
public class SisAdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisAdminServiceApplication.class, args);

        HttpsURLConnection.setDefaultHostnameVerifier(
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
