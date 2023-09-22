package ua.lviv.iot.algo.part1.coursova.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ua.lviv.iot.algo.part1.coursova.service",
        "ua.lviv.iot.algo.part1.coursova.controller",
        "ua.lviv.iot.algo.part1.coursova.model",
        "ua.lviv.iot.algo.part1.coursova.datastorage"})
public class NovaPoshtaApiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(NovaPoshtaApiApplication.class, args);
    }

}
