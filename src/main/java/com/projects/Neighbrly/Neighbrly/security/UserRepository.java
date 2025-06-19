package com.projects.Neighbrly.Neighbrly.security;


import com.projects.Neighbrly.Neighbrly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);


}
