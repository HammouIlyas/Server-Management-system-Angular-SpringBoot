package com.example.servermanagementsystem_backend.controllers;

import com.example.servermanagementsystem_backend.model.HttpResponse;
import com.example.servermanagementsystem_backend.model.Server;
import com.example.servermanagementsystem_backend.model.ServerStatus;
import com.example.servermanagementsystem_backend.service.implementation.ServerServicesIplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.servermanagementsystem_backend.model.ServerStatus.SERVER_UP;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerServicesIplementation servicesIplementation;

    @GetMapping ("/list")
    public ResponseEntity<HttpResponse> getServers() throws InterruptedException {
        TimeUnit.MILLISECONDS.  sleep(2000);
        System.out.println("getting all servers");
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("servers",servicesIplementation.listServers(10)))
                        .message("severs found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build())
                ;

    }

    @GetMapping ("/list2")
    public Collection<Server> getServers2(){
        System.out.println("getting all servers");
        return servicesIplementation.listServers(2);

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

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("server",servicesIplementation.createServer(server)))
                        .message("server created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                ;
    }

    @GetMapping("/get/{serverId}")
    public ResponseEntity<HttpResponse> fetchServer(@PathVariable Long serverId){
        Server server = servicesIplementation.getServer(serverId);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("server",server))
                        .message("server fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build())
                ;
    }

    @DeleteMapping("/delete/{serverId}")
    public ResponseEntity<HttpResponse> deleteServer(@PathVariable Long serverId) {

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDate.now())
                        .data(Map.of("server",servicesIplementation.deleteServer (serverId)))
                        .message("server deleted ")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build())
                ;
    }


    @GetMapping(path="/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/images/" + fileName));
    }
}
