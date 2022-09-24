package com.example.servermanagementsystem_backend.controllers;

import com.example.servermanagementsystem_backend.model.HttpResponse;
import com.example.servermanagementsystem_backend.model.Server;
import com.example.servermanagementsystem_backend.model.ServerStatus;
import com.example.servermanagementsystem_backend.service.implementation.ServerServicesIplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static com.example.servermanagementsystem_backend.model.ServerStatus.SERVER_UP;


@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerServicesIplementation servicesIplementation;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getServers(){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("servers",servicesIplementation.listServers(30)))
                        .message("severs found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build())
                ;

    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<HttpResponse> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = servicesIplementation.pingServer(ipAddress);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("server",server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build())
                ;
    }
}
