package com.labdesoft.roteiro01.service;

import com.labdesoft.roteiro01.repository.taskRepository;
import com.labdesoft.roteiro01.entity.task;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class taskService {

    private final taskRepository taskRepository;

    public taskService(taskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Operation(summary = "Excluir uma tarefa pelo ID")
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    
}
