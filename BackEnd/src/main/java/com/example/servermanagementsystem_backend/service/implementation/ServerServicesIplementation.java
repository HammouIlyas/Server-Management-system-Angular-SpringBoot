package com.example.servermanagementsystem_backend.service.implementation;

import com.example.servermanagementsystem_backend.Repositories.ServerRepo;
import com.example.servermanagementsystem_backend.model.Server;
import com.example.servermanagementsystem_backend.service.abstraction.ServerServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
        return null;
    }

    @Override
    public Server getServer(Long id) {
        return null;
    }

    @Override
    public Server UpdateServer(Server server) {
        return null;
    }

    @Override
    public Boolean deleteServer(Long id) {
        return null;
    }

    @Override
    public Server pingServer(Server server) {
        return null;
    }

    @Override
    public Collection<Server> listServers(int number) {
        return null;
    }
}
