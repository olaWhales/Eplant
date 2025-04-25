package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.Mc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface McRepository extends JpaRepository<Mc, Long> {
}
