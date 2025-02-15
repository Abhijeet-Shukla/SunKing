package com.sunking.CaseStudy.Repository;

import com.sunking.CaseStudy.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
