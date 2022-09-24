package com.example.servermanagementsystem_backend.model;

/**
 * @author Ilyass Hammou
 * @version 1.0
 * @since 24/9/2022
 */
public enum ServerStatus {
    SERVER_UP("the server is up"),
    SERVER_DOWN("the server is down");

    private final String status;

    ServerStatus (String serverStatus){
        this.status= serverStatus;
    }
    public String getStatus(){
        return this.status;
    }

}
