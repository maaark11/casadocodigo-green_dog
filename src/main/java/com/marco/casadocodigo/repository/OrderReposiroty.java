package com.marco.casadocodigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marco.casadocodigo.domain.Order;

@Repository
public interface OrderReposiroty extends JpaRepository<Order, Long> {

}
