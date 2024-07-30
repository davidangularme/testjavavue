package com.travelfactory.translationmanager.repository;

import com.travelfactory.translationmanager.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
