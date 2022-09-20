package com.webmarket.repositories;

import com.webmarket.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A repository for working with users.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Selects a user by his name.
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * Selects all users.
     * @return
     */
    @Query("select u from User u")
    List<User> getAll();
}
