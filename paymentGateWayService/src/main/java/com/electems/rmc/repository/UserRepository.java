package com.electems.rmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.electems.rmc.model.User;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmail(String userName);
    
    @Query("select u from User u where u.email = :username and u.password = :pass")
	User authUser(@Param("username")String username, @Param("pass") String pass);
    
    @Query("select u from User u where (u.systemDelete = null or u.systemDelete= false) order by firstName,lastName")
	List<User> findAllUsers();

}
