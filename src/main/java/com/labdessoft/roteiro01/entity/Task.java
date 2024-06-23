package com.labdessoft.roteiro01.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;
    private LocalDate creationDate;

    private TaskPriorityEnum priority;
    private TaskTypeEnum type;

    private Boolean completed;
    private LocalDate plannedDate;

    private Integer plannedDays;
    private LocalDate lastModifiedPrazoDate;

    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.type = TaskTypeEnum.LIVRE;
        this.priority = TaskPriorityEnum.AUSENTE;
    }

    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority) {
        this.description = description;
        this.completed = false;
        this.type = type;
        this.priority = priority;
        this.creationDate = LocalDate.now();
    }

    // Para tipo DATA
    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority, LocalDate plannedDate) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.plannedDate = plannedDate;
        this.completed = false;
        this.creationDate = LocalDate.now();
    }

    // Para tipo PRAZO
    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority, Integer plannedDays) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.plannedDays = plannedDays;
        this.completed = false;
        this.creationDate = LocalDate.now();
        this.lastModifiedPrazoDate = LocalDate.now();
    }

    @Override
    public String toString() {
        String status = calculateStatus();
        return String.format("Task [id=%d, description=%s, priority=%s, type=%s, status=%s]",
                this.id, this.description, this.priority, type.getDescricao(), status);
    }
    
    private String calculateStatus() {
        if (this.completed) {
            return "Concluída";
        }
        LocalDate currentDate = LocalDate.now();
        long daysLate;
        switch (this.type) {
            case DATA:
                if (plannedDate != null) {
                    daysLate = ChronoUnit.DAYS.between(plannedDate, currentDate);
                    return plannedDate.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case PRAZO:
                if (plannedDays != null) {
                    LocalDate dueDate = creationDate.plusDays(plannedDays);
                    daysLate = ChronoUnit.DAYS.between(dueDate, currentDate);
                    return dueDate.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case LIVRE:
                return "Prevista";
        }
        return "Prevista";
    }

    // Talvez chamar quando for enviar as tarefas para o frontend (checar)
    public void diminuirDiasPrazo() {
        LocalDate currentDate = LocalDate.now();

        LocalDate referenceDate;
        if (this.getLastModifiedPrazoDate() != null) {
            referenceDate = this.getLastModifiedPrazoDate();
        } else {
            referenceDate = this.getCreationDate();
        }

        long diasPassados = ChronoUnit.DAYS.between(referenceDate, currentDate);

        int prazoRestante = this.getPlannedDays() - (int) diasPassados;

        this.setPlannedDays(prazoRestante);

    }

}
