package org.example.dataproducer;

import lombok.RequiredArgsConstructor;
import org.example.dataproducer.service.DataProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class DataProducerApplication {

    private final DataProducerService dataProducerService;

    public static void main(String[] args) {
        SpringApplication.run(DataProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner startProducer() {
        return args -> dataProducerService.startSocketServer();
    }
}

