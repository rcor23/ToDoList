package com.labdessoft.roteiro01.controller;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<Task>> listAll() {
        try {
            List<Task> taskList = new ArrayList<>();
            taskRepository.findAll().forEach(taskList::add);
            if (taskList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/task")
    @Operation(summary = "Cria uma nova tarefa na lista")
    public ResponseEntity<String> createTask(@RequestBody Task newTask) {
        try {
            newTask.setCompleted(false); 
            taskRepository.save(newTask);
            return new ResponseEntity<>("Tarefa criada com sucesso", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
    @PutMapping("/task/{taskId}/complete")
    @Operation(summary = "Marca uma tarefa como concluída")
    public ResponseEntity<String> markTaskAsComplete(@PathVariable Long taskId) {
        try {
            taskRepository.findById(taskId).ifPresent(task -> {
                task.setCompleted(true);
                taskRepository.save(task);
            });
            return new ResponseEntity<>("Tarefa marcada como concluída", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/task/{taskId}")
    @Operation(summary = "Exclui uma tarefa da lista")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        try {
            taskRepository.deleteById(taskId);
            return new ResponseEntity<>("Tarefa excluída com sucesso", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
