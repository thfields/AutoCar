package com.ifrn.autocar.repositories;

import com.ifrn.autocar.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}