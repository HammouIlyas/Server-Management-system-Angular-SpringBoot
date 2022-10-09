package com.example.servermanagementsystem_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Ilyass Hammou
 * @version 1.0
 * @since 24/9/2022
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "ip adreess must not be empty")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private ServerStatus status;


}
