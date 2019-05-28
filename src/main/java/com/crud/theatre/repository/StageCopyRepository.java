package com.crud.theatre.repository;

import com.crud.theatre.domain.StageCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageCopyRepository extends JpaRepository<StageCopy, Long> {
}
