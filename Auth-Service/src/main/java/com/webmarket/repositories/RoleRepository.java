package com.webmarket.repositories;

import com.webmarket.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Реопзитарий для работы с ролями пользователей.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("select r from Role r where r.name = ?1")
    Role findRoleByName(String role);
}
