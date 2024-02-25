package com.minsait.pruebatecnica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;

@SpringBootApplication(exclude = GroovyTemplateAutoConfiguration.class)
public class PruebatecnicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebatecnicaApplication.class, args);
    }

}
