package com.example.servermanagementsystem_backend.service.abstraction;

import com.example.servermanagementsystem_backend.model.Server;

import java.util.Collection;

public interface ServerServices {
    Server createServer(Server server);
    Server getServer(Long id);
    Server UpdateServer(Server server);
    Boolean deleteServer(Long id);
    Server pingServer(Server server);
    Collection<Server> listServers(int number);
}
