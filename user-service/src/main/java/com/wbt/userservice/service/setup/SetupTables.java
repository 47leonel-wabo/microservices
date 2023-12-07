package com.wbt.userservice.service.setup;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
public record SetupTables(R2dbcEntityTemplate r2dbcEntityTemplate, Environment environment) {

    private static String pathFile = "";

    @PostConstruct
    public void getClasspath() {
        if (environment().acceptsProfiles("dev"))
            pathFile = "h2/init.sql";
        else
            pathFile = "postgres/init.sql";
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            String query = StreamUtils.copyToString(new ClassPathResource(pathFile).getInputStream(), StandardCharsets.UTF_8);

            System.out.println("************************************************************************************");
            System.out.println("Executing Query");
            System.out.println(query);

            this.r2dbcEntityTemplate.getDatabaseClient()
                    .sql(query)
                    .then()
                    .subscribe();

            System.out.println("************************************************************************************");
        };
    }

}
