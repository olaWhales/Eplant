package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.Caterer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatererRepository extends JpaRepository<Caterer, Long> {
}
