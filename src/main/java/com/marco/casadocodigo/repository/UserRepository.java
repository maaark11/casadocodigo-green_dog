package com.marco.casadocodigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marco.casadocodigo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
