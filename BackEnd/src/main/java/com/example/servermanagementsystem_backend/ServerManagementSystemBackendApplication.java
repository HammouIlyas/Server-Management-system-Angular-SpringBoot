package com.example.servermanagementsystem_backend;

import com.example.servermanagementsystem_backend.Repositories.ServerRepo;
import com.example.servermanagementsystem_backend.model.Server;
import com.example.servermanagementsystem_backend.model.ServerStatus;
import com.example.servermanagementsystem_backend.service.implementation.ServerServicesIplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.servermanagementsystem_backend.model.ServerStatus.SERVER_DOWN;
import static com.example.servermanagementsystem_backend.model.ServerStatus.SERVER_UP;

@SpringBootApplication
public class ServerManagementSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagementSystemBackendApplication.class, args);
    }
    @Autowired
    private ServerRepo serverRepo;
    @Bean
    CommandLineRunner run(){
        return args -> {
            serverRepo.save(new Server(null,"192.168.1.156","Fedora Linux","16GB",
                    "personnal laptop","http://....", SERVER_UP));
            serverRepo.save(new Server(null,"192.168.1.146","Ubuntu Linux","32GB",
                    "Orange server","http://....", SERVER_UP));
            serverRepo.save(new Server(null,"192.168.1.136","Fedora Linux","64GB",
                    "Mail server","http://....", SERVER_DOWN));
            serverRepo.save(new Server(null,"192.168.1.126","Debian Linux","128GB",
                    "Web server","http://....", SERVER_UP));
        };
    }
}
