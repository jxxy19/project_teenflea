package org.fullstack4.teenflea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeenfleaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeenfleaApplication.class, args);
    }

}
