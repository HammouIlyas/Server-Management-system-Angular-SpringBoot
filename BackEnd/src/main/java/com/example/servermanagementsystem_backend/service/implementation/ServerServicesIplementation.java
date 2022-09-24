package com.example.servermanagementsystem_backend.service.implementation;

import com.example.servermanagementsystem_backend.Repositories.ServerRepo;
import com.example.servermanagementsystem_backend.model.Server;
import com.example.servermanagementsystem_backend.model.ServerStatus;
import com.example.servermanagementsystem_backend.service.abstraction.ServerServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import static java.lang.Boolean.TRUE;

@Service
@Transactional
@Slf4j
public class ServerServicesIplementation implements ServerServices {
    @Autowired
    private final ServerRepo serverRepo;

    public ServerServicesIplementation(ServerRepo serverRepo) {
        this.serverRepo = serverRepo;
    }

    @Override
    public Server createServer(Server server) {
        log.info("creating and saving a new server" + server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server getServer(Long id) {
        log.info("getting server with id : "+ id );
        return serverRepo.findById(id).get();
    }

    @Override
    public Server UpdateServer(Server server) {
        log.info("updating server : " +server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean deleteServer(Long id) {
        log.info("deleting server with id : " + id);
        serverRepo.deleteById(id);
        return TRUE;

    }

    @Override
    public Server pingServer(String ipAdress) throws IOException {
        log.info("pinging server with IP : " + ipAdress );
        Server server = serverRepo.findServerByIpAdresse(ipAdress);
        InetAddress inetAddress = InetAddress.getByName(ipAdress);
        server.setStatus(inetAddress.isReachable(10000)? ServerStatus.SERVER_UP : ServerStatus.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> listServers(int size) {
        log.info("fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,size)).toList();
    }

    private String setServerImageUrl() {
        String [] imageServersNames = {"server1.png","server2.png","server3.png","server4.png"};
        System.out.println();
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" +imageServersNames[new Random().nextInt(4)]).toUriString();

    }

}
