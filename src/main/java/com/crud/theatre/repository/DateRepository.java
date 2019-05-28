package com.crud.theatre.repository;

import com.crud.theatre.domain.SpectacleDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<SpectacleDate, Long> {
}
