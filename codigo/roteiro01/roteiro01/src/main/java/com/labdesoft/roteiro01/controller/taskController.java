package com.labdesoft.roteiro01.controller;
import com.labdesoft.roteiro01.entity.task;
import com.labdesoft.roteiro01.repository.taskRepository;
import com.labdesoft.roteiro01.service.taskService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class taskController {

@Autowired

    taskRepository taskRepository;
    @Autowired
    private taskService taskService;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<task>> listAll() {
    try {
        List<task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }


    @PostMapping("/task")
    @Operation(summary = "Cria uma nova tarefa")
    public ResponseEntity<task> createTask(@RequestBody task newTask) {
        try {
            newTask.setCompleted(false);
            task createdTask = taskRepository.save(newTask);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Exclui uma tarefa")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable("id") Long id) {
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/task/{id}/complete")
    @Operation(summary = "Marca uma tarefa como concluida")
    public ResponseEntity<task> completeTask(@PathVariable("id") Long id) {
    try {
        Optional<task> taskData = taskRepository.findById(id);

        if (taskData.isPresent()) {
            task existingTask = taskData.get();
            existingTask.setCompleted(true); 
            task updatedTask = taskRepository.save(existingTask);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    // apagar depois do teste
    @GetMapping("/task/{id}")
    @Operation(summary = "Recupera uma tarefa pelo ID")
    public ResponseEntity<task> getTaskById(@PathVariable("id") Long id) {
        try {
            Optional<task> taskData = taskRepository.findById(id);

            if (taskData.isPresent()) {
                task retrievedTask = taskData.get();
                return new ResponseEntity<>(retrievedTask, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}