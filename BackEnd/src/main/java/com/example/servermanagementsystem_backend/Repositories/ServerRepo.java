package com.example.servermanagementsystem_backend.Repositories;

import com.example.servermanagementsystem_backend.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyass Hammou
 * @version 1.0
 * @since 24/9/2022
 */
@Repository
public interface ServerRepo extends JpaRepository<Server,Long> {
}
