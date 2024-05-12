package com.labdesoft.roteiro01.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Todos os detalhes sobre uma tarefa.")
public class task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Schema(name = "Descrição da tarefa")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    
    private taskType type;
    private taskPriority priority;

    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate finalDate;

    private Boolean completed;

    public task(String description, taskType type, taskPriority priority) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.finalDate = finalDate;
        this.completed = false;
    }

    
    public String getStatus() {
    if (type == taskType.DATA) {
        if (completed) {
            return "Concluída";
        } else if (finalDate == null) {
            return "Prevista, data prevista: Não especificada";
        } else if (finalDate.isAfter(LocalDate.now())) {
            return "Prevista, data prevista: " + finalDate;
        } else {
            long daysLate = ChronoUnit.DAYS.between(finalDate, LocalDate.now());
            return "Atrasada por " + daysLate + " dia" + (daysLate != 1 ? "s" : "");
        }
    } else if (type == taskType.PRAZO) {
        if (completed) {
            return "Concluída";
        } else if (finalDate == null) {
            return "Prevista, previsão: Não especificada";
        } else {
            LocalDate today = LocalDate.now();
            LocalDate deadline = finalDate.plusDays(priority.ordinal() + 1);
            if (deadline.isEqual(today)) {
                return "Prevista, previsão: 1 dia";
            } else if (deadline.isAfter(today)) {
                long daysRemaining = ChronoUnit.DAYS.between(today, deadline);
                return "Prevista, previsão: " + daysRemaining + " dia" + (daysRemaining != 1 ? "s" : "") + " restante" + (daysRemaining != 1 ? "s" : "");
            } else {
                long daysLate = ChronoUnit.DAYS.between(finalDate, LocalDate.now());
                return "Atrasada por " + daysLate + " dia" + (daysLate != 1 ? "s" : "");
            }
        }
    } else { // Livre
        return completed ? "Concluída" : "Prevista";
    }
}




@Override
    public String toString() {
    return "Task [id=" + id + ", description=" + description + ", type=" + type + ", priority=" + priority + ", finalDate=" + finalDate + ", status=" + getStatus() + "]";
    }




}

