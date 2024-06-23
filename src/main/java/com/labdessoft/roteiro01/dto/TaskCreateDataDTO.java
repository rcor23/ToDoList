package com.labdessoft.roteiro01.dto;

import java.time.LocalDate;

import com.labdessoft.roteiro01.entity.TaskPriorityEnum;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDataDTO {
    @NotBlank(message = "A descrição da tarefa é obrigatória")
    private String description;
    
    @NotNull(message = "A prioridade da tarefa é obrigatória")
    private TaskPriorityEnum priority;
    
    @FutureOrPresent(message = "A data prevista de conclusão deve ser no presente ou no futuro")
    private LocalDate plannedDate;
}
