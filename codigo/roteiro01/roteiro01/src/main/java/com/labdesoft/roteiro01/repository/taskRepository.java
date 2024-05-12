package com.labdesoft.roteiro01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.labdesoft.roteiro01.entity.task;

public interface taskRepository extends JpaRepository<task, Long> {
}