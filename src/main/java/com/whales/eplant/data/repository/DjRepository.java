package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.Dj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DjRepository extends JpaRepository<Dj, Long> {
}
