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
    @Autowired
    private ServerServicesIplementation servicesIplementation;
    @Bean
    CommandLineRunner run(){
        return args -> {
            servicesIplementation.createServer(new Server(null,"196.75.203.63","Fedora Linux","16GB",
                    "personnal laptop","http://....", SERVER_UP));
            servicesIplementation.createServer(new Server(null,"192.168.1.146","Ubuntu Linux","32GB",
                    "Orange server","http://....", SERVER_UP));
            servicesIplementation.createServer(new Server(null,"196.75.203.61","Fedora Linux","64GB",
                    "Mail server","http://....", SERVER_DOWN));
            servicesIplementation.createServer(new Server(null,"196.75.203.62","Debian Linux","128GB",
                    "Web server","http://....", SERVER_UP));
        };
    }
}
