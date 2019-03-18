package com.example.search.coffee.repository;

import com.example.search.coffee.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository to maintain persistance layer of User.
 * @author Armin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
}
