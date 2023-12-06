package com.wbt.userservice.service.setup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
public record SetupTables(R2dbcEntityTemplate r2dbcEntityTemplate) {

//    @Value("classpath:h2/init.sql")
//    private static Resource initSql;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            String query = StreamUtils.copyToString(new ClassPathResource("h2/init.sql").getInputStream(), StandardCharsets.UTF_8);

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
