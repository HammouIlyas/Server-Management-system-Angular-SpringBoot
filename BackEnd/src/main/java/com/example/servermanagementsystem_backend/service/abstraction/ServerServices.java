package com.example.servermanagementsystem_backend.service.abstraction;

import com.example.servermanagementsystem_backend.model.Server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

public interface ServerServices {
    Server createServer(Server server);
    Server getServer(Long id);
    Server UpdateServer(Server server);
    Boolean deleteServer(Long id);
    Server pingServer(String ipAdress) throws IOException;
    Collection<Server> listServers(int number);
}
