package com.crud.theatre.repository;

import com.crud.theatre.domain.LoginInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInformationRepository extends JpaRepository<LoginInformation, Long> {
}
