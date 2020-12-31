package pl.lukaszg.sportapp.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean findByUsername(String username);

    @Query("Select u From User u")
    List<User> findAll();



}