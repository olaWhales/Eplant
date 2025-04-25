package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.Decorator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoratorRepository extends JpaRepository<Decorator, Long> {
}
