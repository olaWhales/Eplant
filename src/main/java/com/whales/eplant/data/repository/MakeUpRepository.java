package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.MakeUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeUpRepository extends JpaRepository<MakeUp, Long> {
}
